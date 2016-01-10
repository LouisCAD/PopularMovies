package xyz.louiscad.popularmovies.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.DateFormat;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * Created by Louis Cognault on 07/01/16.
 */
@EViewGroup(R.layout.content_list_item_release_date)
public class ReleaseDateListItem extends LinearLayout implements ViewWrapper.Binder<Movie> {

    @ViewById TextView releaseDateTitleTextView, releaseDateTextView;

    public ReleaseDateListItem(Context context) {
        super(context);
    }

    public ReleaseDateListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReleaseDateListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReleaseDateListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void bind(Movie movie) {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        releaseDateTextView.setText(dateFormat.format(movie.releaseDate));
        if (movie.posterPalette != null) {
            releaseDateTitleTextView.setTextColor(movie.posterPalette.vibrantColor);
        }
    }
}
