package xyz.louiscad.popularmovies.util;

import android.net.Uri;

/**
 * Utility class for TMDb images
 */
public class ImageUtil {

    public static final String SECURE_BASE_URL = "https://image.tmdb.org/t/p/";

    public static final String POSTER_DEFAULT_SIZE = "w500";
    public static final String BACKDROP_DEFAULT_SIZE= "w1280";

    public static Uri getPosterUri(String path) {
        return getImageUri(path, POSTER_DEFAULT_SIZE);
    }

    public static Uri getBackdropUri(String path) {
        return getImageUri(path, BACKDROP_DEFAULT_SIZE);
    }

    private static Uri getImageUri(String path, String size) {
        String strUrl = SECURE_BASE_URL + size + path;
        return Uri.parse(strUrl);
    }
}
