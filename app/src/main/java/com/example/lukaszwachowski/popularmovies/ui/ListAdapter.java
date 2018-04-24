package com.example.lukaszwachowski.popularmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.network.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.IMAGE_URL;

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

        holder.movie_title.setText(results.get(position).getOriginalTitle());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cover_image_view)
        ImageView image;

        @BindView(R.id.movie_title)
        TextView movie_title;

        public DataViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
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
