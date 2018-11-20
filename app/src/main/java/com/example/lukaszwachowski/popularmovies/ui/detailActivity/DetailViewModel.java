package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.arch.lifecycle.MutableLiveData;
import android.widget.TextView;
import com.example.lukaszwachowski.popularmovies.data.DataManager;
import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.data.model.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.data.model.videos.VideosResult;
import com.example.lukaszwachowski.popularmovies.ui.base.BaseViewModel;
import com.example.lukaszwachowski.popularmovies.utils.rx.SchedulerProvider;
import java.util.List;

public class DetailViewModel extends BaseViewModel {

  private MutableLiveData<List<VideosResult>> videosLiveData;
  private MutableLiveData<List<ReviewsResult>> reviewsLiveData;

  public DetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    super(dataManager, schedulerProvider);
    videosLiveData = new MutableLiveData<>();
    reviewsLiveData = new MutableLiveData<>();
  }

  public void fetchVideos(String movieId, TextView videos) {
    getDisposable().add(getDataManager()
        .getVideos(movieId)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(result -> {
          showIcon(videos, result.getResults().isEmpty());
          videosLiveData.setValue(result.getResults());
        })
    );
  }

  public void fetchReviews(String movieId, TextView reviews) {
    getDisposable().add(getDataManager()
        .getReviews(movieId)
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(result -> {
          showIcon(reviews, result.getResults().isEmpty());
          reviewsLiveData.setValue(result.getResults());
        })
    );
  }

  public int isInFavourites(int id) {
    return getDataManager()
        .isInFavourites(id);
  }

  public void insertMovie(MoviesResult moviesResult) {
    getDataManager().insertMovie(moviesResult);
  }

  public void deleteMovie(int id) {
    getDataManager().deleteMovie(id);
  }

  public MutableLiveData<List<VideosResult>> getVideosLiveData() {
    return videosLiveData;
  }

  public MutableLiveData<List<ReviewsResult>> getReviewsLiveData() {
    return reviewsLiveData;
  }
}
