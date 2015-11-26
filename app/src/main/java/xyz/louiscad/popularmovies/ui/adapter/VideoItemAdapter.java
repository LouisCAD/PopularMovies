package xyz.louiscad.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.Video;
import xyz.louiscad.popularmovies.ui.widget.VideoListItem;
import xyz.louiscad.popularmovies.ui.widget.VideoListItem_;
import xyz.louiscad.popularmovies.util.recyclerview.RecyclerViewAdapterBase;

/**
 * The {@link RecyclerView.Adapter} for {@link Movie} items
 */
public class VideoItemAdapter extends RecyclerViewAdapterBase<Video, VideoListItem> {
    @Override
    protected VideoListItem onCreateItemView(ViewGroup parent, int viewType) {
        return VideoListItem_.build(parent.getContext());
    }

    @Override
    protected Video getData(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
