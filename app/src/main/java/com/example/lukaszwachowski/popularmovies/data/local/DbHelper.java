package com.example.lukaszwachowski.popularmovies.data.local;

import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import io.reactivex.Flowable;
import java.util.List;

public interface DbHelper {

  Flowable<List<MoviesResult>> getAllMovies();

  int isInFavourites(int id);

  void insertMovie(MoviesResult moviesResult);

  void deleteMovie(int id);
}
