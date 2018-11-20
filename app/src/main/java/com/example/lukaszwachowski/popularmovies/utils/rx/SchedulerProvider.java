package com.example.lukaszwachowski.popularmovies.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider {

  Scheduler io();

  Scheduler ui();
}
