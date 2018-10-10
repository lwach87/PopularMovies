package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import com.example.lukaszwachowski.popularmovies.network.DetailService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DetailActivityPresenter implements DetailActivityMVP.Presenter {

  private DetailActivityMVP.View view;
  private CompositeDisposable reviewDisposable, videoDisposable;
  private DetailService detailService;

  public DetailActivityPresenter(DetailService detailService) {
    this.detailService = detailService;
    reviewDisposable = new CompositeDisposable();
    videoDisposable = new CompositeDisposable();
  }

  @Override
  public void loadData(String movieId) {

    reviewDisposable.add(
        detailService.getReviews(movieId)
            .concatMap(reviews -> Observable.fromIterable(reviews.results))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> {

              view.showReview(result != null);

              if (view != null) {
                view.updateReviews(result);
              }
            }, error -> {
              if (view != null) {
                view.showSnackBar(error.getLocalizedMessage());
              }
            })
    );

    videoDisposable.add(
        detailService.getVideos(movieId)
            .concatMap(videos -> Observable.fromIterable(videos.results))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(result -> {

              view.showTrailer(result != null);

              if (view != null) {
                view.updateVideos(result);
              }
            }, error -> {
              if (view != null) {
                view.showSnackBar(error.getLocalizedMessage());
              }
            })
    );
  }

    /*

    Downloading in one presenter

    public Observable<VideosResult> result(String sortingType) {

        return movieService.getMovies(sortingType)
                .concatMap(movies -> Observable.fromIterable(movies.getResults()))
                .concatMap(moviesResult -> detailService.getVideos(String.valueOf(moviesResult.getMovieId()))
                        .concatMap(videos -> Observable.fromIterable(videos.getResults())));
    }
    */

  @Override
  public void attachView(DetailActivityMVP.View view) {
    this.view = view;
  }

  @Override
  public void detachView() {
    reviewDisposable.clear();
    videoDisposable.clear();
  }
}
