package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import static com.example.lukaszwachowski.popularmovies.configuration.Constants.MOVIE_OBJECT;
import static com.example.lukaszwachowski.popularmovies.configuration.Constants.POPULAR;
import static com.example.lukaszwachowski.popularmovies.configuration.Constants.TOP_RATED;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.db.Repository;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerMainActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.MainActivityModule;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.View,
    ListAdapter.OnItemClickListener {

  @Inject
  ListAdapter listAdapter;

  @Inject
  MainActivityMVP.Presenter presenter;

  @Inject
  Repository repository;

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  @BindView(R.id.activity_main)
  ViewGroup rootView;

  private CompositeDisposable disposable = new CompositeDisposable();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    DaggerMainActivityComponent.builder()
        .mainActivityModule(new MainActivityModule(this))
        .applicationComponent(MoviesApp.get(this).component())
        .build().inject(this);

    presenter.attachView(this);
    listAdapter.setListener(this);
    presenter.loadData(TOP_RATED);

    recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
    recyclerView.setAdapter(listAdapter);
  }

  private int numberOfColumns() {
    DisplayMetrics displayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    int widthDivider = 400;
    int width = displayMetrics.widthPixels;
    int nColumns = width / widthDivider;
    if (nColumns < 2) {
      return 2; //to keep the grid aspect
    }
    return nColumns;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case R.id.sort_by_rating:
        listAdapter.clearData();
        presenter.loadData(TOP_RATED);
        return true;

      case R.id.sort_by_popularity:
        listAdapter.clearData();
        presenter.loadData(POPULAR);
        return true;

      case R.id.sort_by_favourites:
        listAdapter.clearData();
        setFavourites();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void setFavourites() {
    disposable.add(repository.getAllMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(moviesResults -> {
          for (MoviesResult movie : moviesResults) {
            listAdapter.swapData(movie);
          }
        }, error -> Snackbar.make(rootView, error.getLocalizedMessage(), Snackbar.LENGTH_SHORT)
            .show()));
  }

  @Override
  public void updateData(MoviesResult result) {
    listAdapter.swapData(result);
  }

  @Override
  public void onItemClick(MoviesResult result, Bundle bundle) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(MOVIE_OBJECT, result);
    startActivity(intent, bundle);
  }

  @Override
  public void showSnackBar(String text) {
    Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    presenter.detachView();
    disposable.clear();
  }
}
