package com.example.android.populartvapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivPosterDetail;
    private TextView tvTitleDetail;
    private TextView tvFirstAirDateDetail;
    private TextView tvVoteAverageDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getIntent().getStringExtra("title"));

        initView();
//        tvTitleDetail.setText(getIntent().getStringExtra("title"));
        tvFirstAirDateDetail.setText(getIntent().getStringExtra("first_air_date"));
        tvVoteAverageDetail.setText(getIntent().getStringExtra("vote_average"));
        Glide.with(DetailActivity.this).load(getIntent().getStringExtra("poster")).error(R.drawable.logonebula)
                .into(ivPosterDetail);
    }

    private void initView() {
        ivPosterDetail = findViewById(R.id.iv_poster_detail);
//        tvTitleDetail = findViewById(R.id.tv_title_detail);
        tvFirstAirDateDetail = findViewById(R.id.tv_firstAirDate_detail);
        tvVoteAverageDetail = findViewById(R.id.tv_voteAverage_detail);
    }
}