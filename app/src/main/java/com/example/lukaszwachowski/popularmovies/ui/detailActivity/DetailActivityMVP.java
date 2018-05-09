package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;

public class DetailActivityMVP {

    interface View {

        void updateReviews(ReviewsResult result);

        void updateVideos(VideosResult result);

        void showReview(boolean show);

        void showTrailer(boolean show);

        void showSnackBar(String text);
    }

    public interface Presenter {

        void loadData(String movieId);

        void detachView();

        void attachView(DetailActivityMVP.View view);
    }
}
