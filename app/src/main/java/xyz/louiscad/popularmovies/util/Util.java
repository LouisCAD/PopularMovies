package xyz.louiscad.popularmovies.util;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Louis Cognault on 30/10/15.
 */
public class Util {
    public static void setToolbarAsActionBar(Activity activity, Toolbar toolbar) {
        setToolbarAsActionBar((AppCompatActivity) activity, toolbar);
    }

    public static void setToolbarAsActionBar(AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
