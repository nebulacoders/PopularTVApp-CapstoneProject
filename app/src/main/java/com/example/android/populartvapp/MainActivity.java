package com.example.android.populartvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.android.populartvapp.adapter.TVSeriesAdapter;
import com.example.android.populartvapp.model.TVSeries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences prefs;
    public boolean firstStart;

    private RecyclerView rvTVSeries;
    private TVSeriesAdapter adapterTVSeries;
    private ArrayList<TVSeries> listDataTVSeries = new ArrayList<>();

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

        addData();
        rvTVSeries = (RecyclerView) findViewById(R.id.recyclerView);
        adapterTVSeries = new TVSeriesAdapter(this, listDataTVSeries);

        rvTVSeries.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvTVSeries.setAdapter(adapterTVSeries);
    }

    private void addData() {
        listDataTVSeries = new ArrayList<>();
        listDataTVSeries.add(new TVSeries("The Boys", "2019-07-25", "8.4"));
        listDataTVSeries.add(new TVSeries("Lucifer", "2016-01-25", "8.5"));
        listDataTVSeries.add(new TVSeries("The Walking Dead", "2010-10-31", "7.8"));
        listDataTVSeries.add(new TVSeries("The 100", "2014-03-19", "7.7"));
        listDataTVSeries.add(new TVSeries("Cobra Kai", "2018-05-02", "8"));
        listDataTVSeries.add(new TVSeries("Raised by Wolves", "2020-09-03", "7.6"));
        listDataTVSeries.add(new TVSeries("The Good Doctor", "2017-09-25", "8.6"));

    }
}