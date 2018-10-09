package com.example.lukaszwachowski.popularmovies.db;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import io.reactivex.Flowable;
import java.util.List;

@Dao
public interface MovieDao {

  @Query("SELECT * FROM movies")
  Flowable<List<MoviesResult>> getAllMovies();

  @Query("SELECT count(*) FROM movies WHERE movieId = :id")
  int isInFavourites(int id);

  @Insert(onConflict = REPLACE)
  void insertMovie(MoviesResult moviesResult);

  @Query("DELETE FROM movies WHERE movieId = :id")
  void deleteMovie(int id);
}
