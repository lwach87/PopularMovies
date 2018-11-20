package com.example.lukaszwachowski.popularmovies.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import com.example.lukaszwachowski.popularmovies.ViewModelProviderFactory;
import com.example.lukaszwachowski.popularmovies.data.DataManager;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.ListAdapter;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainActivity;
import com.example.lukaszwachowski.popularmovies.ui.mainActivity.MainViewModel;
import com.example.lukaszwachowski.popularmovies.utils.rx.SchedulerProvider;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

  @Provides
  ListAdapter listAdapter(MainActivity mainActivity, Picasso picasso) {
    return new ListAdapter(mainActivity, picasso);
  }

  @Provides
  MainViewModel provideMainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    return new MainViewModel(dataManager, schedulerProvider);
  }

  @Provides
  ViewModelProvider.Factory mainViewModelProvider(MainViewModel mainViewModel) {
    return new ViewModelProviderFactory<>(mainViewModel);
  }
}
