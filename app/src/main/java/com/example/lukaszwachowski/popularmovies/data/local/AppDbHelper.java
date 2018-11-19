package com.example.lukaszwachowski.popularmovies.data.local;

import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDbHelper implements DbHelper {

  private MoviesDatabase moviesDatabase;

  @Inject
  public AppDbHelper(MoviesDatabase moviesDatabase) {
    this.moviesDatabase = moviesDatabase;
  }

  @Override
  public Flowable<List<MoviesResult>> getAllMovies() {
    return Flowable.fromCallable(() -> moviesDatabase.movieDao().getAllMovies());
  }

  @Override
  public Observable<Integer> isInFavourites(int id) {
    return Observable.fromCallable(() -> moviesDatabase.movieDao().isInFavourites(id));
  }

  @Override
  public Observable<Boolean> insertMovie(MoviesResult moviesResult) {
    return Observable.fromCallable(() -> {
      moviesDatabase.movieDao().insertMovie(moviesResult);
      return true;
    });
  }

  @Override
  public Observable<Boolean> deleteMovie(int id) {
    return Observable.fromCallable(() -> {
      moviesDatabase.movieDao().deleteMovie(id);
      return true;
    });
  }
}
