package com.example.lukaszwachowski.popularmovies.data.model.movies;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movies")
public class MoviesResult implements Parcelable {

  @PrimaryKey
  @SerializedName("id")
  private int movieId;

  @SerializedName("vote_average")
  private double voteAverage;

  @SerializedName("poster_path")
  private String posterPath;

  @SerializedName("original_title")
  private String originalTitle;

  @SerializedName("backdrop_path")
  private String backdropPath;

  @SerializedName("overview")
  private String overview;

  @SerializedName("release_date")
  private String releaseDate;

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(double voteAverage) {
    this.voteAverage = voteAverage;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public MoviesResult() {
  }

  protected MoviesResult(Parcel in) {
    movieId = in.readInt();
    voteAverage = in.readDouble();
    posterPath = in.readString();
    originalTitle = in.readString();
    backdropPath = in.readString();
    overview = in.readString();
    releaseDate = in.readString();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(movieId);
    parcel.writeDouble(voteAverage);
    parcel.writeString(posterPath);
    parcel.writeString(originalTitle);
    parcel.writeString(backdropPath);
    parcel.writeString(overview);
    parcel.writeString(releaseDate);
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
}