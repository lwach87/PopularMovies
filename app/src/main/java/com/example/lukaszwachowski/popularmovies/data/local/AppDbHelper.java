package com.example.lukaszwachowski.popularmovies.data.local;

import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import io.reactivex.Flowable;
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
  public int isInFavourites(int id) {
    return moviesDatabase.movieDao().isInFavourites(id);
  }

  @Override
  public void insertMovie(MoviesResult moviesResult) {
    moviesDatabase.movieDao().insertMovie(moviesResult);
  }

  @Override
  public void deleteMovie(int id) {
    moviesDatabase.movieDao().deleteMovie(id);
  }
}
