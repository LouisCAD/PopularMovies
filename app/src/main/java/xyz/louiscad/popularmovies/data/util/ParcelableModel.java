package xyz.louiscad.popularmovies.data.util;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.parceler.Parcel;
import org.parceler.ParcelClass;

/**
 * Created by Louis Cognault on 13/12/15.
 */
@ParcelClass(value = ModelAdapter.class, annotation = @Parcel(converter = ModelAdapterConverter.class))
public abstract class ParcelableModel extends BaseModel {
}
