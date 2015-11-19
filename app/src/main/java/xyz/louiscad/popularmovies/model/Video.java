package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 19/11/15.
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
public class Video {
    public long id;
    public String iso_639_1;
    public String key;
    public String name;
    public String site;
    public int size;
    public String type;
}
