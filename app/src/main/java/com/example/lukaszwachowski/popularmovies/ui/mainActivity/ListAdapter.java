package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import static com.example.lukaszwachowski.popularmovies.configuration.Constants.IMAGE_URL;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.DataViewHolder> {

  private List<MoviesResult> results = new ArrayList<>();
  private Context context;
  private Picasso picasso;
  private OnItemClickListener listener;

  public ListAdapter(Context context, Picasso picasso) {
    this.context = context;
    this.picasso = picasso;
  }

  public void setListener(OnItemClickListener listener) {
    this.listener = listener;
  }

  public interface OnItemClickListener {

    void onItemClick(MoviesResult result);
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

    picasso.load(IMAGE_URL + results.get(position).posterPath)
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
      listener.onItemClick(results.get(getAdapterPosition()));
    }
  }

  public void swapData(MoviesResult result) {
    results.add(result);
    notifyDataSetChanged();
  }

  public void clearData() {
    results.clear();
  }
}
