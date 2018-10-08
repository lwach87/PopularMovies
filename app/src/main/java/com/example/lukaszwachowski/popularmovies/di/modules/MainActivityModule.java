package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.di.MainActivityScope;
import com.example.lukaszwachowski.popularmovies.network.MovieService;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.ListAdapter;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainActivity;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainActivityMVP;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainActivityPresenter;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainActivityModule {

  private MainActivity mainActivity;

  public MainActivityModule(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @Provides
  @MainActivityScope
  public ListAdapter listAdapter(Picasso picasso) {
    return new ListAdapter(mainActivity, picasso);
  }

  @Provides
  @MainActivityScope
  public MovieService movieService(Retrofit retrofit) {
    return retrofit.create(MovieService.class);
  }

  @Provides
  @MainActivityScope
  public MainActivityMVP.Presenter providePresenter(MovieService movieService) {
    return new MainActivityPresenter(movieService);
  }
}
