package xyz.louiscad.popularmovies.widget;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.util.ImageUtil;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * Created by Louis Cognault on 11/10/15.
 */
@EViewGroup(R.layout.list_item_movie)
public class MovieListItem extends FrameLayout implements ViewWrapper.Binder<Movie> {

    @ViewById
    TextView titleTextView;

    @ViewById
    SimpleDraweeView posterImage;

    public MovieListItem(Context context) {
        super(context);
    }

    @Override
    public void bind(Movie movie) {
        titleTextView.setText(movie.title);
        posterImage.setImageURI(ImageUtil.getPosterUri(movie.poster_path));
    }
}
