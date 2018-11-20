package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import android.arch.lifecycle.MutableLiveData;
import com.example.lukaszwachowski.popularmovies.data.DataManager;
import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.ui.base.BaseViewModel;
import com.example.lukaszwachowski.popularmovies.utils.rx.SchedulerProvider;
import java.util.List;

public class MainViewModel extends BaseViewModel {

  private MutableLiveData<List<MoviesResult>> moviesLiveData;

  public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    super(dataManager, schedulerProvider);
    moviesLiveData = new MutableLiveData<>();
  }

  public void fetchMovies(String sortingType) {
    getDisposable().add(getDataManager()
        .getMovies(sortingType, 1)
        .concatWith(getDataManager().getMovies(sortingType, 2))
        .concatWith(getDataManager().getMovies(sortingType, 3))
        .concatWith(getDataManager().getMovies(sortingType, 4))
        .concatWith(getDataManager().getMovies(sortingType, 5))
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(result -> moviesLiveData.setValue(result.getResults())));
  }

  public void setFavourites() {
    getDisposable().add(getDataManager()
        .getAllMovies()
        .subscribeOn(getSchedulerProvider().io())
        .observeOn(getSchedulerProvider().ui())
        .subscribe(results -> moviesLiveData.setValue(results)));
  }

  public MutableLiveData<List<MoviesResult>> getMoviesLiveData() {
    return moviesLiveData;
  }
}
