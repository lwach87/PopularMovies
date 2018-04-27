package com.example.lukaszwachowski.popularmovies.network;

import com.example.lukaszwachowski.popularmovies.network.model.Movies;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.API_KEY;

public interface MovieService {

    @GET("{sortingType}?api_key=" + API_KEY + "&language=en-US&page=1")
    Observable<Movies> getMovies(@Path("sortingType") String sortingType);
}
