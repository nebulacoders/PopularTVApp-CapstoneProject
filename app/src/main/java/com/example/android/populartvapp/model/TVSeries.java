package com.example.android.populartvapp.model;

// Class model for Testing with Data Dummy TV Series
// Class model for Testing with Data Dummy TV Series
// Class model for Testing with Data Dummy TV Series
public class TVSeries {

    // Member variables representing the title and information about the tv.
    private String title;
    private String firstAirDate;
    private String voteAverage;

    public TVSeries(String title, String firstAirDate, String voteAverage) {
        this.title = title;
        this.firstAirDate = firstAirDate;
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

}
