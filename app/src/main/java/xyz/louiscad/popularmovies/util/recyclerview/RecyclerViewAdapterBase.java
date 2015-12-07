package xyz.louiscad.popularmovies.util.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Louis Cognault on 10/09/15.
 */
public abstract class RecyclerViewAdapterBase<Data, V extends View & ViewWrapper.Binder<Data>> extends RecyclerView.Adapter<ViewWrapper<Data, V>> {
    @Override
    public final ViewWrapper<Data, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(inflate(getLayout(viewType), parent));
    }

    @LayoutRes
    protected abstract int getLayout(int viewType);

    @Override
    public final void onBindViewHolder(ViewWrapper<Data, V> holder, int position) {
        V view = holder.getView();
        Data data = getData(position);
        view.bind(data);
    }

    protected abstract Data getData(int position);

    @SuppressWarnings("unchecked cast")
    private V inflate(@LayoutRes int layoutResId, ViewGroup parent) {
        return (V) LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
    }
}
