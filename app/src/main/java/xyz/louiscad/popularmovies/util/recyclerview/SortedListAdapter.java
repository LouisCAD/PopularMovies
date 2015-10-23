package xyz.louiscad.popularmovies.util.recyclerview;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Louis Cognault on 11/10/15.
 */
public abstract class SortedListAdapter<Data extends Comparable<Data>, V extends View & ViewWrapper.Binder<Data>>
        extends RecyclerViewAdapterBase<Data, V> {

    private SortedList<Data> mDataList;

    public SortedListAdapter() {
        mDataList = new SortedList<>(provideClass(), provideCallback(this));
    }

    protected abstract SortedListCallback<Data> provideCallback(RecyclerView.Adapter adapter);

    protected abstract Class<Data> provideClass();

    @Override
    protected Data getData(int position) {
        return mDataList.get(position);
    }

    public final SortedList<Data> getDataList() {
        return mDataList;
    }
}
