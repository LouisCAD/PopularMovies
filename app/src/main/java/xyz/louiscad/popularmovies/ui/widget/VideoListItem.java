package xyz.louiscad.popularmovies.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Video;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * Created by Louis Cognault on 26/11/15.
 */
@EViewGroup(R.layout.list_item_video)
public class VideoListItem extends FrameLayout implements ViewWrapper.Binder<Video> {

    @ViewById
    SimpleDraweeView thumbnail;

    public VideoListItem(Context context) {
        super(context);
    }

    public VideoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void bind(Video video) {
        thumbnail.setImageURI(video.thumbnailUrl);
    }
}
