package com.example.android.populartvapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvTitle = findViewById(R.id.titleDetail);
        TextView tvFirstAirDate = findViewById(R.id.firstAirDateDetail);
        TextView tvVoteAverage = findViewById(R.id.voteAverageDetail);
        tvTitle.setText(getIntent().getStringExtra("title"));
        tvFirstAirDate.setText(getIntent().getStringExtra("first_air_date"));
        tvVoteAverage.setText(getIntent().getStringExtra("vote_average"));

    }
}