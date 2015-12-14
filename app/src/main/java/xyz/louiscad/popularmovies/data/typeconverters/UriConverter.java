package xyz.louiscad.popularmovies.data.typeconverters;

import android.net.Uri;

import com.raizlabs.android.dbflow.annotation.TypeConverter;

/**
 * Created by Louis Cognault on 13/12/15.
 */
@TypeConverter
public class UriConverter extends com.raizlabs.android.dbflow.converter.TypeConverter<String, Uri>{
    @Override
    public String getDBValue(Uri model) {
        return model.toString();
    }

    @Override
    public Uri getModelValue(String data) {
        return Uri.parse(data);
    }
}
