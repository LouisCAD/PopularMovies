package xyz.louiscad.popularmovies.data;

import com.raizlabs.android.dbflow.BuildConfig;
import com.raizlabs.android.dbflow.annotation.Database;

import static com.raizlabs.android.dbflow.annotation.ConflictAction.REPLACE;

/**
 * Database and ContentProvider for favorite movies
 *
 * @see <a href="https://github.com/Raizlabs/DBFlow/blob/master/usage/ContentProviderGenerators.md">
 *     DBFlow's ContentProvider documentation</a>
 */
/*@ContentProvider(authority = PopMoviesDatabase.AUTHORITY,
        baseContentUri = PopMoviesDatabase.BASE_CONTENT_URI,
        databaseName = PopMoviesDatabase.NAME)*/
@Database(name = PopMoviesDatabase.NAME,
        version = PopMoviesDatabase.VERSION,
        insertConflict = REPLACE)
public class PopMoviesDatabase {

    public static final String NAME = "PopMovies";
    public static final int VERSION = 1;

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".provider";
    public static final String BASE_CONTENT_URI = "content://";

   /* @TableEndpoint(name = FavoriteMoviesEndpoint.FAVORITE_MOVIES)
    public static class FavoriteMoviesEndpoint {
        public static final String FAVORITE_MOVIES = "favoritemovies";

        @ContentUri(path = FAVORITE_MOVIES, type = ContentUri.ContentType.VND_MULTIPLE + FAVORITE_MOVIES)
        public static final Uri CONTENT_URI = buildUri(FAVORITE_MOVIES);

        private static Uri buildUri(String... paths) {
            Uri.Builder builder = Uri.parse(BASE_CONTENT_URI + AUTHORITY).buildUpon();
            for (String path : paths) {
                builder.appendPath(path);
            }
            return builder.build();
        }
    }*/
}
