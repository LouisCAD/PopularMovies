package xyz.louiscad.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.lang.annotation.Retention;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import trikita.log.Log;
import xyz.louiscad.popularmovies.MoviesApp;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.MovieDiscoverResult;
import xyz.louiscad.popularmovies.ui.adapter.MovieItemAdapter;
import xyz.louiscad.popularmovies.util.recyclerview.EndlessRecyclerOnScrollListener;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Louis Cognault on 16/10/15.
 */
@EFragment(R.layout.swipe_refresh_layout_recycler_view)
public class MovieListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, Callback<MovieDiscoverResult> {

    public static final String POPULARITY = "popularity";
    public static final String NEWEST = "release_date";
    public static final String VOTE_AVERAGE = "vote_average";

    public static final String DESC = "desc";

    @FragmentArg
    @InstanceState
    String mSort = POPULARITY;

    @App
    MoviesApp mApp;

    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById
    RecyclerView recyclerView;

    MovieItemAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MovieItemAdapter();
        onRefresh();
    }

    @AfterViews
    void init() {
        int spanCount = getResources().getInteger(R.integer.span_count);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        //TODO: Use new API for OnScrollListener and make infinite scroll code a little cleaner here
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                String sort = MovieListFragment.this.mSort + "." + DESC;
                mApp.getAPI().discoverMovies(currentPage, sort).enqueue(MovieListFragment.this);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void onRefresh() {
        String sort = this.mSort + "." + DESC;
        mApp.getAPI().discoverMovies(1, sort).enqueue(this);
    }

    @Override
    public void onResponse(Response<MovieDiscoverResult> response, Retrofit retrofit) {
        if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
        MovieDiscoverResult result = response.body();
        if (!response.isSuccess() || result == null) onFailure(new NullPointerException());
        else mAdapter.onDataUpdated(response.body().results, response.body().page);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(t);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            Snackbar.make(swipeRefreshLayout, "Oops! Unable to get movies…", LENGTH_LONG).show();
        }
    }

    @StringDef({POPULARITY, NEWEST, VOTE_AVERAGE})
    @Retention(SOURCE)
    public @interface Sort {}
}
