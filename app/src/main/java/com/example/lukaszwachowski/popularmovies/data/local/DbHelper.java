package com.example.lukaszwachowski.popularmovies.data.local;

import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import java.util.List;

public interface DbHelper {

  Flowable<List<MoviesResult>> getAllMovies();

  Observable<Integer> isInFavourites(int id);

  Observable<Boolean> insertMovie(MoviesResult moviesResult);

  Observable<Boolean> deleteMovie(int id);
}
