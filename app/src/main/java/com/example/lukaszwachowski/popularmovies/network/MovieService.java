package com.example.lukaszwachowski.popularmovies.network;

import com.example.lukaszwachowski.popularmovies.network.movies.Movies;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.API_KEY;

public interface MovieService {

    @GET("{sortingType}?api_key=" + API_KEY)
    Observable<Movies> getMovies(@Path("sortingType") String sortingType);
}
