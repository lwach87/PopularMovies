package com.example.lukaszwachowski.popularmovies.network;

import com.example.lukaszwachowski.popularmovies.network.reviews.Reviews;
import com.example.lukaszwachowski.popularmovies.network.videos.Videos;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.API_KEY;

public interface DetailService {

    @GET("{movieId}/reviews?api_key=" + API_KEY)
    Observable<Reviews> getReviews(@Path("movieId") String movieId);

    @GET("{movieId}/videos?api_key=" + API_KEY)
    Observable<Videos> getVideos(@Path("movieId") String movieId);
}
