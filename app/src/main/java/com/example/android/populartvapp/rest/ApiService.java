package com.example.android.populartvapp.rest;

import com.example.android.populartvapp.model.RootTVSeriesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // tv/popular?api_key=0dde3e9896a8c299d142e214fcb636f8&language=en-US&page=1
    @GET("popular")
    Call<RootTVSeriesModel> getPopular(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page
    );

    // tv/63174?api_key=0dde3e9896a8c299d142e214fcb636f8
    @GET("{tv_id}")
    Call<RootTVSeriesModel> getDetails(
            @Path("tv_id") String tvId,
            @Query("api_key") String apiKey
    );

}