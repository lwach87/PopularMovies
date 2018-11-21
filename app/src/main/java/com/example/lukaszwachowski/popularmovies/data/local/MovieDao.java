package com.example.lukaszwachowski.popularmovies.data.local;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import java.util.List;

@Dao
public interface MovieDao {

  @Query("SELECT * FROM movies")
  List<MoviesResult> getAllMovies();

  @Query("SELECT * FROM movies WHERE movieId = :id")
  MoviesResult getMovieById(int id);

  @Insert(onConflict = REPLACE)
  void insertMovie(MoviesResult moviesResult);

  @Query("DELETE FROM movies WHERE movieId = :id")
  void deleteMovie(int id);
}
