package com.example.lukaszwachowski.popularmovies.di.modules;

import static com.example.lukaszwachowski.popularmovies.utils.Constants.BASE_URL;
import static com.example.lukaszwachowski.popularmovies.utils.Constants.DATABASE_NAME;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import com.example.lukaszwachowski.popularmovies.data.AppDataManager;
import com.example.lukaszwachowski.popularmovies.data.DataManager;
import com.example.lukaszwachowski.popularmovies.data.local.AppDbHelper;
import com.example.lukaszwachowski.popularmovies.data.local.DbHelper;
import com.example.lukaszwachowski.popularmovies.data.local.MoviesDatabase;
import com.example.lukaszwachowski.popularmovies.data.remote.ApiHelper;
import com.example.lukaszwachowski.popularmovies.utils.rx.AppSchedulerProvider;
import com.example.lukaszwachowski.popularmovies.utils.rx.SchedulerProvider;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

  @Provides
  @Singleton
  Context provideContext(Application application) {
    return application;
  }

  @Provides
  @Singleton
  Picasso picasso(Context context) {
    return new Picasso.Builder(context).build();
  }

  @Provides
  @Singleton
  MoviesDatabase provideDatabase(Context context) {
    return Room.databaseBuilder(context, MoviesDatabase.class,
        DATABASE_NAME).build();
  }

  @Provides
  @Singleton
  Retrofit retrofit() {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  @Provides
  @Singleton
  ApiHelper provideApiHelper(Retrofit retrofit) {
    return retrofit.create(ApiHelper.class);
  }

  @Provides
  @Singleton
  DbHelper provideDbHelper(AppDbHelper appDbHelper) {
    return appDbHelper;
  }

  @Provides
  @Singleton
  DataManager provideDataManager(AppDataManager appDataManager) {
    return appDataManager;
  }

  @Provides
  SchedulerProvider provideSchedulerProvider() {
    return new AppSchedulerProvider();
  }
}
