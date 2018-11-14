package com.example.lukaszwachowski.popularmovies.network.movies;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "movies")
public class MoviesResult implements Parcelable {

  @PrimaryKey
  @SerializedName("id")
  public int movieId;

  @SerializedName("vote_average")
  public double voteAverage;

  @SerializedName("poster_path")
  public String posterPath;

  @SerializedName("original_title")
  public String originalTitle;

  @SerializedName("backdrop_path")
  public String backdropPath;

  @SerializedName("overview")
  public String overview;

  @SerializedName("release_date")
  public String releaseDate;

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