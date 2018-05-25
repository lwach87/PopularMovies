package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.db.MoviesRepository;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerDetailActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;
import com.example.lukaszwachowski.popularmovies.utils.AppExecutors;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.hideView;
import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.showView;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.MOVIE_OBJECT;

public class DetailActivity extends AppCompatActivity implements DetailActivityMVP.View, View.OnClickListener {

    @Inject
    DetailAdapter detailAdapter;

    @Inject
    ReviewListAdapter reviewsAdapter;

    @Inject
    VideoListAdapter videosAdapter;

    @Inject
    DetailActivityMVP.Presenter presenter;

    @Inject
    MoviesRepository repository;

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

    @BindView(R.id.favourite_button)
    ImageView favourite;

    private MoviesResult result;

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

        result = getIntent().getParcelableExtra(MOVIE_OBJECT);
        presenter.loadData(String.valueOf(result.getMovieId()));

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoRecyclerView.setAdapter(videosAdapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewRecyclerView.setAdapter(reviewsAdapter);

        favourite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (isInFavourites(result.getMovieId())) {

            AppExecutors.getInstance().diskIO()
                    .execute(() -> repository.deleteMovie(repository.findMovieById(result.getMovieId())));
            favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_grey));

        } else {

            AppExecutors.getInstance().diskIO().execute(() -> repository.insertMovie(result));
            favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_red));

        }
    }

    private boolean isInFavourites(int movieId) {

        final int[] local = new int[1];

        AppExecutors.getInstance().diskIO().execute(() -> {
            MoviesResult movieById = repository.findMovieById(movieId);
            local[0] = (movieById == null) ? 0 : movieById.getMovieId();
        });

        Toast.makeText(this, "local =" + local[0] + " " + "movieId =" + movieId, Toast.LENGTH_SHORT).show();

        return local[0] == movieId;
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
