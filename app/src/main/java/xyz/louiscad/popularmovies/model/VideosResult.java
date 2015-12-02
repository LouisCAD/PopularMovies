package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import org.parceler.Parcel;

import java.util.List;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 19/11/15.
 */
@Parcel
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
public class VideosResult {
    public long id;
    public List<Video> results;
}
