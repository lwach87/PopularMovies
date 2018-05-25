package com.example.lukaszwachowski.popularmovies.di.components;

import com.example.lukaszwachowski.popularmovies.db.MoviesRepository;
import com.example.lukaszwachowski.popularmovies.di.ApplicationScope;
import com.example.lukaszwachowski.popularmovies.di.modules.ContextModule;
import com.example.lukaszwachowski.popularmovies.di.modules.DatabaseModule;
import com.squareup.picasso.Picasso;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, DatabaseModule.class})
public interface ApplicationComponent {
    Picasso getPicasso();
    MoviesRepository getRepository();
}
