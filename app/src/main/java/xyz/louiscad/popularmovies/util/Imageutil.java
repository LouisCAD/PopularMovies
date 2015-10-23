package xyz.louiscad.popularmovies.util;

import android.net.Uri;

/**
 * Created by Louis Cognault on 16/10/15.
 */
public class ImageUtil {

    public static final String SECURE_BASE_URL = "https://image.tmdb.org/t/p/";

    public static final String POSTER_DEFAULT_SIZE = "w500";

    public static Uri getPosterUri(String path) {
        String strUrl = SECURE_BASE_URL + POSTER_DEFAULT_SIZE + path;
        return Uri.parse(strUrl);
    }
}
