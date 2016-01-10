package xyz.louiscad.popularmovies.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.imagepipeline.core.DefaultExecutorSupplier;
import com.facebook.imagepipeline.core.ExecutorSupplier;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.FragmentByTag;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Executor;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import trikita.log.Log;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.ReviewsResult;
import xyz.louiscad.popularmovies.ui.fragment.LoadingFragment;
import xyz.louiscad.popularmovies.ui.fragment.MovieDetailFragment;
import xyz.louiscad.popularmovies.ui.fragment.MoviesFragment;
import xyz.louiscad.popularmovies.ui.widget.MovieListItem;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity implements
        MovieListItem.ClickListener, ExecutorSupplier, Callback, LoadingFragment.Caller {

    public static final String MOVIE_DETAIL_LOADER_FRAGMENT = "detailLoader";

    @FragmentById(R.id.moviesFragment)
    protected MoviesFragment mMoviesFragment;

    @FragmentById(R.id.detailFragment)
    protected MovieDetailFragment mMovieDetailFragment;

    @FragmentByTag(MOVIE_DETAIL_LOADER_FRAGMENT)
    protected LoadingFragment mMovieDetailLoader;

    private Executor mExecutor;

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
        if (mMovieDetailLoader == null) {
            mMovieDetailLoader = new LoadingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(mMovieDetailLoader, MOVIE_DETAIL_LOADER_FRAGMENT)
                    .commit();
        }
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

    @Override
    public void onResponse(Response response, Retrofit retrofit) {
        Object body = response.body();
        if (body instanceof Movie || body instanceof ReviewsResult) {
            mMovieDetailFragment.onResponse(response, retrofit);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        mMovieDetailLoader.onFailure(t);
    }

    @Override
    public void enqueue(Call request) {
        mMovieDetailLoader.enqueue(request);
    }
}
