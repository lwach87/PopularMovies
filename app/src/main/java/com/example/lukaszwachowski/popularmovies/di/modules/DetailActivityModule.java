package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.di.DetailActivityScope;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailAdapter;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.ReviewListAdapter;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.VideoListAdapter;
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

    @Provides
    @DetailActivityScope
    public ReviewListAdapter reviewListAdapter() {
        return new ReviewListAdapter(detailActivity);
    }

    @Provides
    @DetailActivityScope
    public VideoListAdapter videolistAdapter(Picasso picasso) {
        return new VideoListAdapter(detailActivity, picasso);
    }
}
