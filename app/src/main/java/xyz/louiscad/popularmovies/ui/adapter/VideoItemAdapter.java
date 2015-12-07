package xyz.louiscad.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.Video;
import xyz.louiscad.popularmovies.ui.widget.VideoListItem;

/**
 * The {@link RecyclerView.Adapter} for {@link Movie} items
 */
public class VideoItemAdapter extends ListAdapter<Video, VideoListItem> {

    @Override
    protected int getLayout(int viewType) {
        return R.layout.list_item_video;
    }
}
