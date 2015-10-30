package xyz.louiscad.popularmovies.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.fragment.MovieDetailFragment_;

import static xyz.louiscad.popularmovies.model.Movie.MOVIE_EXTRA;

/**
 * Created by Louis Cognault on 30/10/15.
 */
@EActivity(R.layout.activity_movie_detail)
public class MovieDetailActivity extends AppCompatActivity {

    public static final String DETAIL_FRAGMENT = "movie_detail_fragment";

    @Extra(MOVIE_EXTRA)
    Movie mMovie;

    @AfterViews
    void showFragment() {
        if (getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT) == null) {
            Fragment fragment = MovieDetailFragment_.builder().mMovie(mMovie).build();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, DETAIL_FRAGMENT)
                    .commit();
        }
    }

    @OptionsItem(android.R.id.home)
    boolean upClicked() {
        onBackPressed();
        return true;
    }
}
