package com.example.lukaszwachowski.popularmovies.data.local;

import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;

public interface DbHelper {

  Flowable<List<MoviesResult>> getAllMovies();

  Single<MoviesResult> getMovieById(int id);

  void insertMovie(MoviesResult moviesResult);

  void deleteMovie(int id);
}
