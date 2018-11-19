package com.example.lukaszwachowski.popularmovies.network;

import com.example.lukaszwachowski.popularmovies.data.model.movies.Movies;
import com.example.lukaszwachowski.popularmovies.utils.Constants;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

  @GET("{sortingType}?api_key=" + Constants.API_KEY)
  Observable<Movies> getMovies(@Path("sortingType") String sortingType, @Query("page") int page);
}
