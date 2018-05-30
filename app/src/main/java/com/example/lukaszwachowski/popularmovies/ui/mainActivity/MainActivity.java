package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerMainActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.MainActivityModule;
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.example.lukaszwachowski.popularmovies.ui.detailActivity.DetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.MOVIE_OBJECT;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.POPULAR;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.TOP_RATED;
import static com.example.lukaszwachowski.popularmovies.db.FavMovieContract.MovieEntry.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.View, ListAdapter.OnItemClickListener {

    @Inject
    ListAdapter listAdapter;

    @Inject
    MainActivityMVP.Presenter presenter;

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
        presenter.loadData(TOP_RATED);
        listAdapter.setListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));
        recyclerView.setAdapter(listAdapter);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
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

        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    MoviesResult movie = new MoviesResult(cursor.getInt(1),
                            cursor.getDouble(2), cursor.getString(3),
                            cursor.getString(4), cursor.getString(5),
                            cursor.getString(6));
                    listAdapter.swapData(movie);
                } while (cursor.moveToNext());
            }

        } finally {
            if (cursor != null)
                cursor.close();
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
