package xyz.louiscad.popularmovies.ui.fragment;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.util.Util;

/**
 * This Fragment is meant to display the details of a Movie
 */
@EFragment(R.layout.fragment_movie_detail)
public class MovieDetailFragment extends Fragment {

    @FragmentArg
    Movie mMovie;

    @ViewById CoordinatorLayout mCL;
    @ViewById CollapsingToolbarLayout mToolbarLayout;
    @ViewById Toolbar mToolbar;

    @ViewById SimpleDraweeView mBackdropImage;

    @ViewById
    TextView mOverviewTitleTextView, mOverviewTextView;

    @AfterViews
    void init() {
        Util.setToolbarAsActionBar(getActivity(), mToolbar);
        mBackdropImage.setImageURI(mMovie.backdropUrl);
        mToolbarLayout.setContentScrimColor(mMovie.posterPalette.mutedColor);
        mToolbarLayout.setTitle(mMovie.title);
        mOverviewTextView.setText(mMovie.overview);
        mOverviewTitleTextView.setTextColor(mMovie.posterPalette.lightVibrantColor);
    }
}
