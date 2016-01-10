package xyz.louiscad.popularmovies.util.recyclerview;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Louis Cognault on 02/01/16.
 */
public class ViewWrapper<Data, V extends View & ViewWrapper.Binder<Data>> extends ViewHolder<V> {

    public ViewWrapper(V itemView) {
        super(itemView);
    }

    public ViewWrapper(@LayoutRes int layoutResId, ViewGroup parent) {
        super(layoutResId, parent);
    }

    public interface Binder<Data> {
        void bind(Data data);
    }
}
