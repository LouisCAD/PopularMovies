package xyz.louiscad.popularmovies.util.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Louis Cognault on 10/09/15.
 */
public abstract class RecyclerViewAdapterBase<Data, V extends View & ViewWrapper.Binder<Data>> extends RecyclerView.Adapter<ViewWrapper<Data, V>> {
    @Override
    public final ViewWrapper<Data, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @Override
    public final void onBindViewHolder(ViewWrapper<Data, V> holder, int position) {
        V view = holder.getView();
        Data data = getData(position);
        view.bind(data);
    }

    protected abstract Data getData(int position);
}
