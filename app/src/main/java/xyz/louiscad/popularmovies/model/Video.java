package xyz.louiscad.popularmovies.model;

import android.net.Uri;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;

import org.parceler.Parcel;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 19/11/15.
 */
@Parcel
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
public class Video {
    public long id;
    public String iso_639_1;
    public String key;
    public String name;
    public String site;
    public int size;
    public String type;
    public Uri thumbnailUrl;

    @OnJsonParseComplete
    void onParseComplete() {
        thumbnailUrl = Uri.parse("http://img.youtube.com/vi/" + key + "/mqdefault.jpg");
    }
}
