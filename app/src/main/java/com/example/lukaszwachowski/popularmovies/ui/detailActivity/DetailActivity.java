package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.hideView;
import static com.example.lukaszwachowski.popularmovies.configuration.ConfigureView.showView;
import static com.example.lukaszwachowski.popularmovies.configuration.Constants.IMAGE_URL;
import static com.example.lukaszwachowski.popularmovies.configuration.Constants.MOVIE_OBJECT;
import static com.example.lukaszwachowski.popularmovies.configuration.Constants.YOUTUBE_BASE_URL;

import android.content.Intent;
import android.net.Uri;
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
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.db.Repository;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;
import com.squareup.picasso.Picasso;
import dagger.android.AndroidInjection;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity implements DetailActivityMVP.View,
    View.OnClickListener, VideoListAdapter.OnItemClickListener {

  @Inject
  Picasso picasso;

  @Inject
  ReviewListAdapter reviewsAdapter;

  @Inject
  VideoListAdapter videosAdapter;

  @Inject
  DetailActivityMVP.Presenter presenter;

  @Inject
  Repository repository;

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

  @BindView(R.id.title_detail)
  TextView title_detail;

  @BindView(R.id.detail_poster)
  ImageView poster_detail;

  @BindView(R.id.date_detail)
  TextView date_detail;

  @BindView(R.id.rating_detail)
  TextView rating_detail;

  @BindView(R.id.plot_detail)
  TextView plot_detail;

  private MoviesResult result;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    ButterKnife.bind(this);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    AndroidInjection.inject(this);

    result = getIntent().getParcelableExtra(MOVIE_OBJECT);

    setupUI();

    getSupportActionBar().setTitle(result.originalTitle);
    presenter.loadData(String.valueOf(result.movieId));
    presenter.attachView(this);

    videoRecyclerView.setLayoutManager(new LinearLayoutManager(this,
        LinearLayoutManager.HORIZONTAL, false));
    videoRecyclerView.setAdapter(videosAdapter);
    videosAdapter.setListener(this);

    reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    reviewRecyclerView.setAdapter(reviewsAdapter);

    favourite.setOnClickListener(this);
  }

  private void setupUI() {
    Completable.fromAction(this::setIconFavorites).subscribeOn(Schedulers.io()).subscribe();

    title_detail.setText(result.originalTitle);
    date_detail.setText(result.releaseDate);
    rating_detail.setText(String.valueOf(result.voteAverage));
    plot_detail.setText(result.overview);
    picasso.load(IMAGE_URL + result.posterPath).into(poster_detail);
  }

  @Override
  public void onClick(View v) {
    Completable.fromAction(() -> {
      if (repository.isInFavourites(result.movieId) == 1) {
        repository.deleteMovie(result.movieId);
      } else {
        repository.insertMovie(result);
      }
      setIconFavorites();
    }).subscribeOn(Schedulers.io())
        .subscribe();
  }

  private void setIconFavorites() {
    if (repository.isInFavourites(result.movieId) == 1) {
      favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_red));
    } else {
      favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_grey));
    }
  }

  @Override
  public void onVideoClick(String key) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + key));
    startActivity(browserIntent);
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
