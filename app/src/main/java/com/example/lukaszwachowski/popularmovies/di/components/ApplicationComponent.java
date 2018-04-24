package com.example.lukaszwachowski.popularmovies.di.components;

import com.example.lukaszwachowski.popularmovies.di.ApplicationScope;
import com.example.lukaszwachowski.popularmovies.di.modules.ContextModule;
import com.squareup.picasso.Picasso;

import dagger.Component;

@ApplicationScope
@Component(modules = ContextModule.class)
public interface ApplicationComponent {
    Picasso getPicasso();
}
