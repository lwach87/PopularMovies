package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.di.DetailActivityScope;
import com.example.lukaszwachowski.popularmovies.network.DetailService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.BASE_URL;

@Module
public class DetailServiceModule {

    @Provides
    @DetailActivityScope
    public DetailService detailService(Retrofit retrofit) {
        return retrofit.create(DetailService.class);
    }

    @Provides
    @DetailActivityScope
    public Retrofit retrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
