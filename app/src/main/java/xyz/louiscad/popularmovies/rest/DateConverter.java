package xyz.louiscad.popularmovies.rest;

import android.annotation.SuppressLint;

import com.bluelinelabs.logansquare.typeconverters.DateTypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Converts API date to {@link java.util.Date}. This class is used by LoganSquare serializer.
 */
public class DateConverter extends DateTypeConverter {

    @SuppressLint("SimpleDateFormat")
    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
