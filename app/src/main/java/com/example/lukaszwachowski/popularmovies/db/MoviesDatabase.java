package com.example.lukaszwachowski.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;

@Database(entities = MoviesResult.class, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

  public abstract MovieDao movieDao();
}
