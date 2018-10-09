package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;

public class MainActivityMVP {

  interface View {

    void updateData(MoviesResult result);

    void showSnackBar(String text);
  }

  public interface Presenter {

    void loadData(String sortingType);

    void detachView();

    void attachView(MainActivityMVP.View view);
  }
}
