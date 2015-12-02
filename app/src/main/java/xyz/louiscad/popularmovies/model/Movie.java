package xyz.louiscad.popularmovies.model;

import android.net.Uri;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import xyz.louiscad.popularmovies.util.ImageUtil;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Model for Movies from TMDb
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
@Parcel
public class Movie {

    public static final String MOVIE_EXTRA = "movie";

    public long id;
    public String title;
    public String original_title;
    public String overview;
    public float popularity;
    public int vote_average;
    public long vote_count;
    public Date release_date;
    public String original_language;
    String backdrop_path;
    String poster_path;
    public Uri backdropUrl;
    public Uri posterUrl;
    public PaletteLite posterPalette;
    //Optional data get with /movie/{id}
    public String tagline;
    public String status;
    public long budget;
    public List<Genre> genres;
    public List<ProductionCompany> production_companies;
    public VideosResult videos;

    @OnJsonParseComplete
    void onParseComplete() {
        posterUrl = ImageUtil.getPosterUri(poster_path);
        backdropUrl = ImageUtil.getBackdropUri(backdrop_path);
    }
}
