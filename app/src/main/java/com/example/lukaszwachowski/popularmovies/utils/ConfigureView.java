package com.example.lukaszwachowski.popularmovies.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lukaszwachowski.popularmovies.R;

public class ConfigureView {

  private static void showView(View view) {
    if (view.getVisibility() != View.VISIBLE) {
      view.setVisibility(View.VISIBLE);
    }
  }

  private static void hideView(View view) {
    if (view.getVisibility() != View.GONE) {
      view.setVisibility(View.GONE);
    }
  }

  public static void showIcon(TextView textView, boolean noShow) {
    if (noShow) {
      hideView(textView);
    } else {
      showView(textView);
    }
  }

  public static void setIconGray(ImageView view, Context context) {
    view.setColorFilter(ContextCompat.getColor(context, R.color.fav_grey));
  }

  public static void setIconRed(ImageView view, Context context) {
    view.setColorFilter(ContextCompat.getColor(context, R.color.fav_red));
  }
}
