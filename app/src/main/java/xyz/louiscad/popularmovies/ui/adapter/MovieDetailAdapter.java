package xyz.louiscad.popularmovies.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.model.Movie;
import xyz.louiscad.popularmovies.model.Review;
import xyz.louiscad.popularmovies.model.ReviewsResult;
import xyz.louiscad.popularmovies.util.recyclerview.ViewHolder;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;

public class MovieDetailAdapter extends RecyclerView.Adapter<ViewHolder<?>> {

    public static final int VIEW_TYPE_RELEASE_DATE = R.layout.list_item_release_date;
    public static final int VIEW_TYPE_RATING = R.layout.list_item_rating;
    public static final int VIEW_TYPE_OVERVIEW = R.layout.list_item_overview;
    public static final int VIEW_TYPE_VIDEOS = R.layout.list_item_videos_list;
    public static final int VIEW_TYPE_REVIEW_TITLE = R.layout.list_item_review_title;
    public static final int VIEW_TYPE_REVIEW = R.layout.list_item_comment;

    /**
     * Contains View types for adapter positions.
     */
    private static final int[] VIEW_TYPES = {
            VIEW_TYPE_RELEASE_DATE,
            VIEW_TYPE_RATING,
            VIEW_TYPE_OVERVIEW,
            VIEW_TYPE_VIDEOS,
            VIEW_TYPE_REVIEW_TITLE
    };

    private Movie mMovie;

    private VideoItemAdapter mVideoAdapter;
    private LinearLayoutManager mVideoLayoutManager;

    private List<Review> mReviews;

    private int mReviewsCount = 0;

    public MovieDetailAdapter() {
        mVideoAdapter = new VideoItemAdapter();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mVideoLayoutManager = new LinearLayoutManager(recyclerView.getContext(), HORIZONTAL, false);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mVideoLayoutManager = null;
    }

    public void replaceData(Movie movie) {
        mMovie = movie;
        mReviewsCount = 0;
        mVideoAdapter.replaceData(movie.videos == null ? null : movie.videos.results);
        notifyDataSetChanged();
    }

    public void addReviews(ReviewsResult reviewsResult) {
        int itemCount = getItemCount();
        mReviewsCount += reviewsResult.results.size();
        mReviews = reviewsResult.results;
        notifyItemRangeInserted(itemCount, mReviewsCount);
    }

    @Override
    public ViewHolder<?> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_RELEASE_DATE:
            case VIEW_TYPE_RATING:
            case VIEW_TYPE_OVERVIEW:
            case VIEW_TYPE_REVIEW:
                return new ViewWrapper<>(viewType, parent);
            case VIEW_TYPE_VIDEOS:
            case VIEW_TYPE_REVIEW_TITLE:
                return new ViewHolder<>(viewType, parent);
            default:
                throw new IllegalArgumentException("unknown viewType: " + viewType);
        }
    }

    @Override
    @SuppressWarnings("unchecked call")
    public void onBindViewHolder(ViewHolder<?> holder, int position) {
        if (holder.view instanceof RecyclerView && VIEW_TYPES[position] == VIEW_TYPE_VIDEOS) {
            RecyclerView videoRV = ((RecyclerView) holder.view);
            videoRV.setLayoutManager(mVideoLayoutManager);
            videoRV.setAdapter(mVideoAdapter);
        } else if (position < VIEW_TYPES.length) {
            if (VIEW_TYPES[position] != VIEW_TYPE_REVIEW_TITLE) {
                ((ViewWrapper<Movie, ?>) holder).view.bind(mMovie);
            } else {
                //noinspection ConstantConditions
                ((TextView) holder.view).setTextColor(mMovie.posterPalette.vibrantColor);
            }
        } else {
            ((ViewWrapper<Review, ?>) holder).view.bind(mReviews.get(position - VIEW_TYPES.length));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < VIEW_TYPES.length) return VIEW_TYPES[position];
        else return VIEW_TYPE_REVIEW;
    }

    @Override
    public int getItemCount() {
        return VIEW_TYPES.length + mReviewsCount;
    }
}
