package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.network.model.Result;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.IMAGE_URL;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.MOVIE_POSTER;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.ORIGINAL_TITLE;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.PLOT_SYNOPSIS;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.RELEASE_DATE;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.USER_RATING;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.DataViewHolder> {

    private List<Result> results = new ArrayList<>();
    private Context context;
    private Picasso picasso;

    public ListAdapter(Context context, Picasso picasso) {
        this.context = context;
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public ListAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.single_item, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        picasso.load(IMAGE_URL + results.get(position).getPosterPath())
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cover_image_view)
        ImageView image;

        public DataViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(ORIGINAL_TITLE, results.get(position).getOriginalTitle());
            intent.putExtra(MOVIE_POSTER, IMAGE_URL + results.get(position).getPosterPath());
            intent.putExtra(PLOT_SYNOPSIS, results.get(position).getOverview());
            intent.putExtra(USER_RATING, results.get(position).getVoteAverage());
            intent.putExtra(RELEASE_DATE, results.get(position).getReleaseDate());
            context.startActivity(intent);
        }
    }

    public void swapData(Result teacher) {
        results.add(teacher);
        notifyDataSetChanged();
    }

    public void clearData() {
        results.clear();
    }
}
