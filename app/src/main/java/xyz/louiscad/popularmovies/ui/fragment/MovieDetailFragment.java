package xyz.louiscad.popularmovies.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.text.DateFormat;
import java.util.List;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.util.Util;

import static android.os.Build.VERSION.SDK_INT;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * This Fragment is meant to display the details of a Movie
 */
@EFragment(R.layout.fragment_movie_detail)
public class MovieDetailFragment extends Fragment {

    @FragmentArg
    Movie mMovie;

    @ViewById CoordinatorLayout cl;
    @ViewById CollapsingToolbarLayout toolbarLayout;
    @ViewById Toolbar toolbar;
    @ViewById FloatingActionButton fab;

    @ViewById SimpleDraweeView backdropImage, posterImage;

    @ViewById
    TextView overviewTextView, releaseDateTextView, ratingTextView;

    @ViewsById({R.id.overviewTitleTextView, R.id.releaseDateTitleTextView, R.id.ratingTitleTextView})
    List<TextView> mTextViews;

    @AfterViews
    void init() {
        Util.setToolbarAsActionBar(getActivity(), toolbar);
        if (SDK_INT > 21) getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        posterImage.setImageURI(mMovie.posterUrl);
        backdropImage.setImageURI(mMovie.backdropUrl);
        toolbar.setTitle(mMovie.title);
        overviewTextView.setText(mMovie.overview);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getActivity());
        releaseDateTextView.setText(dateFormat.format(mMovie.release_date));
        ratingTextView.setText(mMovie.vote_average + "/10"); //TODO: Use String res with placeholder
        if (mMovie.posterPalette != null) {
            toolbarLayout.setContentScrimColor(mMovie.posterPalette.mutedColor);
            fab.setBackgroundTintList(ColorStateList.valueOf(mMovie.posterPalette.vibrantColor));
            for (TextView textView : mTextViews) {
                textView.setTextColor(mMovie.posterPalette.vibrantColor);
            }
        }
    }

    @Click
    void fabClicked() {
        Snackbar.make(cl, "Not implemented yet… note it on paper!", LENGTH_LONG).show();
    }
}