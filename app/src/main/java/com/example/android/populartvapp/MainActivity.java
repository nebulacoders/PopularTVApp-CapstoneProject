package com.example.android.populartvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.android.populartvapp.adapter.TVSeriesAdapter;
import com.example.android.populartvapp.model.ResultsItem;
import com.example.android.populartvapp.model.RootTVSeriesModel;
import com.example.android.populartvapp.model.TVSeries;
import com.example.android.populartvapp.rest.ApiConfig;
import com.example.android.populartvapp.rest.ApiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs;
    public boolean firstStart;

    private RecyclerView rvTVSeries;
    private TVSeriesAdapter adapterTVSeries;
    private ArrayList<ResultsItem> listDataTVSeries = new ArrayList<>();

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
        rvTVSeries = (RecyclerView) findViewById(R.id.recyclerView);

        listDataTVSeries = new ArrayList<>();
        getData();

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

    private void getData(){
        final int gridColumnCount = 3;
        ApiService apiService = ApiConfig.getApiService();
        apiService.getPopular("0dde3e9896a8c299d142e214fcb636f8","en-US","1")
                .enqueue(new Callback<RootTVSeriesModel>() {
                    @Override
                    public void onResponse(Call<RootTVSeriesModel> call, Response<RootTVSeriesModel> response) {
                        if (response.isSuccessful()){
                            listDataTVSeries = response.body().getResults(); // Mengambil data dari JSON lalu ditampung ke model

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

}