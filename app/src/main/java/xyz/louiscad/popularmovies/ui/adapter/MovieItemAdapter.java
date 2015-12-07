package xyz.louiscad.popularmovies.ui.adapter;

import android.support.annotation.LayoutRes;

import java.util.List;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.widget.MovieListItem;
import xyz.louiscad.popularmovies.util.recyclerview.RecyclerViewAdapterBase;

/**
 * Created by Louis Cognault on 11/10/15.
 */
public class MovieItemAdapter extends RecyclerViewAdapterBase<Movie, MovieListItem> {

    private List<Movie> mMovieList;

    public void onDataUpdated(List<Movie> data, int page) {
        if (page == 1) {
            mMovieList = data;
            notifyDataSetChanged();
        } else {
            mMovieList.addAll(data);
            notifyItemRangeInserted(20 * page - 20, 20 * page - 1);
        }
    }

    @Override
    @LayoutRes
    protected int getLayout(int viewType) {
        return R.layout.list_item_movie;
    }

    @Override
    protected Movie getData(int position) {
        return mMovieList.get(position);
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null ? 0 : mMovieList.size());
    }
}
