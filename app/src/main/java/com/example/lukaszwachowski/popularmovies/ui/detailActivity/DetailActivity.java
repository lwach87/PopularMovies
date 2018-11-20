package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import static com.example.lukaszwachowski.popularmovies.utils.Constants.IMAGE_URL;
import static com.example.lukaszwachowski.popularmovies.utils.Constants.MOVIE_OBJECT;
import static com.example.lukaszwachowski.popularmovies.utils.Constants.YOUTUBE_BASE_URL;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.ui.base.BaseActivity;
import com.squareup.picasso.Picasso;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class DetailActivity extends BaseActivity<DetailViewModel> implements
    View.OnClickListener, VideoListAdapter.OnItemClickListener {

  @Inject
  Picasso picasso;

  @Inject
  ReviewListAdapter reviewsAdapter;

  @Inject
  VideoListAdapter videosAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @BindView(R.id.details_toolbar)
  Toolbar toolbar;

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

  @BindView(R.id.iv_backdrop)
  ImageView backdrop_poster;

  @BindView(R.id.title_detail)
  TextView title_detail;

  @BindView(R.id.iv_detail_poster)
  ImageView poster_detail;

  @BindView(R.id.date_detail)
  TextView date_detail;

  @BindView(R.id.rating_detail)
  TextView rating_detail;

  @BindView(R.id.plot_detail)
  TextView plot_detail;

  @BindView(R.id.details_toolbar_collapsing)
  CollapsingToolbarLayout detailsCollapsingToolbar;

  private MoviesResult result;
  private DetailViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    ButterKnife.bind(this);

    result = getIntent().getParcelableExtra(MOVIE_OBJECT);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);

    if (toolbar != null) {
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayShowTitleEnabled(false);

      toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
      toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    if (detailsCollapsingToolbar != null) {
      detailsCollapsingToolbar.setTitle(result.getOriginalTitle());
    }

    setupUI();

    viewModel.fetchVideos(String.valueOf(result.getMovieId()), trailers);
    viewModel.fetchReviews(String.valueOf(result.getMovieId()), reviews);

    videoRecyclerView.setLayoutManager(new LinearLayoutManager(this,
        LinearLayoutManager.HORIZONTAL, false));
    videoRecyclerView.setAdapter(videosAdapter);
    videosAdapter.setListener(this);

    reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    reviewRecyclerView.setAdapter(reviewsAdapter);

    favourite.setOnClickListener(this);

    subscribeToLiveData();
  }

  private void setupUI() {
    Completable.fromAction(this::setIconFavorites).subscribeOn(Schedulers.io()).subscribe();

    title_detail.setText(result.getOriginalTitle());
    date_detail.setText(result.getReleaseDate());
    rating_detail.setText(String.valueOf(result.getVoteAverage()));
    plot_detail.setText(result.getOverview());
    picasso.load(IMAGE_URL + result.getPosterPath()).into(poster_detail);
    picasso.load(IMAGE_URL + result.getBackdropPath()).into(backdrop_poster);
  }

  @Override
  public void onClick(View v) {
    Completable.fromAction(() -> {
      if (viewModel.isInFavourites(result.getMovieId()) == 1) {
        viewModel.deleteMovie(result.getMovieId());
      } else {
        viewModel.insertMovie(result);
      }
      setIconFavorites();
    }).subscribeOn(Schedulers.io())
        .subscribe();
  }

  private void setIconFavorites() {
    if (viewModel.isInFavourites(result.getMovieId()) == 1) {
      favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_red));
    } else {
      favourite.setColorFilter(ContextCompat.getColor(this, R.color.fav_grey));
    }
  }

  private void subscribeToLiveData() {
    viewModel.getVideosLiveData()
        .observe(this, videosResults -> videosAdapter.swapData(videosResults));
    viewModel.getReviewsLiveData()
        .observe(this, reviewsResults -> reviewsAdapter.swapData(reviewsResults));
  }

  @Override
  public void onVideoClick(String key) {
    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + key));
    startActivity(browserIntent);
  }
}