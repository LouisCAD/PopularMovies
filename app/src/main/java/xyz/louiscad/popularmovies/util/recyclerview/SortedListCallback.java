package xyz.louiscad.popularmovies.util.recyclerview;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

/**
 * Created by Louis Cognault on 11/10/15.
 */
public abstract class SortedListCallback<Data extends Comparable<Data>> extends SortedListAdapterCallback<Data> {
    /**
     * Creates a {@link SortedList.Callback} that will forward data change events to the provided
     * Adapter.
     *
     * @param adapter The Adapter instance which should receive events from the SortedList.
     */
    public SortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public int compare(Data o1, Data o2) {
        return o1.compareTo(o2);
    }
}
