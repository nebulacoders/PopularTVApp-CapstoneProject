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
    private LiveData<List<ResultsItem>> mResultsItemsPopular;
    private LiveData<List<ResultsItem>> mResultsItemsTopRated;

    public TVSeriesViewModel(Application application) {
        super(application);
        mRepository = new TVSeriesRepository(application);
        mResultsItemsPopular = mRepository.getAllDataPopular();
        mResultsItemsTopRated = mRepository.getAllDataTopRated();
    }

    public LiveData<List<ResultsItem>> getAllDataPopular() {
        return mResultsItemsPopular;
    }
    public LiveData<List<ResultsItem>> getAllDataTopRated() {return mResultsItemsTopRated;}

    public void insert(ResultsItem resultsItem) { mRepository.insert(resultsItem); }

    public void deleteAllPopular() {mRepository.deleteAllPopular();}
    public void deleteAllTopRated() {mRepository.deleteAllTopRated();}
}
