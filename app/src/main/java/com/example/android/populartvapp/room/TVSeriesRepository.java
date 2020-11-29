package com.example.android.populartvapp.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.android.populartvapp.model.ResultsItem;

import java.util.List;

public class TVSeriesRepository {
    private TVSeriesDao mTVSeriesDao;
    private LiveData<List<ResultsItem>> mResultsItemsPopular;
    private LiveData<List<ResultsItem>> mResultsItemsTopRated;

    TVSeriesRepository(Application application){
        TVSeriesRoomDatabase db = TVSeriesRoomDatabase.getDatabase(application);
        mTVSeriesDao = db.tvSeriesDao();
        mResultsItemsPopular = mTVSeriesDao.getAllDataPopular();
        mResultsItemsTopRated = mTVSeriesDao.getAllDataTopRated();
    }

    LiveData<List<ResultsItem>> getAllDataPopular() {
        return mResultsItemsPopular;
    }
    LiveData<List<ResultsItem>> getAllDataTopRated() {return mResultsItemsTopRated;}

    public void insert (ResultsItem data) {
        new insertAsyncTask(mTVSeriesDao).execute(data);
    }

    private static class insertAsyncTask extends AsyncTask<ResultsItem, Void, Void> {

        private TVSeriesDao mAsyncTaskDao;

        insertAsyncTask(TVSeriesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ResultsItem... params) {
            mAsyncTaskDao.insertData(params[0]);
            return null;
        }
    }

    public void deleteAllPopular()  {
        new deleteAllPopularAsyncTask(mTVSeriesDao).execute();
    }

    private static class deleteAllPopularAsyncTask extends AsyncTask<Void, Void, Void> {
        private TVSeriesDao mAsyncTaskDao;

        deleteAllPopularAsyncTask(TVSeriesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllPopular();
            return null;
        }
    }

    public void deleteAllTopRated()  {
        new deleteAllTopRatedAsyncTask(mTVSeriesDao).execute();
    }

    private static class deleteAllTopRatedAsyncTask extends AsyncTask<Void, Void, Void> {
        private TVSeriesDao mAsyncTaskDao;

        deleteAllTopRatedAsyncTask(TVSeriesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllTopRated();
            return null;
        }
    }
}
