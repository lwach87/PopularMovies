package com.example.lukaszwachowski.popularmovies.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.R;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerMainActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.MainActivityModule;
import com.example.lukaszwachowski.popularmovies.network.model.Result;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.View {

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

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    public void updateData(Result result) {
        listAdapter.swapData(result);
    }

    @Override
    public void showSnackBar() {
        Snackbar.make(rootView, "No internet connection", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxUnSubscribe();
    }
}
