package xyz.louiscad.popularmovies.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.text.DateFormat;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import trikita.log.Log;
import xyz.louiscad.popularmovies.MoviesApp;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.ui.adapter.VideoItemAdapter;
import xyz.louiscad.popularmovies.util.Util;

import static android.os.Build.VERSION.SDK_INT;
import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

/**
 * This Fragment is meant to display the details of a Movie
 */
@EFragment(R.layout.fragment_movie_detail)
public class MovieDetailFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Callback<Movie> {

    @FragmentArg
    @InstanceState
    Movie mMovie;

    @App
    MoviesApp mApp;

    VideoItemAdapter mVideoAdapter;

    @ViewById CoordinatorLayout cl;
    @ViewById Toolbar toolbar;
    @ViewById CollapsingToolbarLayout toolbarLayout;
    @ViewById SwipeRefreshLayout swipeRefreshLayout;
    @ViewById NestedScrollView scrollView;
    @ViewById FloatingActionButton fab;

    @ViewById SimpleDraweeView backdropImage, posterImage;

    @ViewById RecyclerView videosRecyclerView;

    @ViewById
    TextView overviewTextView, releaseDateTextView, ratingTextView;

    @ViewsById({
            R.id.overviewTitleTextView,
            R.id.releaseDateTitleTextView,
            R.id.ratingTitleTextView,
            R.id.videosTitleTextView,
            R.id.commentsTitleTextView})
    List<TextView> mTextViews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mVideoAdapter = new VideoItemAdapter();
        if (mMovie != null) onRefresh();
    }

    @AfterViews
    void init() {
        Util.setToolbarAsActionBar(getActivity(), toolbar);
        if (SDK_INT > 21) getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        videosRecyclerView.setAdapter(mVideoAdapter);
        videosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), HORIZONTAL, false));
        if (mMovie != null) bindViewsToData();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void bindViewsToData() {
        posterImage.setImageURI(mMovie.posterUrl);
        backdropImage.setImageURI(mMovie.backdropUrl);
        toolbarLayout.setTitle(mMovie.title);
        overviewTextView.setText(mMovie.overview);
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getActivity());
        releaseDateTextView.setText(dateFormat.format(mMovie.releaseDate));
        ratingTextView.setText(mMovie.voteAverage + "/10"); //TODO: Use String res with placeholder
        if (mMovie.posterPalette != null) {
            toolbarLayout.setContentScrimColor(mMovie.posterPalette.mutedColor);
            fab.setBackgroundTintList(ColorStateList.valueOf(mMovie.posterPalette.vibrantColor));
            for (TextView textView : mTextViews) {
                textView.setTextColor(mMovie.posterPalette.vibrantColor);
            }
        }
    }

    @Override
    public void onRefresh() {
        mApp.getAPI().getMovieDetails(mMovie.id).enqueue(this);
    }

    @Click
    void fabClicked() {
        //TODO: Change icon and remove from db if already favored
        mMovie.save();
        Snackbar.make(cl, "Movie favored!", LENGTH_LONG).show();
        scrollView.scrollTo(0, 0);
        toolbarLayout.scrollTo(0, 0);
        //swipeRefreshLayout.scrollTo(0, 0);
    }

    @Override
    public void onResponse(Response<Movie> response, Retrofit retrofit) {
        swipeRefreshLayout.setRefreshing(false);
        if (response.isSuccess()) {
            mMovie = response.body();
            mVideoAdapter.replaceData(mMovie.videos.results);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Log.d(t);
        Snackbar.make(cl, "Oops! Unable to get movie details…", LENGTH_LONG).show();
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        bindViewsToData();
        scrollView.scrollTo(0, 0);
        toolbarLayout.scrollTo(0, 0);
    }
}
