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
import xyz.louiscad.popularmovies.model.Review;
import xyz.louiscad.popularmovies.ui.adapter.MovieDetailAdapter;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * @see MovieDetailAdapter
 */
@EViewGroup(R.layout.content_list_item_review)
public class ReviewListItem extends LinearLayout implements ViewWrapper.Binder<Review> {

    @ViewById TextView reviewAuthorTextView, reviewContentTextView;

    public ReviewListItem(Context context) {
        super(context);
    }

    public ReviewListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReviewListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ReviewListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void bind(Review review) {
        reviewAuthorTextView.setText(review.author);
        reviewContentTextView.setText(review.content);
    }
}
