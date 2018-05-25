package com.example.lukaszwachowski.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.DATABASE_NAME;

@Database(entities = {MoviesResult.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static MoviesDatabase mInstance;

    public static MoviesDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, DATABASE_NAME).build();
            }
        }
        return mInstance;
    }

    public abstract MovieDao movieDao();
}
