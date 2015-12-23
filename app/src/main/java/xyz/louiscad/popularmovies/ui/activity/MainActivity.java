package xyz.louiscad.popularmovies.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

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

    @ViewById
    View twoPaneContainer;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @AfterViews
    void init() {
        //mTwoPane = mMovieDetailFragment != null; //
        mTwoPane = twoPaneContainer != null;
        Log.d("mTwoPane %b", mTwoPane);
        Log.d("moviesFragment id: " + mMoviesFragment.getId());
        Log.d("detailsFragment id: " + mMovieDetailFragment.getId());
    }

    @OptionsItem
    void actionSettingsSelected() {
        Log.i("Settings clicked");
    }

        //Log.i("Fragment attached with id: " + fragment.getId());
    @Override
    public void onAttachFragment(Fragment fragment) {
        this.mMoviesFragment = findSupportFragmentById(R.id.moviesFragment, fragment);
        this.mMovieDetailFragment = findSupportFragmentById(R.id.detailFragment, fragment);
    }

    @SuppressWarnings("unchecked cast")
    private <F extends android.support.v4.app.Fragment> F findSupportFragmentById(int id, Fragment fragment) {
        boolean isExpected = id == fragment.getId();
        return (isExpected ? (F) fragment : null);
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
