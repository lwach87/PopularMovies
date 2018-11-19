package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import static com.example.lukaszwachowski.popularmovies.utils.Constants.MOVIE_OBJECT;
import static com.example.lukaszwachowski.popularmovies.utils.Constants.POPULAR;
import static com.example.lukaszwachowski.popularmovies.utils.Constants.TOP_RATED;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.data.model.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.ui.base.BaseActivity;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;
import javax.inject.Inject;

public class MainActivity extends BaseActivity<MainViewModel> implements
    ListAdapter.OnItemClickListener {

  @Inject
  ListAdapter listAdapter;

  @Inject
  ViewModelProvider.Factory viewModelFactory;

  @BindView(R.id.recycler_view)
  RecyclerView recyclerView;

  private MainViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel.class);

    viewModel.fetchMovies(TOP_RATED);
    subscribeToLiveData();

    listAdapter.setListener(this);

    recyclerView.setLayoutManager(new GridLayoutManager(this, columnCount(210)));
    recyclerView.setAdapter(listAdapter);
  }

  public int columnCount(int scalingFactor) {
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
    float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
    int columnCount = (int) (dpWidth / scalingFactor);
    return (columnCount >= 2 ? columnCount : 2);
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
        viewModel.fetchMovies(TOP_RATED);
        return true;

      case R.id.sort_by_popularity:
        listAdapter.clearData();
        viewModel.fetchMovies(POPULAR);
        return true;

      case R.id.sort_by_favourites:
        listAdapter.clearData();
        viewModel.setFavourites();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void subscribeToLiveData() {
    viewModel.getMoviesLiveData()
        .observe(this, moviesResults -> listAdapter.swapData(moviesResults));
  }

  @Override
  public void onItemClick(MoviesResult result, Bundle bundle) {
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(MOVIE_OBJECT, result);
    startActivity(intent, bundle);
  }
}
