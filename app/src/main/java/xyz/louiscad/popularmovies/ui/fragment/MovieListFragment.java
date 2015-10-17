package xyz.louiscad.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import trikita.log.Log;
import xyz.louiscad.popularmovies.MoviesApp;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.MovieDiscoverResult;
import xyz.louiscad.popularmovies.ui.adapter.MovieItemAdapter;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by Louis Cognault on 16/10/15.
 */
@EFragment(R.layout.swipe_refresh_layout_recycler_view)
public class MovieListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, Callback<MovieDiscoverResult> {

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
        Log.i("onCreate");
        mAdapter = new MovieItemAdapter();
    }

    @AfterViews
    void init() {
        Log.i("MovieListFragment list init");
        int spanCount = getResources().getInteger(R.integer.span_count);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        mApp.getAPI().discoverMovies(1).enqueue(this);
    }

    @Override
    public void onResponse(Response<MovieDiscoverResult> response, Retrofit retrofit) {
        swipeRefreshLayout.setRefreshing(false);
        MovieDiscoverResult result = response.body();
        if (result == null) onFailure(new NullPointerException());
        else mAdapter.onDataUpdated(response.body().results);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(t);
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(swipeRefreshLayout, "Oops! Unable to get movies…", LENGTH_LONG).show();
    }
}
