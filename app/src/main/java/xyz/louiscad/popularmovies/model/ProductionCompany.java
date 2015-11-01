package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.parceler.Parcel;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * @see Movie
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
@Parcel
public class ProductionCompany {
    public String name;
    public long id;
}
