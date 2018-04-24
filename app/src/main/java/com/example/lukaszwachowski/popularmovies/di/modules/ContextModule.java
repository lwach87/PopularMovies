package com.example.lukaszwachowski.popularmovies.di.modules;

import android.content.Context;

import com.example.lukaszwachowski.popularmovies.di.ApplicationScope;
import com.squareup.picasso.Picasso;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    public Context context() {
        return context;
    }

    @Provides
    @ApplicationScope
    public Picasso picasso(Context context) {
        return new Picasso.Builder(context).build();
    }
}
