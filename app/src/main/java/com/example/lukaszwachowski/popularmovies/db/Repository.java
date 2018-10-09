package com.example.lukaszwachowski.popularmovies.db;

import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import io.reactivex.Flowable;
import java.util.List;

public class Repository {

  private MovieDao movieDao;

  public Repository(MovieDao movieDao) {
    this.movieDao = movieDao;
  }

  public Flowable<List<MoviesResult>> getAllMovies() {
    return movieDao.getAllMovies();
  }

  public int isInFavourites(int id) {
    return movieDao.isInFavourites(id);
  }

  public void insertMovie(MoviesResult moviesResult) {
    movieDao.insertMovie(moviesResult);
  }

  public void deleteMovie(int id) {
    movieDao.deleteMovie(id);
  }
}
