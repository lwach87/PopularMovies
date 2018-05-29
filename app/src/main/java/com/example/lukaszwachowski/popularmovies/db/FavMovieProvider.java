package com.example.lukaszwachowski.popularmovies.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.AUTHORITY;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.MOVIE_ID;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.SORT_ORDER;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.TABLE_NAME;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.buildMovieUri;

public class FavMovieProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDatabase mDbHelper;

    private static final int MOVIES = 100;
    private static final int MOVIES_ID = 101;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, TABLE_NAME, MOVIES);
        matcher.addURI(AUTHORITY, TABLE_NAME + "/#", MOVIES_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MoviesDatabase(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                return "vnd.android.cursor.dir/" + AUTHORITY + "/" + TABLE_NAME;
            case MOVIES_ID:
                return "vnd.android.cursor.item/" + AUTHORITY + "/" + TABLE_NAME;
            default:
                return null;
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor movieCursor;
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {

            case MOVIES: {
                builder.setTables(TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = SORT_ORDER;
                }
                break;
            }

            case MOVIES_ID: {
                builder.setTables(TABLE_NAME);
                builder.appendWhere(MOVIE_ID + " = " + uri.getLastPathSegment());
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        movieCursor = builder.query(
                mDbHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        movieCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return movieCursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIES: {
                long _id = mDbHelper.getWritableDatabase().insert(TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed when inserting the row into" + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        int rowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                rowsDeleted = mDbHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int rowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                rowsUpdated = mDbHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}