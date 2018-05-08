package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerDetailActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;
import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoRecyclerView.setNestedScrollingEnabled(false);
        videoRecyclerView.setHasFixedSize(true);
        videoRecyclerView.setAdapter(videosAdapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setAdapter(reviewsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);

        videosAdapter.clearData();

        String movieId = String.valueOf(getIntent().getIntExtra("id", 1));
        presenter.loadReviews(movieId);
        presenter.loadVideos(movieId);
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
    public void showSnackBar() {
        Snackbar.make(rootView, "No internet connection", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reviewsAdapter.clearData();
        videosAdapter.clearData();
        presenter.reviewsUnSubscribe();
        presenter.videosUnSubscribe();
    }
}
