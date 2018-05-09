package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private List<ReviewsResult> list = new ArrayList<>();
    private Context context;

    public ReviewListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.single_review_item, parent, false);
        return new ReviewListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewListAdapter.ViewHolder holder, int position) {

        holder.detailAuthor.setText(list.get(position).getAuthor());
        holder.detailReview.setText(list.get(position).getContent());
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

    public void swapData(ReviewsResult result) {
        list.add(result);
        notifyDataSetChanged();
    }
}
