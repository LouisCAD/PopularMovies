package xyz.louiscad.popularmovies.model;

import android.content.Context;
import android.support.v7.graphics.Palette;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.parceler.Parcel;

import xyz.louiscad.popularmovies.R;

import static android.support.v4.content.ContextCompat.getColor;
import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 30/10/15.
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
@Parcel
public class PaletteLite {

    public PaletteLite(Context context, Palette palette) {
        mutedColor = palette.getMutedColor(getColor(context, R.color.colorPrimary));
        lightVibrantColor = palette.getVibrantColor(getColor(context, R.color.colorAccent));
    }

    public PaletteLite() {
    }

    public int mutedColor;
    public int lightVibrantColor;
}
