package com.example.android.populartvapp.rest;

import com.example.android.populartvapp.model.RootTVSeriesModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // tv/popular?api_key=0dde3e9896a8c299d142e214fcb636f8&language=en-US&page=1
    @GET("tv/popular")
    Call<RootTVSeriesModel> getPopular(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page
    );

    // tv/63174?api_key=0dde3e9896a8c299d142e214fcb636f8
    @GET("tv/{tv_id}")
    Call<RootTVSeriesModel> getDetails(
            @Path("tv_id") String tvId,
            @Query("api_key") String apiKey
    );

    @GET("search/tv")
    Call<RootTVSeriesModel> searchTV(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

    @GET("tv/top_rated")
    Call<RootTVSeriesModel> getTopRated(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") String page
    );

}