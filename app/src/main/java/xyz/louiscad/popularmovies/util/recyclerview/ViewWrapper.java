package xyz.louiscad.popularmovies.util.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Louis Cognault on 10/09/15.
 */
public class ViewWrapper<Data, V extends View & ViewWrapper.Binder> extends RecyclerView.ViewHolder {

    private V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }

    public interface Binder<Data> {
        void bind(Data data);
    }
}
