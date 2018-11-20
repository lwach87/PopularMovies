package com.example.lukaszwachowski.popularmovies.data.model.movies;

import java.util.List;

public class Movies {

  private int page;

  private List<MoviesResult> results = null;

  public List<MoviesResult> getResults() {
    return results;
  }

  public int getPage() {
    return page;
  }
}
