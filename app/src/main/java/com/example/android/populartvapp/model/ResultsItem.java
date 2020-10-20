package com.example.android.populartvapp.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

    @SerializedName("first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("origin_country")
    private List<String> originCountry;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("popularity")
    private Double popularity;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private Integer id;

    @SerializedName("vote_count")
    private Integer voteCount;

    public void setFirstAirDate(String firstAirDate){
        this.firstAirDate = firstAirDate;
    }

    public String getFirstAirDate(){
        return firstAirDate;
    }

    public void setOverview(String overview){
        this.overview = overview;
    }

    public String getOverview(){
        return overview;
    }

    public void setOriginalLanguage(String originalLanguage){
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage(){
        return originalLanguage;
    }

    public void setGenreIds(List<Integer> genreIds){
        this.genreIds = genreIds;
    }

    public List<Integer> getGenreIds(){
        return genreIds;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    public String getPosterPath(){
        return "https://image.tmdb.org/t/p/w200"+posterPath;
    }

    public void setOriginCountry(List<String> originCountry){
        this.originCountry = originCountry;
    }

    public List<String> getOriginCountry(){
        return originCountry;
    }

    public void setBackdropPath(String backdropPath){
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath(){
        return backdropPath;
    }

    public void setOriginalName(String originalName){
        this.originalName = originalName;
    }

    public String getOriginalName(){
        return originalName;
    }

    public void setPopularity(Double popularity){
        this.popularity = popularity;
    }

    public Double getPopularity(){
        return popularity;
    }

    public void setVoteAverage(Double voteAverage){
        this.voteAverage = voteAverage;
    }

    public Double getVoteAverage(){
        return voteAverage;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setVoteCount(Integer voteCount){
        this.voteCount = voteCount;
    }

    public Integer getVoteCount(){
        return voteCount;
    }
}