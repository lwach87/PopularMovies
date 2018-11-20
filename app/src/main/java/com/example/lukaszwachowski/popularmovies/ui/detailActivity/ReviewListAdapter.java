package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.data.model.reviews.ReviewsResult;
import java.util.ArrayList;
import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

  private List<ReviewsResult> list = new ArrayList<>();

  public ReviewListAdapter() {
  }

  @NonNull
  @Override
  public ReviewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.single_review_item, parent, false);
    return new ReviewListAdapter.ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ReviewListAdapter.ViewHolder holder, int position) {

    holder.detailAuthor.setText(list.get(position).author);
    holder.detailReview.setText(list.get(position).content);
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.detail_author)
    TextView detailAuthor;

    @BindView(R.id.detail_review)
    TextView detailReview;

    public ViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }

  public void swapData(List<ReviewsResult> newResult) {
    list.addAll(newResult);
    notifyDataSetChanged();
  }
}
