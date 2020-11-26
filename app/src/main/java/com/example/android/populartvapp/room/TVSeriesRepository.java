package com.example.android.populartvapp.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.android.populartvapp.model.ResultsItem;

import java.util.List;

public class TVSeriesRepository {
    private TVSeriesDao mTVSeriesDao;
    private LiveData<List<ResultsItem>> mResultsItems;

    TVSeriesRepository(Application application){
        TVSeriesRoomDatabase db = TVSeriesRoomDatabase.getDatabase(application);
        mTVSeriesDao = db.tvSeriesDao();
        mResultsItems = mTVSeriesDao.getAllData();
    }

    LiveData<List<ResultsItem>> getAllData() {
        return mResultsItems;
    }

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

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mTVSeriesDao).execute();
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private TVSeriesDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(TVSeriesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
