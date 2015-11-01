package xyz.louiscad.popularmovies;

import android.app.Application;

import com.bluelinelabs.logansquare.LoganSquare;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import java.util.Date;

import xyz.louiscad.popularmovies.rest.DateConverter;
import xyz.louiscad.popularmovies.rest.TheMovieDb;

/**
 * Created by Louis Cognault on 10/10/15.
 */
@EApplication
public class MoviesApp extends Application {

    private TheMovieDb.API mAPI;

    @AfterInject
    void init() {
        Fresco.initialize(this);
        LoganSquare.registerTypeConverter(Date.class, new DateConverter());
        mAPI = TheMovieDb.createAPI(this);
    }

    public TheMovieDb.API getAPI() {
        return mAPI;
    }
}
