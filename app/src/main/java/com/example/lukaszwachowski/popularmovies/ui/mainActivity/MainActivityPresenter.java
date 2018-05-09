package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import com.example.lukaszwachowski.popularmovies.network.MovieService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivityPresenter implements MainActivityMVP.Presenter {

    private MainActivityMVP.View view;
    private CompositeDisposable disposable;
    private MovieService movieService;

    public MainActivityPresenter(MovieService movieService) {
        this.movieService = movieService;
        disposable = new CompositeDisposable();
    }

    @Override
    public void loadData(String sortingType) {

        disposable.add(
                movieService.getMovies(sortingType)
                        .concatMap(movies -> Observable.fromIterable(movies.getResults()))
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
