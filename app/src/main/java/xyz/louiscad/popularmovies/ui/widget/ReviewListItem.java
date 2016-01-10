package xyz.louiscad.popularmovies.ui.widget;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
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
public class ReviewListItem extends CardView implements ViewWrapper.Binder<Review> {

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

    @Override
    public void bind(Review review) {
        reviewAuthorTextView.setText(review.author);
        reviewContentTextView.setText(review.content);
    }
}
