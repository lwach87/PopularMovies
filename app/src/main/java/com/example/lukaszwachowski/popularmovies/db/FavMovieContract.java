package com.example.lukaszwachowski.popularmovies.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavMovieContract {

    public static final String AUTHORITY = "com.example.lukaszwachowski.popularmovies";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_ID = "id";
        public static final String MOVIE_TITLE = "original_title";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_RELEASE_DATE = "release_date";
        public static final String MOVIE_POSTER = "poster_path";
        public static final String MOVIE_VOTE_AVERAGE = "vote_average";

        public static final Uri CONTENT_URI = BASE_URI.buildUpon().appendPath(TABLE_NAME).build();
        public static final String SORT_ORDER = MOVIE_VOTE_AVERAGE + " ASC";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
