package xyz.louiscad.popularmovies.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.adapter.MovieDetailAdapter;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * @see MovieDetailAdapter
 */
@EViewGroup(R.layout.content_list_item_overview)
public class OverViewListItem extends LinearLayout implements ViewWrapper.Binder<Movie> {

    @ViewById TextView overViewTitleTextView, overViewTextView;

    public OverViewListItem(Context context) {
        super(context);
    }

    public OverViewListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverViewListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public OverViewListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void bind(Movie movie) {
        overViewTextView.setText(movie.overview);
        if (movie.posterPalette != null) {
            overViewTitleTextView.setTextColor(movie.posterPalette.vibrantColor);
        }
    }
}
