package com.example.lukaszwachowski.popularmovies.ui.detailActivity;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lukaszwachowski.popularmovies.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.MOVIE_POSTER;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.ORIGINAL_TITLE;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.PLOT_SYNOPSIS;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.RELEASE_DATE;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.USER_RATING;

public class DetailAdapter extends CoordinatorLayout {

    private Picasso picasso;

    @BindView(R.id.title_detail)
    TextView title_detail;

    @BindView(R.id.detail_poster)
    ImageView poster_detail;

    @BindView(R.id.date_detail)
    TextView date_detail;

    @BindView(R.id.rating_detail)
    TextView rating_detail;

    @BindView(R.id.plot_detail)
    TextView plot_detail;

    public DetailAdapter(Context context, Picasso picasso) {
        super(context);
        this.picasso = picasso;
        inflateLayout(context);
    }

    public DetailAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void inflateLayout(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_detail, this);
        ButterKnife.bind(this, view);

        String title = ((Activity) context).getIntent().getStringExtra(ORIGINAL_TITLE);
        String poster = ((Activity) context).getIntent().getStringExtra(MOVIE_POSTER);
        String plot = ((Activity) context).getIntent().getStringExtra(PLOT_SYNOPSIS);
        double rating = ((Activity) context).getIntent().getDoubleExtra(USER_RATING, 1.0);
        String date = ((Activity) context).getIntent().getStringExtra(RELEASE_DATE);

        title_detail.setText(title);
        date_detail.setText(date);
        rating_detail.setText(String.valueOf(rating));
        plot_detail.setText(plot);

        picasso.load(poster)
                .into(poster_detail);
    }
}