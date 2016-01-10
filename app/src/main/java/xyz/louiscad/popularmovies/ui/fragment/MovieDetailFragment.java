package xyz.louiscad.popularmovies.ui.fragment;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewPropertyAnimator;

import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.louiscad.popularmovies.MoviesApp;
import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.PaletteLite;
import xyz.louiscad.popularmovies.model.ReviewsResult;
import xyz.louiscad.popularmovies.model.Video;
import xyz.louiscad.popularmovies.ui.adapter.MovieDetailAdapter;
import xyz.louiscad.popularmovies.util.Util;

import static android.os.Build.VERSION.SDK_INT;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * This Fragment is meant to display the details of a Movie
 */
@EFragment(R.layout.fragment_movie_detail)
public class MovieDetailFragment extends Fragment implements Callback, LoadingFragment.Caller,
        SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener {

    public static final int MIN_APPBAR_HEIGHT_PERCENTAGE_TO_SHOW_POSTER = 5;

    @FragmentArg
    @InstanceState
    Movie mMovie;

    @App
    MoviesApp mApp;

    @ViewById CoordinatorLayout cl;
    @ViewById AppBarLayout appbar;
    @ViewById Toolbar toolbar;
    @ViewById CollapsingToolbarLayout toolbarLayout;
    @ViewById SwipeRefreshLayout swipeRefreshLayout;
    @ViewById FloatingActionButton fab;

    @ViewById SimpleDraweeView backdropImage, posterImage;

    @ViewById RecyclerView recyclerView;

    private MovieDetailAdapter mAdapter;

    private ViewPropertyAnimator mPosterAnimator;

    private int mAppBarScrollRange;

    private boolean mIsPosterVisible = true;

    private boolean mJustCreated = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mAdapter = new MovieDetailAdapter();
    }

    @AfterViews
    void init() {
        mPosterAnimator = posterImage.animate();
        Util.setToolbarAsActionBar(getActivity(), toolbar);
        if (SDK_INT > 21) getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        mAppBarScrollRange = 0;
        appbar.addOnOffsetChangedListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        bindViewsToData(mJustCreated);
        mJustCreated = false;
    }

    private void bindViewsToData(boolean fullBind) {
        if (fullBind){
            mAdapter.replaceData(mMovie);
            swipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                    onRefresh();
                }
            });
        }
        fab.setImageResource(mMovie.getFavoredStateIcon());
        posterImage.setImageURI(mMovie.posterUrl);
        backdropImage.setImageURI(mMovie.backdropUrl);
        toolbarLayout.setTitle(mMovie.title);
        toolbar.setSubtitle(mMovie.tagline); //TODO: put this in the details RecyclerView
        if (mMovie.posterPalette != null) {
            toolbarLayout.setContentScrimColor(mMovie.posterPalette.mutedColor);
            fab.setBackgroundTintList(ColorStateList.valueOf(mMovie.posterPalette.vibrantColor));
        }
    }

    @Override
    public void onRefresh() {
        enqueue(mApp.getAPI().getMovieDetails(mMovie.id));
        enqueue(mApp.getAPI().getReviews(mMovie.id));
    }

    @Click
    void fabClicked() {
        fab.setImageResource(mMovie.toggleFavoredState());
    }

    @Override
    public void onResponse(Response response, Retrofit retrofit) {
        swipeRefreshLayout.setRefreshing(false);
        Object body = response.body();
        if (body instanceof Movie) {
            PaletteLite palette = mMovie.posterPalette;
            mMovie = (Movie) response.body();
            mMovie.posterPalette = palette;
            List<Video> videos = mMovie.videos.results;
            /**
             * Filter videos to keep only Youtube ones
             */
            for (int i = 0; i < videos.size(); i++) {
                Video video = videos.get(i);
                if (!video.site.equals("YouTube")) {
                    videos.remove(i);
                    i--;
                }
            }

            mAdapter.replaceData(mMovie);
        } else if (body instanceof ReviewsResult) {
            mAdapter.addReviews((ReviewsResult) body);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(cl, "Oops! Unable to get movie details…", LENGTH_LONG).show();
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
        bindViewsToData(true);
        toolbarLayout.scrollTo(0, 0);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mAppBarScrollRange == 0) mAppBarScrollRange = appBarLayout.getTotalScrollRange();
        int offsetPercentage = (Math.abs(verticalOffset) * 100) / mAppBarScrollRange;
        if (mIsPosterVisible && offsetPercentage > MIN_APPBAR_HEIGHT_PERCENTAGE_TO_SHOW_POSTER) {
            mIsPosterVisible = false;
            mPosterAnimator.cancel();
            mPosterAnimator.scaleX(0f).scaleY(0f).setDuration(400).start();
        } else if (!mIsPosterVisible && offsetPercentage < MIN_APPBAR_HEIGHT_PERCENTAGE_TO_SHOW_POSTER) {
            mIsPosterVisible = true;
            mPosterAnimator.cancel();
            mPosterAnimator.scaleX(1f).scaleY(1f).setDuration(400).start();
        }
    }

    @Override
    public void enqueue(Call request) {
        ((LoadingFragment.Caller) getContext()).enqueue(request);
    }
}
