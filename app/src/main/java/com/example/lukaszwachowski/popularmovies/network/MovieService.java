package com.example.lukaszwachowski.popularmovies.network;

import com.example.lukaszwachowski.popularmovies.network.model.Result;

import retrofit2.http.GET;
import rx.Observable;

public interface MovieService {

    @GET("top_rated?api_key=26ae9389aea811fc3942a97a387102e7&language=en-US&page=1")
    Observable<Result> getMovies();
}
