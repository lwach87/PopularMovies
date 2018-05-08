package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import com.example.lukaszwachowski.popularmovies.network.DetailService;
import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivityPresenter implements DetailActivityMVP.Presenter {

    private DetailActivityMVP.View view;
    private Subscription reviewSubscription = null;
    private Subscription videoSubscription = null;
    private DetailService detailService;

    public DetailActivityPresenter(DetailService detailService) {
        this.detailService = detailService;
    }

    @Override
    public void loadReviews(String movieId) {

        reviewSubscription = detailService.getReviews(movieId)
                .concatMap(reviews -> Observable.from(reviews.getResults()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReviewsResult>() {
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
                    public void onNext(ReviewsResult result) {
                        if (view != null) {
                            view.updateReviews(result);
                        }
                    }
                });
    }

    @Override
    public void loadVideos(String movieId) {

        videoSubscription = detailService.getVideos(movieId)
                .concatMap(videos -> Observable.from(videos.getResults()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideosResult>() {
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
                    public void onNext(VideosResult result) {
                        if (view != null) {
                            view.updateVideos(result);
                        }
                    }
                });
    }

    @Override
    public void reviewsUnSubscribe() {
        if (reviewSubscription != null) {
            if (!reviewSubscription.isUnsubscribed()) {
                reviewSubscription.unsubscribe();
            }
        }
    }

    @Override
    public void videosUnSubscribe() {
        if (videoSubscription != null) {
            if (!videoSubscription.isUnsubscribed()) {
                videoSubscription.unsubscribe();
            }
        }
    }

    @Override
    public void setView(DetailActivityMVP.View view) {
        this.view = view;
    }
}
