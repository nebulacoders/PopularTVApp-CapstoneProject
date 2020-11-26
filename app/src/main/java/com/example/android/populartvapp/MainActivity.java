package com.example.android.populartvapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.populartvapp.adapter.TVSeriesAdapter;
import com.example.android.populartvapp.model.ResultsItem;
import com.example.android.populartvapp.model.RootTVSeriesModel;
import com.example.android.populartvapp.rest.ApiConfig;
import com.example.android.populartvapp.rest.ApiService;
import com.example.android.populartvapp.room.TVSeriesRoomDatabase;
import com.example.android.populartvapp.room.TVSeriesViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs;
    public boolean firstStart;

    private RecyclerView rvTVSeries;
    private TVSeriesAdapter adapterTVSeries;
    private ArrayList<ResultsItem> listDataTVSeries = new ArrayList<>();
    final int gridColumnCount = 3;
    private TVSeriesViewModel mTVSeriesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SharedPrefences IntroActivity
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            Intent intentIntro = new Intent(this, IntroActivity.class);
            startActivity(intentIntro);
        }

        //initView
        rvTVSeries = findViewById(R.id.recyclerView);
        listDataTVSeries = new ArrayList<>();
        mTVSeriesViewModel = ViewModelProviders.of(this).get(TVSeriesViewModel.class);

        if (haveNetwork()) {
            getAndSaveDataAPI();
        } else if (!haveNetwork()) {
            adapterTVSeries = new TVSeriesAdapter(MainActivity.this);
            rvTVSeries.setAdapter(adapterTVSeries);
            rvTVSeries.setLayoutManager(new GridLayoutManager(MainActivity.this, gridColumnCount));
            mTVSeriesViewModel.getAllData().observe(this, new Observer<List<ResultsItem>>() {
                @Override
                public void onChanged(@Nullable final List<ResultsItem> data) {
                    // Update the cached copy of the words in the adapter.
                    adapterTVSeries.setData((ArrayList<ResultsItem>) data);
                }
            });
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
            default:
                // Do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAndSaveDataAPI() {
        ApiService apiService = ApiConfig.getApiService();
        apiService.getPopular("0dde3e9896a8c299d142e214fcb636f8", "en-US", "1")
                .enqueue(new Callback<RootTVSeriesModel>() {
                    @Override
                    public void onResponse(Call<RootTVSeriesModel> call, Response<RootTVSeriesModel> response) {
                        if (response.isSuccessful()) {
                            listDataTVSeries = response.body().getResults(); // Mengambil data dari JSON lalu ditampung ke model
                            mTVSeriesViewModel.deleteAll();

                            // Menyimpan data ke database || Save data
                            for (int i = 0; i < listDataTVSeries.size(); i++) {
                                Integer id = listDataTVSeries.get(i).getId();
                                String name = listDataTVSeries.get(i).getOriginalName();
                                String firstAirDate = listDataTVSeries.get(i).getFirstAirDate();
                                Double voteAverage = listDataTVSeries.get(i).getVoteAverage();
                                String poster = listDataTVSeries.get(i).getPosterPath();
                                String overview = listDataTVSeries.get(i).getOverview();
                                ArrayList<Integer> genre = listDataTVSeries.get(i).getGenreIds();
                                Double popularity = listDataTVSeries.get(i).getPopularity();

                                ResultsItem tvSeries = new ResultsItem();
                                tvSeries.setId(id);
                                tvSeries.setOriginalName(name);
                                tvSeries.setFirstAirDate(firstAirDate);
                                tvSeries.setVoteAverage(voteAverage);
                                tvSeries.setPosterPath(poster);
                                tvSeries.setOverview(overview);
                                tvSeries.setGenreIds(genre);
                                tvSeries.setPopularity(popularity);
                                mTVSeriesViewModel.insert(tvSeries);
                            }

                            adapterTVSeries = new TVSeriesAdapter(MainActivity.this, listDataTVSeries); // Membuat adapter dan supply data yang akan ditampilkan
                            adapterTVSeries.notifyDataSetChanged(); // Memberitahu adapter apabila ada data baru
                            rvTVSeries.setAdapter(adapterTVSeries); // Connect adapter dengan RV
                            rvTVSeries.setLayoutManager(new GridLayoutManager(MainActivity.this, gridColumnCount)); // Menentukan RV default layout
                        }
                    }

                    @Override
                    public void onFailure(Call<RootTVSeriesModel> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
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
}