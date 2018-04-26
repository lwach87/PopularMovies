package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.di.DetailActivityScope;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailAdapter;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class DetailActivityModule {

    private final DetailActivity detailActivity;

    public DetailActivityModule(DetailActivity detailActivity) {
        this.detailActivity = detailActivity;
    }

    @Provides
    @DetailActivityScope
    public DetailAdapter detailAdapter(Picasso picasso) {
        return new DetailAdapter(detailActivity, picasso);
    }
}
