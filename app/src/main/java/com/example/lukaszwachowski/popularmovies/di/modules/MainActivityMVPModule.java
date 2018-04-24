package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.di.MainActivityScope;
import com.example.lukaszwachowski.popularmovies.network.MovieService;
import com.example.lukaszwachowski.popularmovies.ui.MainActivityMVP;
import com.example.lukaszwachowski.popularmovies.ui.MainActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityMVPModule {

    @Provides
    @MainActivityScope
    public MainActivityMVP.Presenter providePresenter(MovieService service) {
        return new MainActivityPresenter(service);
    }
}
