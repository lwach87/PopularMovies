package com.example.lukaszwachowski.popularmovies.di.builder;

import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;
import com.example.lukaszwachowski.popularmovies.di.modules.MainActivityModule;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

  @ContributesAndroidInjector(modules = MainActivityModule.class)
  abstract MainActivity bindMainActivity();

  @ContributesAndroidInjector(modules = DetailActivityModule.class)
  abstract DetailActivity bindDetailActivity();
}
