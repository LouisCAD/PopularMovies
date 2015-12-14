package xyz.louiscad.popularmovies.ui.adapter;

import com.raizlabs.android.dbflow.list.FlowCursorList;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.widget.MovieListItem;
import xyz.louiscad.popularmovies.util.recyclerview.RecyclerViewAdapterBase;

/**
 * Created by Louis Cognault on 14/12/15.
 */
public class FavoriteMoviesAdapter extends RecyclerViewAdapterBase<Movie, MovieListItem> {

    private FlowCursorList<Movie> mData;

    public void replaceData(FlowCursorList<Movie> data) {
        mData = data;
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.list_item_movie;
    }

    @Override
    protected Movie getData(int position) {
        return mData.getItem(position);
    }

    @Override
    public int getItemCount() {
        return (mData == null ? 0 : mData.getCount());
    }
}
