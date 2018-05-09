package com.example.lukaszwachowski.popularmovies.configuration;

import android.view.View;

public class ConfigureView {

    public static void showView(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideView(View view) {
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }
}
