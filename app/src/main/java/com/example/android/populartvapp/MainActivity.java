package com.example.android.populartvapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.android.populartvapp.fragment.PopularFragment;
import com.example.android.populartvapp.fragment.SecondFragment;
import com.example.android.populartvapp.adapter.TVSeriesAdapter;
import com.example.android.populartvapp.fragment.TopRatedFragment;
import com.example.android.populartvapp.model.ResultsItem;
import com.example.android.populartvapp.model.RootTVSeriesModel;
import com.example.android.populartvapp.rest.ApiConfig;
import com.example.android.populartvapp.rest.ApiService;
import com.example.android.populartvapp.room.TVSeriesViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    public SharedPreferences prefs;
    public SharedPreferences.Editor editor;
    public boolean firstStart;

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private PopularFragment popularFragment;
    private TopRatedFragment topRatedFragment;
    private SecondFragment secondFragment;

    private TVSeriesViewModel mTVSeriesViewModel;

    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 1012;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private AlarmManager alarmManager;
    private PendingIntent notifyPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPrefences IntroActivity
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        editor = prefs.edit();
        firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            Intent intentIntro = new Intent(this, IntroActivity.class);
            startActivity(intentIntro);

            enableNotification(null);
        } else
            recordRunTime();

        //initView
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        popularFragment = new PopularFragment();
        topRatedFragment = new TopRatedFragment();
        secondFragment = new SecondFragment();

        tabLayout.setupWithViewPager(viewPager);

//        mTVSeriesViewModel = ViewModelProviders.of(this).get(TVSeriesViewModel.class);
//        if (haveNetwork()) {
//            mTVSeriesViewModel.deleteAll();
//        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(popularFragment, "Popular");
        viewPagerAdapter.addFragment(topRatedFragment, "Top Rated");
        viewPagerAdapter.addFragment(secondFragment, "Search");
        viewPager.setAdapter(viewPagerAdapter);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        createNotificationChannel();

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentsTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentsTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentsTitle.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_alarm: // Toggle Alarm
                String toastMessage;
                if (prefs.getBoolean("enabled", true)) { // Jika true, matikan
                    disableNotification(null);
                    toastMessage = "Alarm Off!";
                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }
                } else {
                    enableNotification(null);
                    toastMessage = "Alarm On!";
                    long repeatInterval = AlarmManager.INTERVAL_DAY; // 1 Day
                    long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                triggerTime, repeatInterval, notifyPendingIntent);
                    }
                }
                Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT).show();
                return true;
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean haveNetwork() {
        boolean have_WIFI = false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected()) have_WIFI = true;

            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected()) have_MobileData = true;
        }
        return have_WIFI || have_MobileData;
    }

    public void createNotificationChannel() {
        // Create a notification manager object.
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            "Reminder notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 1 day");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public void recordRunTime() {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.commit();
    }

    public void enableNotification(View v) {
        editor.putLong("lastRun", System.currentTimeMillis());
        editor.putBoolean("enabled", true);
        editor.commit();
        Log.v(TAG, "Notifications enabled");
    }

    public void disableNotification(View v) {
        editor.putBoolean("enabled", false);
        editor.commit();
        Log.v(TAG, "Notifications disabled");
    }

}