package com.example.lukaszwachowski.popularmovies.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import com.example.lukaszwachowski.popularmovies.ViewModelProviderFactory;
import com.example.lukaszwachowski.popularmovies.data.DataManager;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailViewModel;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.ReviewListAdapter;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.VideoListAdapter;
import com.example.lukaszwachowski.popularmovies.utils.rx.SchedulerProvider;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

@Module
public class DetailActivityModule {

  @Provides
  ReviewListAdapter reviewListAdapter() {
    return new ReviewListAdapter();
  }

  @Provides
  VideoListAdapter videolistAdapter(Picasso picasso) {
    return new VideoListAdapter(picasso);
  }

  @Provides
  DetailViewModel provideDetailViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    return new DetailViewModel(dataManager, schedulerProvider);
  }

  @Provides
  ViewModelProvider.Factory detailViewModelProvider(DetailViewModel detailViewModel) {
    return new ViewModelProviderFactory<>(detailViewModel);
  }
}
