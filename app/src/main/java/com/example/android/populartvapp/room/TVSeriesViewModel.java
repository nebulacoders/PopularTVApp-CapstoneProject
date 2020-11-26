package com.example.android.populartvapp.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.populartvapp.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;

public class TVSeriesViewModel extends AndroidViewModel {
    private TVSeriesRepository mRepository;
    private LiveData<List<ResultsItem>> mResultItems;

    public TVSeriesViewModel(Application application) {
        super(application);
        mRepository = new TVSeriesRepository(application);
        mResultItems = mRepository.getAllData();
    }

    public LiveData<List<ResultsItem>> getAllData() { return mResultItems; }

    public void insert(ResultsItem resultsItem) { mRepository.insert(resultsItem); }

    public void deleteAll() {mRepository.deleteAll();}
}
