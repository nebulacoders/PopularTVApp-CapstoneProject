package com.example.android.populartvapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.populartvapp.DetailActivity;
import com.example.android.populartvapp.R;
import com.example.android.populartvapp.model.TVSeries;

import java.util.ArrayList;

public class TVSeriesAdapter extends RecyclerView.Adapter<TVSeriesAdapter.TVSeriesViewHolder> {

    private ArrayList<TVSeries> listTvData;
    private Context mContext;

    public TVSeriesAdapter(Context context, ArrayList<TVSeries> listTVData) {
        this.listTvData = listTVData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public TVSeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new TVSeriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVSeriesViewHolder holder, int position) {
        holder.mTitle.setText(listTvData.get(position).getTitle());
        holder.mFirstAirDate.setText(listTvData.get(position).getFirstAirDate());
        holder.mVoteAverage.setText(listTvData.get(position).getVoteAverage());
    }

    @Override
    public int getItemCount() {
        return (listTvData != null) ? listTvData.size() : 0;
    }

    class TVSeriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Member Variables untuk TextViews
        private TextView mTitle;
        private TextView mFirstAirDate;
        private TextView mVoteAverage;

        public TVSeriesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mFirstAirDate = itemView.findViewById(R.id.tv_firstAirDate);
            mVoteAverage = itemView.findViewById(R.id.tv_voteAverage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TVSeries currentTVSeries = listTvData.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentTVSeries.getTitle());
            detailIntent.putExtra("first_air_date", currentTVSeries.getFirstAirDate());
            detailIntent.putExtra("vote_average", currentTVSeries.getVoteAverage());
            mContext.startActivity(detailIntent);
        }
    }
}
