package com.example.lukaszwachowski.popularmovies.di.components;

import android.app.Application;
import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.di.builder.ActivityBuilder;
import com.example.lukaszwachowski.popularmovies.di.modules.AppModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

  void inject(MoviesApp app);

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }
}
