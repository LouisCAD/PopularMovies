package xyz.louiscad.popularmovies.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.SystemClock;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.DefaultExecutorSupplier;
import com.facebook.imagepipeline.core.ExecutorSupplier;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.concurrent.Executor;

import trikita.log.Log;
import xyz.louiscad.popularmovies.BuildConfig;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.PaletteLite;
import xyz.louiscad.popularmovies.ui.adapter.MovieItemAdapter;
import xyz.louiscad.popularmovies.util.ImagePipelineUtils;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * List Item for a Movie
 *
 * @see MovieItemAdapter
 */
@EViewGroup
public class MovieListItem extends SelectableRelativeLayout implements ViewWrapper.Binder<Movie>, View.OnClickListener {

    public interface ClickListener {
        void onClick(Movie movie);
    }

    private Executor mPaletteExecutor;

    @ViewById
    TextView titleTextView;

    @ViewById
    SimpleDraweeView posterImage;

    @ViewById
    ImageButton favoriteButton;

    private Movie mMovie;

    /**
     * A binding is considered as pending while the Palette generation hasn't returned.
     * <p/>
     * Access this member only in the UI thread. Not doing to could lead to concurrency issues,
     * and inconsistent values.
     */
    private long mLatestBindingTime;

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
        if (BuildConfig.DEBUG & isInEditMode()) {
            mPaletteExecutor = new DefaultExecutorSupplier(1).forBackgroundTasks();
        } else {
            mPaletteExecutor = ((ExecutorSupplier) getContext()).forBackgroundTasks();
        }
    }

    @Override
    public void onClick(View v) {
        ((ClickListener) getContext()).onClick(mMovie);
    }

    @Click
    protected void favoriteButtonClicked() {
        favoriteButton.setImageResource(mMovie.toggleFavoredState());
    }

    @Override
    public void bind(final Movie movie) {
        long now = SystemClock.uptimeMillis();
        mLatestBindingTime = now;
        mMovie = movie;
        favoriteButton.setImageResource(mMovie.getFavoredStateIcon());
        titleTextView.setText(movie.title);
        setBackgroundResource(R.color.colorPrimary);
        boolean hasPosterUrl = movie.posterUrl != null;
        boolean isPaletteGenerated = movie.posterPalette != null;
        if (isPaletteGenerated) setFooterColor(movie.posterPalette);
        else if (hasPosterUrl) generatePaletteAndSetFooterColor(movie, now);
        posterImage.setImageURI(movie.posterUrl);
    }

    private void generatePaletteAndSetFooterColor(final Movie movie, final long bindingTime) {
        ImagePipelineUtils.subscribe(mPaletteExecutor, movie.posterUrl,
                new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        //TODO: add a request counter
                        //TODO: and skip palette generation if already rebound
                        if (bitmap != null) {
                            boolean isRebound = mLatestBindingTime > bindingTime;
                            if (!isRebound) {
                                Palette palette = new Palette.Builder(bitmap).generate();
                                movie.posterPalette = new PaletteLite(getContext(), palette);
                                setFooterColor(movie.posterPalette);
                            }
                        } else {
                            Log.w("received a null bitmap");
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                        dataSource.getFailureCause().printStackTrace();
                    }
                }
        );
    }

    @UiThread
    void setFooterColor(PaletteLite palette) {
        setBackgroundColor(palette.mutedColor);
    }
}
