package xyz.louiscad.popularmovies.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.DefaultExecutorSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import trikita.log.Log;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.PaletteLite;
import xyz.louiscad.popularmovies.ui.adapter.MovieItemAdapter;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * List Item for a Movie
 *
 * @see MovieItemAdapter
 */
@EViewGroup
public class MovieListItem extends RelativeLayout implements ViewWrapper.Binder<Movie>, View.OnClickListener {

    public interface ClickListener {
        void onClick(Movie movie);
    }

    @ViewById
    TextView titleTextView;

    @ViewById
    SimpleDraweeView posterImage;

    @ViewById
    View footerBackground;

    @ViewById
    ImageButton favoriteButton;

    private Movie mMovie;

    private boolean mIsMovieFavorite = false;

    public MovieListItem(Context context) {
        super(context);
    }

    public MovieListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MovieListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @AfterViews
    void init() {
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ((ClickListener) getContext()).onClick(mMovie);
    }

    @Click
    protected void favoriteButtonClicked(View v) {
        mIsMovieFavorite = !mIsMovieFavorite;
        favoriteButton.setImageResource(mIsMovieFavorite ?
                R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_outline_24dp);
        if (mIsMovieFavorite) mMovie.save();
        else mMovie.delete();
    }

    @Override
    public void bind(final Movie movie) {
        mMovie = movie;
        mIsMovieFavorite = movie.exists();
        favoriteButton.setImageResource(mIsMovieFavorite ?
                R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_outline_24dp);
        titleTextView.setText(movie.title);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        if (movie.posterPalette == null) {
            DataSource<CloseableReference<CloseableImage>> dataSource
                    = imagePipeline.fetchDecodedImage(ImageRequest.fromUri(movie.posterUrl), null);
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                protected void onNewResultImpl(Bitmap bitmap) {
                    if (bitmap != null) {
                        movie.posterPalette = new PaletteLite(getContext(), new Palette.Builder(bitmap).generate());
                        setFooterColor(movie.posterPalette);
                    } else Log.w("received a null bitmap");
                }

                @Override
                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    Log.w("onFailureImpl");
                }
            }, new DefaultExecutorSupplier(1).forBackgroundTasks());
        } else {
            setFooterColor(movie.posterPalette);
        }
        posterImage.setImageURI(movie.posterUrl);
    }


    @UiThread
    void setFooterColor(PaletteLite palette) {
        footerBackground.setBackgroundColor(palette.mutedColor);
    }
}
