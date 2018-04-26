package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.lukaszwachowski.popularmovies.MoviesApp;
import com.example.lukaszwachowski.popularmovies.di.components.DaggerDetailActivityComponent;
import com.example.lukaszwachowski.popularmovies.di.modules.DetailActivityModule;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {

    @Inject
    DetailAdapter detailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerDetailActivityComponent.builder()
                .detailActivityModule(new DetailActivityModule(this))
                .applicationComponent(MoviesApp.get(this).component())
                .build().inject(this);

        setContentView(detailAdapter);
    }
}
