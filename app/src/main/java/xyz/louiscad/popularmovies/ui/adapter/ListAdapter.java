package xyz.louiscad.popularmovies.ui.adapter;

import android.view.View;

import java.util.List;

import xyz.louiscad.popularmovies.util.recyclerview.RecyclerViewAdapterBase;
import xyz.louiscad.popularmovies.util.recyclerview.ViewWrapper;

/**
 * Created by Louis Cognault on 02/12/15.
 */
public abstract class ListAdapter<Data, V extends View & ViewWrapper.Binder<Data>>
        extends RecyclerViewAdapterBase<Data, V> {

    private List<Data> mDataList;

    public final void replaceData(List<Data> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    protected final Data getData(int position) {
        return mDataList.get(position);
    }

    @Override
    public final int getItemCount() {
        return (mDataList == null ? 0 : mDataList.size());
    }
}
