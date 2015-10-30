package xyz.louiscad.popularmovies.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.FrameLayout;
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

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import trikita.log.Log;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.PaletteLite;
import xyz.louiscad.popularmovies.ui.activity.MovieDetailActivity_;
import xyz.louiscad.popularmovies.ui.adapter.MovieItemAdapter;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * List Item for a Movie
 * @see MovieItemAdapter
 */
@EViewGroup(R.layout.list_item_movie)
public class MovieListItem extends FrameLayout implements ViewWrapper.Binder<Movie>, View.OnClickListener {

    @ViewById
    TextView titleTextView;

    @ViewById
    SimpleDraweeView posterImage;

    @ViewById
    View footerBackground;

    private Movie mMovie;

    public MovieListItem(Context context) {
        super(context);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MovieDetailActivity_.intent(getContext()).mMovie(mMovie).start();
    }

    @Override
    public void bind(final Movie movie) {
        mMovie = movie;
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
                        Log.i("new bitmap received");
                    } else Log.w("received a null bitmap");
                }

                @Override
                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    Log.i("onFailureImpl");
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
