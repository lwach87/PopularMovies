package com.example.lukaszwachowski.popularmovies.network.movies;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MoviesResult implements Parcelable {

    @SerializedName("id")
    private int movieId;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public MoviesResult(int movieId, double voteAverage, String posterPath, String originalTitle, String overview, String releaseDate) {
        this.movieId = movieId;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    protected MoviesResult(Parcel in) {
        movieId = in.readInt();
        voteAverage = in.readDouble();
        posterPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeDouble(voteAverage);
        dest.writeString(posterPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MoviesResult> CREATOR = new Creator<MoviesResult>() {
        @Override
        public MoviesResult createFromParcel(Parcel in) {
            return new MoviesResult(in);
        }

        @Override
        public MoviesResult[] newArray(int size) {
            return new MoviesResult[size];
        }
    };

    public int getMovieId() {
        return movieId;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}