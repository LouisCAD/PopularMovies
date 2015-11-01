package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.parceler.Parcel;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * @see Movie
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
@Parcel
public class Genre {
    public long id;
    public String name;
}
