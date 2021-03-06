package com.example.lukaszwachowski.popularmovies.data.remote;

import static com.example.lukaszwachowski.popularmovies.utils.Constants.API_KEY;

import com.example.lukaszwachowski.popularmovies.data.model.movies.Movies;
import com.example.lukaszwachowski.popularmovies.data.model.reviews.Reviews;
import com.example.lukaszwachowski.popularmovies.data.model.videos.Videos;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHelper {

  @GET("{sortingType}?api_key=" + API_KEY)
  Observable<Movies> getMovies(@Path("sortingType") String sortingType, @Query("page") int page);

  @GET("{movieId}/reviews?api_key=" + API_KEY)
  Observable<Reviews> getReviews(@Path("movieId") String movieId);

  @GET("{movieId}/videos?api_key=" + API_KEY)
  Observable<Videos> getVideos(@Path("movieId") String movieId);
}
