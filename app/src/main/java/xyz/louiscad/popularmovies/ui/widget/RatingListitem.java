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
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * Created by Louis Cognault on 07/01/16.
 */
@EViewGroup(R.layout.content_list_item_rating)
public class RatingListItem extends LinearLayout implements ViewWrapper.Binder<Movie> {

    @ViewById TextView ratingTitleTextView, ratingTextView, voteCountTextView;

    public RatingListItem(Context context) {
        super(context);
    }

    public RatingListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RatingListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RatingListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void bind(Movie movie) {
        Context context = getContext();
        ratingTextView.setText(context.getString(R.string.vote_avg, movie.voteAverage));
        voteCountTextView.setText(context.getString(R.string.vote_count, movie.voteCount));
        if (movie.posterPalette != null) {
            ratingTitleTextView.setTextColor(movie.posterPalette.vibrantColor);
        }
    }
}
