package com.example.android.populartvapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.populartvapp.DetailActivity;
import com.example.android.populartvapp.R;
import com.example.android.populartvapp.model.ResultsItem;

import java.util.ArrayList;

public class TVSeriesAdapter extends RecyclerView.Adapter<TVSeriesAdapter.ViewHolder> {

    private ArrayList<ResultsItem> listDataTVSeries;
    private Context mContext;

    public TVSeriesAdapter(Context context, ArrayList<ResultsItem> listTVData) {
        this.listDataTVSeries = listTVData;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(listDataTVSeries.get(position).getOriginalName());
        holder.tvFirstAirDate.setText(listDataTVSeries.get(position).getFirstAirDate().substring(0,4));
        holder.tvVoteAverage.setText(Double.toString(listDataTVSeries.get(position).getVoteAverage()));
        Glide.with(mContext).load(listDataTVSeries.get(position).getPosterPath()).error(R.drawable.logonebula)
                .override(220, 330)
                .into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return (listDataTVSeries != null) ? listDataTVSeries.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables untuk TextViews
        private ImageView ivPoster;
        private TextView tvTitle;
        private TextView tvFirstAirDate;
        private TextView tvVoteAverage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvFirstAirDate = itemView.findViewById(R.id.tv_firstAirDate);
            tvVoteAverage = itemView.findViewById(R.id.tv_voteAverage);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ResultsItem currentTVSeries = listDataTVSeries.get(getAdapterPosition());
            Intent detailIntent = new Intent(mContext, DetailActivity.class);
            detailIntent.putExtra("title", currentTVSeries.getOriginalName());
            detailIntent.putExtra("first_air_date", currentTVSeries.getFirstAirDate());
            detailIntent.putExtra("vote_average", Double.toString(currentTVSeries.getVoteAverage()));
            detailIntent.putExtra("poster", currentTVSeries.getPosterPath());
            detailIntent.putExtra("genre", currentTVSeries.getGenreIds());
            detailIntent.putExtra("overview", currentTVSeries.getOverview());
            detailIntent.putExtra("popularity", Double.toString(currentTVSeries.getPopularity()));
            mContext.startActivity(detailIntent);
        }
    }
}
