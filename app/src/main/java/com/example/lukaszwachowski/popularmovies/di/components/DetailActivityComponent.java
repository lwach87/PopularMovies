package com.example.lukaszwachowski.popularmovies.di.components;


import com.example.lukaszwachowski.popularmovies.di.DetailActivityScope;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityMVPModule;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailServiceModule;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;

import dagger.Component;

@DetailActivityScope
@Component(modules = {DetailServiceModule.class, DetailActivityModule.class, DetailActivityMVPModule.class},
        dependencies = ApplicationComponent.class)
public interface DetailActivityComponent {
    void inject(DetailActivity detailActivity);
}
