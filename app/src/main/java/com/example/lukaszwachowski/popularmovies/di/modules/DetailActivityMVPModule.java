package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.di.DetailActivityScope;
import com.example.lukaszwachowski.popularmovies.network.DetailService;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivityMVP;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivityPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailActivityMVPModule {

    @Provides
    @DetailActivityScope
    public DetailActivityMVP.Presenter providePresenter(DetailService detailService) {
        return new DetailActivityPresenter(detailService);
    }
}
