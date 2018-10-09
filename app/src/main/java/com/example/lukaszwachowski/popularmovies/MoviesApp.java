package com.example.lukaszwachowski.popularmovies;

import android.app.Activity;
import android.app.Application;
import com.example.lukaszwachowski.popularmovies.di.components.ApplicationComponent;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerApplicationComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.ContextModule;

public class MoviesApp extends Application {

  private ApplicationComponent component;

  public static MoviesApp get(Activity activity) {
    return (MoviesApp) activity.getApplication();
  }

  @Override
  public void onCreate() {
    super.onCreate();

    component = DaggerApplicationComponent.builder()
        .contextModule(new ContextModule(this))
        .build();
  }

  public ApplicationComponent component() {
    return component;
  }
}
