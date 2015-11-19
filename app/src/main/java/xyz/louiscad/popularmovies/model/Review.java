package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 19/11/15.
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
public class Review {
    public String id;
    public String author;
    public String content;
    public String url;
}
