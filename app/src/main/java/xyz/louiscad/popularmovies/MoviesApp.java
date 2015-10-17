package xyz.louiscad.popularmovies;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

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
        mAPI = TheMovieDb.createAPI(this);
    }

    public TheMovieDb.API getAPI() {
        return mAPI;
    }
}
