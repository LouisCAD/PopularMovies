package xyz.louiscad.popularmovies.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EView;

import xyz.louiscad.popularmovies.model.Video;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

@EView
public class VideoListItem extends SimpleDraweeView implements ViewWrapper.Binder<Video> {

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
        setImageURI(video.thumbnailUrl);
    }
}
