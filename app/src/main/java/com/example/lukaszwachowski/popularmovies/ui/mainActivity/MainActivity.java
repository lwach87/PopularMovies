package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.db.MoviesRepository;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerMainActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.MainActivityModule;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.MOVIE_OBJECT;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.View, ListAdapter.OnItemClickListener {

    @Inject
    ListAdapter listAdapter;

    @Inject
    MainActivityMVP.Presenter presenter;

    @Inject
    MoviesRepository repository;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.activity_main)
    ViewGroup rootView;

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
        presenter.loadData("top_rated");
        listAdapter.setListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(listAdapter);
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
                presenter.loadData("top_rated");
                return true;

            case R.id.sort_by_popularity:
                listAdapter.clearData();
                presenter.loadData("popular");
                return true;

            case R.id.sort_by_favourites:
                listAdapter.clearData();
                repository.getFavouriteMovies()
                        .observe(this, moviesResults -> listAdapter.swapMovies(moviesResults));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void updateData(MoviesResult result) {
        listAdapter.swapData(result);
    }

    @Override
    public void onItemClick(MoviesResult result) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(MOVIE_OBJECT, result);
        startActivity(intent);
    }

    @Override
    public void showSnackBar(String text) {
        Snackbar.make(rootView, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
