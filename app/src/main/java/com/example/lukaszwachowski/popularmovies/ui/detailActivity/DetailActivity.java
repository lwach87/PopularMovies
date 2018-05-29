package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.content.ContentValues;
import android.database.Cursor;
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

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerDetailActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.hideView;
import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.showView;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.MOVIE_OBJECT;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.CONTENT_URI;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.MOVIE_ID;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.MOVIE_OVERVIEW;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.MOVIE_POSTER;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.MOVIE_RELEASE_DATE;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.MOVIE_TITLE;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.MOVIE_VOTE_AVERAGE;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.buildMovieUri;

public class DetailActivity extends AppCompatActivity implements DetailActivityMVP.View, View.OnClickListener {

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

    @BindView(R.id.favourite_button)
    ImageView favourite;

    private MoviesResult result;
    private int id;

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
        id = result.getMovieId();
        presenter.loadData(String.valueOf(id));
        setIconFavorites();

        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        videoRecyclerView.setAdapter(videosAdapter);

        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewRecyclerView.setAdapter(reviewsAdapter);

        favourite.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (isInFavourites(id)) {

            getContentResolver().delete(CONTENT_URI, MOVIE_ID + " = ? ", new String[]{id + ""});

        } else {

            ContentValues values = new ContentValues();
            values.clear();

            values.put(MOVIE_ID, id);
            values.put(MOVIE_VOTE_AVERAGE, result.getVoteAverage());
            values.put(MOVIE_POSTER, result.getPosterPath());
            values.put(MOVIE_TITLE, result.getOriginalTitle());
            values.put(MOVIE_OVERVIEW, result.getOverview());
            values.put(MOVIE_RELEASE_DATE, result.getReleaseDate());

            getContentResolver().insert(CONTENT_URI, values);
        }
        setIconFavorites();
    }

    private void setIconFavorites() {
        if (isInFavourites(id)) {
            favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_red));
        } else {
            favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_grey));
        }
    }

    private boolean isInFavourites(int movieId) {

        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(buildMovieUri(movieId), null, null, null, null);
            if (cursor.moveToFirst())
                return true;
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return false;
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
