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
import com.example.lukaszwachowski.popularmovies.network.movies.MoviesResult;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.IMAGE_URL;
import static com.example.lukaszwachowski.popularmovies.configuration.NetworkUtils.MOVIE_OBJECT;

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

        MoviesResult result = ((Activity) context).getIntent().getParcelableExtra(MOVIE_OBJECT);

        title_detail.setText(result.getOriginalTitle());
        date_detail.setText(result.getReleaseDate());
        rating_detail.setText(String.valueOf(result.getVoteAverage()));
        plot_detail.setText(result.getOverview());

        picasso.load(IMAGE_URL + result.getPosterPath())
                .into(poster_detail);
    }
}
