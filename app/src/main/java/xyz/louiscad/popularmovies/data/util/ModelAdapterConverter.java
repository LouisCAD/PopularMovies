package xyz.louiscad.popularmovies.data.util;

import android.os.Parcel;

import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.parceler.ParcelConverter;

/**
 * Created by Louis Cognault on 13/12/15.
 */
public class ModelAdapterConverter implements ParcelConverter<ModelAdapter> {
    @Override
    public void toParcel(ModelAdapter input, Parcel parcel) {

    }

    @Override
    public ModelAdapter fromParcel(Parcel parcel) {
        return null;
    }
}
