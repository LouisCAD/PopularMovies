package xyz.louiscad.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import trikita.log.Log;
import xyz.louiscad.popularmovies.MoviesApp;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.MovieDiscoverResult;
import xyz.louiscad.popularmovies.ui.activity.MainActivity;
import xyz.louiscad.popularmovies.ui.adapter.MovieItemAdapter;
import xyz.louiscad.popularmovies.ui.fragment.MovieListFragment_.MovieListPrefs_;
import xyz.louiscad.popularmovies.util.recyclerview.EndlessRecyclerOnScrollListener;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by Louis Cognault on 16/10/15.
 */
@EFragment(R.layout.swipe_refresh_layout_recycler_view)
@OptionsMenu(R.menu.menu_movie_list)
public class MovieListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, Callback<MovieDiscoverResult> {

    public static final String POPULARITY = "popularity";
    public static final String NEWEST = "release_date";
    public static final String VOTE_AVERAGE = "vote_average";

    public static final String DESC = "desc";

    @Pref
    MovieListPrefs_ prefs;

    @App
    MoviesApp mApp;

    @ViewById
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById
    RecyclerView recyclerView;

    MovieItemAdapter mAdapter;

    @OptionsMenuItem
    MenuItem mostPopular, highestRated, newest;

    @OptionsItem
    void mostPopularSelected() {
        setToolbarSubtitle(POPULARITY);
        mostPopular.setChecked(true);
        prefs.sort().put(POPULARITY);
        onRefresh();
    }

    @OptionsItem
    void highestRatedSelected() {
        setToolbarSubtitle(VOTE_AVERAGE);
        highestRated.setChecked(true);
        prefs.sort().put(VOTE_AVERAGE);
        onRefresh();
    }

    @OptionsItem
    void newestSelected() {
        setToolbarSubtitle(NEWEST);
        newest.setChecked(true);
        prefs.sort().put(NEWEST);
        onRefresh();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new MovieItemAdapter();
        onRefresh();
    }

    @AfterViews
    void init() {
        setToolbarSubtitle(null);
        int spanCount = getResources().getInteger(R.integer.span_count);
        LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        //TODO: Use new API for OnScrollListener and make infinite scroll code a little cleaner here
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                String sort = prefs.sort().get() + "." + DESC;
                mApp.getAPI().discoverMovies(currentPage, sort).enqueue(MovieListFragment.this);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        switch (prefs.sort().get()) {
            case POPULARITY: mostPopular.setChecked(true); break;
            case NEWEST: newest.setChecked(true); break;
            case VOTE_AVERAGE: highestRated.setChecked(true); break;
        }
    }

    private void setToolbarSubtitle(@Nullable String sort) {
        if (sort == null) sort = prefs.sort().get();
        @StringRes int subtitleResId;
        switch (sort) {
            case POPULARITY: subtitleResId = R.string.mostPopular; break;
            case NEWEST: subtitleResId = R.string.newest; break;
            case VOTE_AVERAGE: subtitleResId = R.string.highestRated; break;
            default: throw new UnsupportedOperationException();
        }
        ((MainActivity) getActivity()).setSubtitle(subtitleResId);
    }

    @Override
    public void onRefresh() {
        String sort = prefs.sort().get() + "." + DESC;
        mApp.getAPI().discoverMovies(1, sort).enqueue(this);
    }

    @Override
    public void onResponse(Response<MovieDiscoverResult> response, Retrofit retrofit) {
        swipeRefreshLayout.setRefreshing(false);
        MovieDiscoverResult result = response.body();
        if (!response.isSuccess() || result == null) onFailure(new NullPointerException());
        else mAdapter.onDataUpdated(response.body().results, response.body().page);
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(t);
        swipeRefreshLayout.setRefreshing(false);
        Snackbar.make(swipeRefreshLayout, "Oops! Unable to get movies…", LENGTH_LONG).show();
    }

    @SharedPref
    public interface MovieListPrefs {

        @DefaultString(POPULARITY)
        String sort();
    }
}
