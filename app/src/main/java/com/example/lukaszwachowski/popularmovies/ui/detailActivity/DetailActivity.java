package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerDetailActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;
import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.hideView;
import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.showView;

public class DetailActivity extends AppCompatActivity implements DetailActivityMVP.View {

    @Inject
    DetailAdapter detailAdapter;

    @Inject
    ReviewListAdapter reviewsAdapter;

    @Inject
    VideoListAdapter videosAdapter;

    @Inject
    DetailActivityMVP.Presenter presenter;

    @BindView(R.id.reviews_recycler_view)
    RecyclerView reviewRecyclerView;

    @BindView(R.id.videos_recycler_view)
    RecyclerView videoRecyclerView;

    @BindView(R.id.reviews)
    TextView reviews;

    @BindView(R.id.trailers)
    TextView trailers;

    @BindView(R.id.activity_detail)
    ViewGroup rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Detail Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DaggerDetailActivityComponent.builder()
                .detailActivityModule(new DetailActivityModule(this))
                .applicationComponent(MoviesApp.get(this).component())
                .build().inject(this);

        setContentView(detailAdapter);

        ButterKnife.bind(this);
        presenter.attachView(this);
        presenter.loadData(String.valueOf(getIntent().getIntExtra("id", 1)));

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoRecyclerView.setAdapter(videosAdapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewRecyclerView.setAdapter(reviewsAdapter);
    }

    @Override
    public void updateReviews(ReviewsResult result) {
        reviewsAdapter.swapData(result);
    }

    @Override
    public void updateVideos(VideosResult result) {
        videosAdapter.swapData(result);
    }

    @Override
    public void showReview(boolean show) {
        if (show) {
            showView(reviews);
        } else {
            hideView(reviews);
        }
    }

    @Override
    public void showTrailer(boolean show) {
        if (show) {
            showView(trailers);
        } else {
            hideView(trailers);
        }
    }

    @Override
    public void showSnackBar(String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
