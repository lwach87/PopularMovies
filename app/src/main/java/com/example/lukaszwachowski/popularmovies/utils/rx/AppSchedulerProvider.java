package com.example.lukaszwachowski.popularmovies.utils.rx;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AppSchedulerProvider implements SchedulerProvider {

  @Override
  public Scheduler io() {
    return Schedulers.io();
  }

  @Override
  public Scheduler ui() {
    return AndroidSchedulers.mainThread();
  }
}
