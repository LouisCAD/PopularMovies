package xyz.louiscad.popularmovies.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.imagepipeline.core.DefaultExecutorSupplier;
import com.facebook.imagepipeline.core.ExecutorSupplier;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Executor;

import trikita.log.Log;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.fragment.MovieDetailFragment;
import xyz.louiscad.popularmovies.ui.fragment.MoviesFragment;
import xyz.louiscad.popularmovies.ui.widget.MovieListItem;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity implements
        MovieListItem.ClickListener, ExecutorSupplier {

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
        mTwoPane = twoPaneContainer != null;
        if (!mTwoPane) mMovieDetailFragment = null;
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

    private Executor mExecutor;

    @Override
    public Executor forLightweightBackgroundTasks() {
        return null;
    }

    @Override
    public Executor forBackgroundTasks() {
        if (mExecutor == null) {
            mExecutor = new DefaultExecutorSupplier(1)
                    .forBackgroundTasks();
        }
        return mExecutor;
    }

    @Override
    public Executor forDecode() {
        return null;
    }

    @Override
    public Executor forLocalStorageWrite() {
        return null;
    }

    @Override
    public Executor forLocalStorageRead() {
        return null;
    }
}
