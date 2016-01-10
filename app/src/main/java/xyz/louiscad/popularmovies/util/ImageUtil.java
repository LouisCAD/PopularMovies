package xyz.louiscad.popularmovies.util;

import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Utility class for TMDb images
 */
public class ImageUtil {

    public static final String SECURE_BASE_URL = "https://image.tmdb.org/t/p/";

    public static final String POSTER_DEFAULT_SIZE = "w500";
    public static final String BACKDROP_DEFAULT_SIZE= "w1280";

    @Nullable
    public static Uri getPosterUri(String path) {
        return (path == null ? null : getImageUri(path, POSTER_DEFAULT_SIZE));
    }

    @Nullable
    public static Uri getBackdropUri(String path) {
        return (path == null ? null : getImageUri(path, BACKDROP_DEFAULT_SIZE));
    }

    private static Uri getImageUri(String path, String size) {
        String strUrl = SECURE_BASE_URL + size + path;
        return Uri.parse(strUrl);
    }
}
