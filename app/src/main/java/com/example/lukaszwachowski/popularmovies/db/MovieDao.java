package com.example.lukaszwachowski.popularmovies.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    LiveData<List<MoviesResult>> getMovies();

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    MoviesResult getItemById(int movieId);

    @Insert(onConflict = REPLACE)
    void insertMovie(MoviesResult moviesResult);

    @Delete
    void deleteMovie(MoviesResult moviesResult);
}
