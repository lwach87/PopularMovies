package com.example.lukaszwachowski.popularmovies.ui.mainActivity;

import com.example.lukaszwachowski.popularmovies.network.model.Result;

public class MainActivityMVP {

    interface View {

        void updateData(Result result);

        void showSnackBar();
    }

    public interface Presenter {

        void loadData(String sortingType);

        void rxUnSubscribe();

        void setView(MainActivityMVP.View view);
    }
}
