package xyz.louiscad.popularmovies.ui.activity;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentByTag;
import org.androidannotations.annotations.OptionsItem;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.ReviewsResult;
import xyz.louiscad.popularmovies.ui.fragment.LoadingFragment;
import xyz.louiscad.popularmovies.ui.fragment.MovieDetailFragment;
import xyz.louiscad.popularmovies.ui.fragment.MovieDetailFragment_;

import static xyz.louiscad.popularmovies.model.Movie.MOVIE_EXTRA;

@EActivity(R.layout.activity_movie_detail)
public class MovieDetailActivity extends AppCompatActivity implements Callback, LoadingFragment.Caller {

    public static final String MOVIE_DETAIL_LOADER_FRAGMENT = "detailLoader";
    public static final String DETAIL_FRAGMENT = "movie_detail_fragment";

    @FragmentByTag(MOVIE_DETAIL_LOADER_FRAGMENT)
    protected LoadingFragment mMovieDetailLoader;

    @FragmentByTag(DETAIL_FRAGMENT)
    protected MovieDetailFragment mMovieDetailFragment;

    @Extra(MOVIE_EXTRA)
    Movie mMovie;

    @AfterViews
    void init() {
        if (mMovieDetailLoader == null) {
            mMovieDetailLoader = new LoadingFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(mMovieDetailLoader, MOVIE_DETAIL_LOADER_FRAGMENT)
                    .commit();
        }
        if (mMovieDetailFragment == null) {
            mMovieDetailFragment = MovieDetailFragment_.builder().mMovie(mMovie).build();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mMovieDetailFragment, DETAIL_FRAGMENT)
                    .commit();
        }
    }

    @OptionsItem(android.R.id.home)
    boolean upClicked() {
        onBackPressed();
        return true;
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
        mMovieDetailFragment.onFailure(t);
    }

    @Override
    public void enqueue(Call request) {
        mMovieDetailLoader.enqueue(request);
    }
}
