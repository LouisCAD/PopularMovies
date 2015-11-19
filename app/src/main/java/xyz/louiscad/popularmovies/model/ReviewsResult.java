package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 19/11/15.
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
public class ReviewsResult {
    public long id;
    public int page;
    public List<Review> results;
    public int total_pages;
    public int total_results;
}
