package com.example.lukaszwachowski.popularmovies.ui.base;

import static com.example.lukaszwachowski.popularmovies.utils.ConfigureView.hideView;
import static com.example.lukaszwachowski.popularmovies.utils.ConfigureView.showView;

import android.arch.lifecycle.ViewModel;
import android.widget.TextView;
import com.example.lukaszwachowski.popularmovies.data.DataManager;
import com.example.lukaszwachowski.popularmovies.utils.rx.SchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {

  private DataManager dataManager;
  private SchedulerProvider schedulerProvider;
  private CompositeDisposable disposable;

  public BaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
    this.dataManager = dataManager;
    this.schedulerProvider = schedulerProvider;
    disposable = new CompositeDisposable();
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    disposable.clear();
  }

  public CompositeDisposable getDisposable() {
    return disposable;
  }

  public DataManager getDataManager() {
    return dataManager;
  }

  public SchedulerProvider getSchedulerProvider() {
    return schedulerProvider;
  }

  public void showIcon(TextView textView, boolean noShow) {
    if (noShow) {
      hideView(textView);
    } else {
      showView(textView);
    }
  }
}
