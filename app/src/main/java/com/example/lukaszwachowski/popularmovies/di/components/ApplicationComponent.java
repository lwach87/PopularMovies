package com.example.lukaszwachowski.popularmovies.di.components;

import com.example.lukaszwachowski.popularmovies.db.Repository;
import com.example.lukaszwachowski.popularmovies.di.modules.ContextModule;
import com.squareup.picasso.Picasso;
import dagger.Component;
import javax.inject.Singleton;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ContextModule.class)
public interface ApplicationComponent {

  Picasso getPicasso();

  Retrofit getRetrofit();

  Repository getRepository();
}
