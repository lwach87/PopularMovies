package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import com.example.lukaszwachowski.popularmovies.network.MovieService;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivityPresenter implements MainActivityMVP.Presenter {

    private MainActivityMVP.View view;
    private Subscription subscription = null;
    private MovieService movieService;

    public MainActivityPresenter(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void loadData(String sortingType) {

        subscription = movieService.getMovies(sortingType)
                .concatMap(movies -> Observable.from(movies.getResults()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MoviesResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (view != null) {
                            view.showSnackBar();
                        }
                    }

                    @Override
                    public void onNext(MoviesResult result) {
                        if (view != null) {
                            view.updateData(result);
                        }
                    }
                });
    }

    @Override
    public void rxUnSubscribe() {
        if (subscription != null) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }

    @Override
    public void setView(MainActivityMVP.View view) {
        this.view = view;
    }
}
