package com.example.lukaszwachowski.popularmovies.di.modules;

import com.example.lukaszwachowski.popularmovies.network.DetailService;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivityMVP;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivityPresenter;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.ReviewListAdapter;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.VideoListAdapter;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DetailActivityModule {

  @Provides
  public ReviewListAdapter reviewListAdapter() {
    return new ReviewListAdapter();
  }

  @Provides
  public VideoListAdapter videolistAdapter(Picasso picasso) {
    return new VideoListAdapter(picasso);
  }

  @Provides
  public DetailService detailService(Retrofit retrofit) {
    return retrofit.create(DetailService.class);
  }

  @Provides
  public DetailActivityMVP.Presenter providePresenter(DetailService detailService) {
    return new DetailActivityPresenter(detailService);
  }
}
