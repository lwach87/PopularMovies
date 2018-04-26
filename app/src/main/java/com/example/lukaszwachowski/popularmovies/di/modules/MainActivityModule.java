package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainActivity;
import com.example.lukaszwachowski.popularmovies.di.MainActivityScope;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.ListAdapter;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    @MainActivityScope
    public ListAdapter listAdapter(Picasso picasso) {
        return new ListAdapter(mainActivity, picasso);
    }
}
