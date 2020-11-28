package com.example.android.populartvapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.android.populartvapp.DetailActivity;
import com.example.android.populartvapp.R;
import com.example.android.populartvapp.model.ResultsItem;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class TVSeriesAdapter extends RecyclerView.Adapter<TVSeriesAdapter.ViewHolder> {

    private ArrayList<ResultsItem> listDataTVSeries;
    private Context mContext;

    public TVSeriesAdapter(Context context, ArrayList<ResultsItem> listTVData) {
        this.listDataTVSeries = listTVData;
        this.mContext = context;
    }

    public TVSeriesAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(ArrayList<ResultsItem> data) {
        this.listDataTVSeries = data;
        notifyDataSetChanged();
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
        holder.tvTitle.setText(listDataTVSeries.get(position).getName());
        if (listDataTVSeries.get(position).getFirstAirDate() != null) {
            if (listDataTVSeries.get(position).getFirstAirDate().length() != 0)
                holder.tvFirstAirDate.setText(listDataTVSeries.get(position).getFirstAirDate().substring(0, 4));
        } else
            holder.tvFirstAirDate.setText(listDataTVSeries.get(position).getFirstAirDate());
        holder.tvVoteAverage.setText(Double.toString(listDataTVSeries.get(position).getVoteAverage()));

        if (listDataTVSeries.get(position).getPosterPath() != null) {
            String url = "https://image.tmdb.org/t/p/w200" + listDataTVSeries.get(position).getPosterPath();
            Glide.with(mContext)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.logonebula)
                    .into(holder.ivPoster);
        }else{
            Glide.with(mContext)
                    .load(R.drawable.no_image_available)
                    .into(holder.ivPoster);
        }
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
            detailIntent.putExtra("title", currentTVSeries.getName());
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
