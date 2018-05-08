package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import com.example.lukaszwachowski.popularmovies.network.reviews.ReviewsResult;
import com.example.lukaszwachowski.popularmovies.network.videos.VideosResult;

public class DetailActivityMVP {

    interface View {

        void updateReviews(ReviewsResult result);

        void updateVideos(VideosResult result);

        void showSnackBar();
    }

    public interface Presenter {

        void loadReviews(String movieId);

        void loadVideos(String movieId);

        void reviewsUnSubscribe();

        void videosUnSubscribe();

        void setView(DetailActivityMVP.View view);
    }
}
