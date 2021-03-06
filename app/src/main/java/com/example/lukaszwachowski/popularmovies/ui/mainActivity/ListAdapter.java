package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import static com.example.lukaszwachowski.popularmovies.utils.Constants.IMAGE_URL;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.DataViewHolder> {

  private List<MoviesResult> results = new ArrayList<>();
  private MainActivity activity;
  private Picasso picasso;
  private OnItemClickListener listener;

  public ListAdapter(MainActivity activity, Picasso picasso) {
    this.activity = activity;
    this.picasso = picasso;
  }

  public void setListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  public interface OnItemClickListener {

    void onItemClick(MoviesResult result, Bundle bundle);
  }

  @NonNull
  @Override
  public ListAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(activity).inflate(R.layout.single_item, parent, false);
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
      Bundle bundle = ActivityOptions
          .makeSceneTransitionAnimation(activity, Pair.create(image, "image")).toBundle();
      listener.onItemClick(results.get(getAdapterPosition()), bundle);
    }
  }

  public void swapData(List<MoviesResult> newResults) {
    results.addAll(newResults);
    notifyDataSetChanged();
  }

  public void clearData() {
    results.clear();
  }
}
