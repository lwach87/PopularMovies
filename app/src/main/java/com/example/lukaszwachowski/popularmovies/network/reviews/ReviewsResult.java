package com.example.lukaszwachowski.popularmovies.network.reviews;

import com.google.gson.annotations.SerializedName;

public class ReviewsResult {

  @SerializedName("author")
  private String author;

  @SerializedName("content")
  private String content;

  public String getAuthor() {
    return author;
  }

  public String getContent() {
    return content;
  }
}
