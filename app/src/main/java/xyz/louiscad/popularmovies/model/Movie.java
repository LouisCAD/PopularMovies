package xyz.louiscad.popularmovies.model;

import android.net.Uri;
import android.support.annotation.DrawableRes;

import com.bluelinelabs.logansquare.annotation.JsonIgnore;
import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.data.PopMoviesDatabase;
import xyz.louiscad.popularmovies.util.ImageUtil;
import xyz.louiscad.popularmovies.data.util.ParcelableModel;

import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS;
import static com.bluelinelabs.logansquare.annotation.JsonObject.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

/**
 * Model for Movies from TMDb
 */
@Table(databaseName = PopMoviesDatabase.NAME)
@Parcel
@JsonObject(
        fieldDetectionPolicy = NONPRIVATE_FIELDS,
        fieldNamingPolicy = LOWER_CASE_WITH_UNDERSCORES
)
public class Movie extends ParcelableModel {

    public static final String MOVIE_EXTRA = "movie";

    @Column
    @PrimaryKey
    public long id;
    @Column
    public String title;
    @Column
    public String originalTitle;
    @Column
    public String overview;
    @Column
    public float popularity;
    @Column
    public int voteAverage;
    @Column
    public long voteCount;
    @Column
    public Date releaseDate;
    @Column
    public String originalLanguage;
    @Column
    @JsonIgnore
    public Uri backdropUrl;
    @Column
    @JsonIgnore
    public Uri posterUrl;

    String posterPath;
    String backdropPath;
    public PaletteLite posterPalette;
    //Optional data get with /movie/{id}
    public String tagline;
    public String status;
    public long budget;
    public List<Genre> genres;
    public List<ProductionCompany> productionCompanies;
    public VideosResult videos;

    private boolean isFavored = true;

    public boolean isFavored() {
        return isFavored;
    }

    @Override
    public void save() {
        super.save();
        isFavored = true;
    }

    @Override
    public void delete() {
        super.delete();
        isFavored = false;
    }

    public void addToFavorites() {
        save();
    }

    public void removeFromFavorites() {
        delete();
    }

    /**
     * @return favorite icon for the new state
     */
    @DrawableRes
    public int toggleFavoredState() {
        if (isFavored) delete();
        else save();
        return (isFavored ?
                R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_outline_24dp);
    }

    @DrawableRes
    public int getFavoredStateIcon() {
        return (isFavored ?
                R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_outline_24dp);
    }

    @OnJsonParseComplete
    void onParseComplete() {
        isFavored = exists();
        posterUrl = ImageUtil.getPosterUri(posterPath);
        backdropUrl = ImageUtil.getBackdropUri(backdropPath);
    }
}
