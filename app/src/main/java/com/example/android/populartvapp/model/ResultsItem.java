package com.example.android.populartvapp.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "api_table")
public class ResultsItem{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rank")
    private Integer rank;

    @ColumnInfo(name = "variety")
    private String variety;

    @SerializedName("first_air_date")
    @ColumnInfo(name = "first_air_date")
    private String firstAirDate;

    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    private String overview;

    @SerializedName("original_language")
    @ColumnInfo(name = "original_language")
    private String originalLanguage;

    @SerializedName("genre_ids")
    @ColumnInfo(name = "genre_ids")
    private ArrayList<Integer> genreIds;

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @SerializedName("origin_country")
    @ColumnInfo(name = "origin_country")
    private ArrayList<String> originCountry;

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("popularity")
    @ColumnInfo(name = "populatity")
    private Double popularity;

    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private Double voteAverage;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("id")
    @ColumnInfo(name = "id")
    private Integer id;

    @SerializedName("vote_count")
    @ColumnInfo(name = "vote_count")
    private Integer voteCount;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getVariety() { return variety; }

    public void setVariety(String variety) { this.variety = variety; }

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

    public void setGenreIds(ArrayList<Integer> genreIds){
        this.genreIds = genreIds;
    }

    public ArrayList<Integer> getGenreIds(){
        return genreIds;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    public String getPosterPath(){
        return posterPath;
    }

    public void setOriginCountry(ArrayList<String> originCountry){
        this.originCountry = originCountry;
    }

    public ArrayList<String> getOriginCountry(){
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

    public void setId(@NonNull Integer id) {
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