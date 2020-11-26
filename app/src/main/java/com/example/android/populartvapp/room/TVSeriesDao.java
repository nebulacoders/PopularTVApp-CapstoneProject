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

    @Query("SELECT * from api_table ORDER BY rank ASC")
    LiveData<List<ResultsItem>> getAllData();

    @Query("DELETE FROM api_table")
    void deleteAll();

}
