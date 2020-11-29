package com.example.android.populartvapp.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.populartvapp.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TVSeriesDao {

    @Insert
    void insertData(ResultsItem data);

//    @Query("SELECT * from api_table ORDER BY rank ASC")
//    LiveData<List<ResultsItem>> getAllData();

    @Query("SELECT * from api_table WHERE variety = 'popular' ORDER BY rank ASC")
    LiveData<List<ResultsItem>> getAllDataPopular();

    @Query("SELECT * from api_table WHERE variety = 'top_rated' ORDER BY rank ASC")
    LiveData<List<ResultsItem>> getAllDataTopRated();

//    @Query("DELETE FROM api_table")
//    void deleteAll();

    @Query("DELETE FROM api_table WHERE variety = 'popular'")
    void deleteAllPopular();

    @Query("DELETE FROM api_table WHERE variety = 'top_rated'")
    void deleteAllTopRated();

}
