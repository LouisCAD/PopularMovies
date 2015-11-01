package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.parceler.Parcel;

import java.util.List;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Model for TMDb API result
 * @see Movie
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
@Parcel
public class MovieDiscoverResult {
    public int page;
    public List<Movie> results;
    public int total_pages;
    public long total_results;
}
