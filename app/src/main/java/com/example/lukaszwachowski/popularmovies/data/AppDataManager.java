package com.example.lukaszwachowski.popularmovies.data;

import com.example.lukaszwachowski.popularmovies.data.local.DbHelper;
import com.example.lukaszwachowski.popularmovies.data.model.movies.Movies;
import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.data.model.reviews.Reviews;
import com.example.lukaszwachowski.popularmovies.data.model.videos.Videos;
import com.example.lukaszwachowski.popularmovies.data.remote.ApiHelper;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager implements DataManager {

  private DbHelper dbHelper;
  private ApiHelper apiHelper;

  @Inject
  public AppDataManager(DbHelper dbHelper, ApiHelper apiHelper) {
    this.dbHelper = dbHelper;
    this.apiHelper = apiHelper;
  }

  @Override
  public Flowable<List<MoviesResult>> getAllMovies() {
    return dbHelper.getAllMovies();
  }

  @Override
  public Observable<Integer> isInFavourites(int id) {
    return dbHelper.isInFavourites(id);
  }

  @Override
  public Observable<Boolean> insertMovie(MoviesResult moviesResult) {
    return dbHelper.insertMovie(moviesResult);
  }

  @Override
  public Observable<Boolean> deleteMovie(int id) {
    return dbHelper.deleteMovie(id);
  }

  @Override
  public Observable<Movies> getMovies(String sortingType, int page) {
    return apiHelper.getMovies(sortingType, page);
  }

  @Override
  public Observable<Reviews> getReviews(String movieId) {
    return apiHelper.getReviews(movieId);
  }

  @Override
  public Observable<Videos> getVideos(String movieId) {
    return apiHelper.getVideos(movieId);
  }
}
