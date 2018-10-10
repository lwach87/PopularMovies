package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import static com.example.lukaszwachowski.popularmovies.configuration.Constants.YOUTUBE_IMG;

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
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

  private List<VideosResult> list = new ArrayList<>();
  private Context context;
  private Picasso picasso;
  private OnItemClickListener listener;

  public VideoListAdapter(Context context, Picasso picasso) {
    this.context = context;
    this.picasso = picasso;
  }

  public void setListener(OnItemClickListener onItemClickListener) {
    this.listener = onItemClickListener;
  }

  public interface OnItemClickListener {

    void onVideoClick(String key);
  }

  @NonNull
  @Override
  public VideoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context)
        .inflate(R.layout.single_video_item, parent, false);
    return new VideoListAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull VideoListAdapter.ViewHolder holder, int position) {

    picasso.load(YOUTUBE_IMG + list.get(position).key + "/mqdefault.jpg")
        .into(holder.detailTrailer);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.detail_trailer)
    ImageView detailTrailer;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);

      detailTrailer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      listener.onVideoClick(list.get(getAdapterPosition()).key);
    }
  }

  public void swapData(VideosResult result) {
    list.add(result);
    notifyDataSetChanged();
  }
}
