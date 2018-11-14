package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import com.example.lukaszwachowski.popularmovies.network.MovieService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivityPresenter implements MainActivityMVP.Presenter {

  private MainActivityMVP.View view;
  private CompositeDisposable disposable = new CompositeDisposable();
  private MovieService movieService;

  public MainActivityPresenter(MovieService movieService) {
    this.movieService = movieService;
  }

  @Override
  public void loadData(String sortingType) {
    disposable.add(
        movieService.getMovies(sortingType, 1)
            .concatWith(movieService.getMovies(sortingType, 2))
            .concatWith(movieService.getMovies(sortingType, 3))
            .concatWith(movieService.getMovies(sortingType, 4))
            .concatWith(movieService.getMovies(sortingType, 5))
            .concatMap(movies -> Observable.fromIterable(movies.results))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> {
              if (view != null) {
                view.updateData(result);
              }
            }, error -> {
              if (view != null) {
                view.showSnackBar(error.getLocalizedMessage());
              }
            })
    );
  }

  @Override
  public void attachView(MainActivityMVP.View view) {
    this.view = view;
  }

  @Override
  public void detachView() {
    disposable.clear();
  }
}
