package xyz.louiscad.popularmovies.ui.activity;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import trikita.log.Log;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.fragment.MovieDetailFragment;
import xyz.louiscad.popularmovies.ui.fragment.MoviesFragment;
import xyz.louiscad.popularmovies.ui.widget.MovieListItem;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity implements MovieListItem.ClickListener {

    @FragmentById(R.id.moviesFragment)
    MoviesFragment mMoviesFragment;

    @FragmentById(R.id.detailFragment)
    MovieDetailFragment mMovieDetailFragment;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @AfterViews
    void init() {
        mTwoPane = mMovieDetailFragment != null;
    }

    @OptionsItem
    void actionSettingsSelected() {
        Log.i("Settings clicked");
    }

    @Override
    public void onClick(Movie movie) {
        if (mTwoPane) {
            mMovieDetailFragment.setMovie(movie);
        } else {
            MovieDetailActivity_.intent(this).mMovie(movie).start();
        }
    }
}
