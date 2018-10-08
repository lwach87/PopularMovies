package com.example.lukaszwachowski.popularmovies.network.movies;

import java.util.List;

public class Movies {

  private int page;

  public List<MoviesResult> results = null;

  public int getPage() {
    return page;
  }
}
