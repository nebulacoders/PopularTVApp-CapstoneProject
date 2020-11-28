package com.example.android.populartvapp.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.populartvapp.R;
import com.example.android.populartvapp.adapter.TVSeriesAdapter;
import com.example.android.populartvapp.model.ResultsItem;
import com.example.android.populartvapp.model.RootTVSeriesModel;
import com.example.android.populartvapp.rest.ApiConfig;
import com.example.android.populartvapp.rest.ApiService;
import com.example.android.populartvapp.room.TVSeriesViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    private EditText mTVInput;
    private TextView mLogText;
    private RecyclerView rvTVSeries;
    private TVSeriesAdapter adapterTVSeries;
    private ArrayList<ResultsItem> listDataTVSeries = new ArrayList<>();
    final int gridColumnCount = 3;

    public SecondFragment() {

        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTVSeries = view.findViewById(R.id.recyclerViewSearch);
        mTVInput = view.findViewById(R.id.tvInput);
        final Button button = view.findViewById(R.id.searchButton);
        mLogText = view.findViewById(R.id.log);

        listDataTVSeries = new ArrayList<>();
        adapterTVSeries = new TVSeriesAdapter(getContext());
        rvTVSeries.setAdapter(adapterTVSeries);
        rvTVSeries.setLayoutManager(new GridLayoutManager(getContext(), gridColumnCount));

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchTVSeries(v);
            }
        });
    }

    public void searchTVSeries(View view) {

        // Get the search string from the input field.
        String queryString = mTVInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            listDataTVSeries.clear();
            mLogText.setText("");
            ApiService apiService = ApiConfig.getApiService();
            apiService.searchTV("0dde3e9896a8c299d142e214fcb636f8", queryString)
                    .enqueue(new Callback<RootTVSeriesModel>() {
                        @Override
                        public void onResponse(Call<RootTVSeriesModel> call, Response<RootTVSeriesModel> response) {
                            if (response.isSuccessful()) {
                                listDataTVSeries = response.body().getResults(); // Mengambil data dari JSON lalu ditampung ke model
                                adapterTVSeries.setData(listDataTVSeries);
                                adapterTVSeries.notifyDataSetChanged(); // Memberitahu adapter apabila ada data baru
                            }
                        }

                        @Override
                        public void onFailure(Call<RootTVSeriesModel> call, Throwable t) {
                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            if (queryString.length() == 0) {
                mLogText.setText("Please enter a search term");
            } else {
                mLogText.setText("No network connection");
            }
        }


    }


}