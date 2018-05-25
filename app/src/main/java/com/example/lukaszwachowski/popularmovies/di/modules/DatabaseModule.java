package com.example.lukaszwachowski.popularmovies.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.lukaszwachowski.popularmovies.db.MovieDao;
import com.example.lukaszwachowski.popularmovies.db.MoviesDatabase;
import com.example.lukaszwachowski.popularmovies.db.MoviesRepository;
import com.example.lukaszwachowski.popularmovies.di.ApplicationScope;

import dagger.Module;
import dagger.Provides;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.DATABASE_NAME;

@Module
public class DatabaseModule {

    @Provides
    @ApplicationScope
    public MoviesDatabase moviesDatabase(Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                MoviesDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @ApplicationScope
    MovieDao provideDao(MoviesDatabase moviesDatabase) {
        return moviesDatabase.movieDao();
    }

    @Provides
    @ApplicationScope
    MoviesRepository provideMoviesRepository(MovieDao movieDao) {
        return new MoviesRepository(movieDao);
    }
}
