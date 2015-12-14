package xyz.louiscad.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.adapter.FavoriteMoviesAdapter;

/**
 * Created by Louis Cognault on 14/12/15.
 */
@EFragment(R.layout.recycler_view)
public class FavoriteMoviesFragment extends Fragment {

    @ViewById
    RecyclerView recyclerView;

    private FavoriteMoviesAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FavoriteMoviesAdapter();
        FlowCursorList<Movie> favoriteMovies = new FlowCursorList<>(false, Movie.class);
        mAdapter.replaceData(favoriteMovies);
    }

    @AfterViews
    void init() {
        int spanCount = getResources().getInteger(R.integer.span_count);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        recyclerView.setAdapter(mAdapter);
    }
}
