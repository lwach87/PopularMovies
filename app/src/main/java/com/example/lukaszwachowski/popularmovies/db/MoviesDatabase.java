package com.example.lukaszwachowski.popularmovies.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;

@Database(entities = MoviesResult.class, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {

  public abstract MovieDao movieDao();

//    private static final int DATABASE_VERSION = 1;
//    private static final String DATABASE_NAME = "favMovies.db";
//
//    public MoviesDatabase(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
//                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                MOVIE_ID + " TEXT UNIQUE NOT NULL," +
//                MOVIE_VOTE_AVERAGE + " DOUBLE NOT NULL," +
//                MOVIE_POSTER + " TEXT NOT NULL," +
//                MOVIE_TITLE + " TEXT NOT NULL," +
//                MOVIE_OVERVIEW + " TEXT NOT NULL," +
//                MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
//                "UNIQUE (" + MOVIE_ID + ") ON CONFLICT IGNORE" +
//                " );";
//
//        db.execSQL(SQL_CREATE_MOVIE_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("ALTER TABLE " + TABLE_NAME);
//        onCreate(db);
//    }
}
