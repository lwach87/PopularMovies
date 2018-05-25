package com.example.lukaszwachowski.popularmovies.db;

import android.arch.lifecycle.LiveData;

import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;

import java.util.List;

public class MoviesRepository {

    private MovieDao movieDao;

    public MoviesRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public LiveData<List<MoviesResult>> getFavouriteMovies() {
        return movieDao.getMovies();
    }

    public MoviesResult findMovieById(int movieId) {
        return movieDao.getItemById(movieId);
    }

    public void insertMovie(MoviesResult moviesResult) {
        movieDao.insertMovie(moviesResult);
    }

    public void deleteMovie(MoviesResult moviesResult) {
        movieDao.deleteMovie(moviesResult);
    }
}
