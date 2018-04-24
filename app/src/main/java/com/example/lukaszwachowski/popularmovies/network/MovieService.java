package com.example.lukaszwachowski.popularmovies.network;

import com.example.lukaszwachowski.popularmovies.network.model.Movies;

import retrofit2.http.GET;
import rx.Observable;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.API_KEY;

public interface MovieService {

    @GET("top_rated?api_key=" + API_KEY + "&language=en-US&page=1")
    Observable<Movies> getMovies();
}
