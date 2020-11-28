package com.example.android.populartvapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivPosterBgDetail;
    private ImageView ivPosterDetail;
    private TextView tvTitleDetail;
    private TextView tvFirstAirDateDetail;
    private TextView tvVoteAverageDetail;
    private TextView tvGenreDetail;
    private TextView tvPopularityDetail;
    private TextView tvOverviewDetail;

    private ArrayList<Integer> genreList;
    StringBuffer sbGenre = new StringBuffer();
    HashMap<Integer, String> genres = new HashMap<Integer, String>() {
        {
            put(28, "Action");
            put(10759, "Action & Adventure");
            put(16, "Animation");
            put(35, "Comedy");
            put(80, "Crime");
            put(99, "Documentary");
            put(18, "Drama");
            put(10751, "Family");
            put(10762, "Kids");
            put(9648, "Mystery");
            put(10763, "News");
            put(10764, "Reality");
            put(10765, "Sci-Fi & Fantasy");
            put(10766, "Soap");
            put(10767, "Talk");
            put(10768, "War & Politics");
            put(37, "Western");
            put(12, "Adventure");
            put(14, "Fantasy");
            put(36, "History");
            put(27, "Horror");
            put(10402, "Music");
            put(10749, "Romance");
            put(878, "Science Fiction");
            put(10770, "TV Movie");
            put(53, "Thriller");
            put(10752, "War");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initView();
        tvTitleDetail.setText(getIntent().getStringExtra("title"));
        tvFirstAirDateDetail.setText(getIntent().getStringExtra("first_air_date"));
        tvVoteAverageDetail.setText(getIntent().getStringExtra("vote_average"));
        tvPopularityDetail.setText(getIntent().getStringExtra("popularity"));
        tvOverviewDetail.setText(getIntent().getStringExtra("overview"));
        genreList = getIntent().getIntegerArrayListExtra("genre");
        for (int i = 0; i < genreList.size(); i++) {
            sbGenre.append(genres.get(genreList.get(i)));
            sbGenre.append(", ");
        }
        if (sbGenre.length() > 2){
            sbGenre.setLength(sbGenre.length() - 2); // Hapus 2 karakter dibelakang string
        }
        tvGenreDetail.setText(sbGenre.toString());

        if (getIntent().getStringExtra("poster") != null) {
            String url = "https://image.tmdb.org/t/p/w200" + getIntent().getStringExtra("poster");
            Glide.with(DetailActivity.this)
                    .load(url)
                    .error(R.drawable.logonebula)
                    .into(ivPosterBgDetail);
            Glide.with(DetailActivity.this)
                    .load(url)
                    .error(R.drawable.logonebula)
                    .into(ivPosterDetail);
        }else{
            Glide.with(DetailActivity.this).load(R.drawable.logonebula).into(ivPosterBgDetail);
            Glide.with(DetailActivity.this).load(R.drawable.no_image_available).into(ivPosterDetail);
        }

    }

    private void initView() {
        ivPosterBgDetail = findViewById(R.id.iv_poster_bg_detail);
        ivPosterDetail = findViewById(R.id.iv_poster_detail);
        tvTitleDetail = findViewById(R.id.tv_title_detail);
        tvFirstAirDateDetail = findViewById(R.id.tv_firstAirDate_detail);
        tvVoteAverageDetail = findViewById(R.id.tv_voteAverage_detail);
        tvGenreDetail = findViewById(R.id.tv_genre_detail);
        tvPopularityDetail = findViewById(R.id.tv_popularity_detail);
        tvOverviewDetail = findViewById(R.id.tv_overview_detail);
    }

    //Membuat Up-Botton act like Back-Botton
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}