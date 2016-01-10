package xyz.louiscad.popularmovies.util.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Louis Cognault on 10/09/15.
 */
public class ViewHolder<V extends View> extends RecyclerView.ViewHolder {

    public final V view;

    public ViewHolder(V itemView) {
        super(itemView);
        view = itemView;
    }

    @SuppressWarnings("unchecked cast")
    public ViewHolder(@LayoutRes int layoutResId, ViewGroup parent) {
        this((V) inflate(layoutResId, parent));
    }

    @SuppressWarnings("unchecked cast")
    private static <V extends View> V inflate(@LayoutRes int layoutResId, ViewGroup parent) {
        return (V) LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
    }
}
