package xyz.louiscad.popularmovies.model;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Date;
import java.util.List;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;

/**
 * Created by Louis Cognault on 09/10/15.
 */
@JsonObject(fieldDetectionPolicy = NONPRIVATE_FIELDS)
public class Movie {
    public long id;
    public String title;
    public String original_title;
    public String overview;
    public float popularity;
    public int vote_average;
    public long vote_count;
    public Date release_date;
    public String original_language;
    public String backdrop_path;
    public String poster_path;
    //Optional data get with /movie/{id}
    public String tagline;
    public String status;
    public long budget;
    public List<Genre> genres;
    public List<ProductionCompany> production_companies;
}
