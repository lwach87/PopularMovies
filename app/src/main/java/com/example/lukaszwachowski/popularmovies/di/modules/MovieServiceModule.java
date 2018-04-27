package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.di.MainActivityScope;
import com.example.lukaszwachowski.popularmovies.network.MovieService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.BASE_URL;

@Module
public class MovieServiceModule {

    @Provides
    @MainActivityScope
    public MovieService movieService(Retrofit retrofit) {
        return retrofit.create(MovieService.class);
    }

    @Provides
    @MainActivityScope
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

}