package com.example.lukaszwachowski.popularmovies.di.components;

import com.example.lukaszwachowski.popularmovies.di.MainActivityScope;
import com.example.lukaszwachowski.popularmovies.di.modules.MainActivityMVPModule;
import com.example.lukaszwachowski.popularmovies.di.modules.MainActivityModule;
import com.example.lukaszwachowski.popularmovies.di.modules.MovieServiceModule;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainActivity;

import dagger.Component;

@MainActivityScope
@Component(modules = {MovieServiceModule.class, MainActivityModule.class, MainActivityMVPModule.class},
        dependencies = ApplicationComponent.class)
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
