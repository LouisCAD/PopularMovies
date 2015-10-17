package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 09/10/15.
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
public class ProductionCompany {
    public String name;
    public long id;
}
