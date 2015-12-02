package xyz.louiscad.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.Video;
import xyz.louiscad.popularmovies.ui.widget.VideoListItem;

/**
 * The {@link RecyclerView.Adapter} for {@link Movie} items
 */
public class VideoItemAdapter extends ListAdapter<Video, VideoListItem> {
    @Override
    protected VideoListItem onCreateItemView(ViewGroup parent, int viewType) {
        return VideoListItem.inflate(parent);
    }
}
