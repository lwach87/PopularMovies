package com.example.lukaszwachowski.popularmovies.di.modules;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.BASE_URL;

import android.content.Context;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ContextModule {

  private Context context;

  public ContextModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context context() {
    return context;
  }

  @Provides
  @Singleton
  public Picasso picasso(Context context) {
    return new Picasso.Builder(context).build();
  }

  @Provides
  @Singleton
  public Retrofit retrofit() {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }
}
