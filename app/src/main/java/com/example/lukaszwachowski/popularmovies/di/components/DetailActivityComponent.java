package com.example.lukaszwachowski.popularmovies.di.components;


import com.example.lukaszwachowski.popularmovies.di.DetailActivityScope;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;
import dagger.Component;

@DetailActivityScope
@Component(modules = DetailActivityModule.class, dependencies = ApplicationComponent.class)
public interface DetailActivityComponent {

  void inject(DetailActivity detailActivity);
}
