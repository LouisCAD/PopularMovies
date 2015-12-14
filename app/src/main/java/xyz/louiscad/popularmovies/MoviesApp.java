package xyz.louiscad.popularmovies;

import android.app.Application;

import com.bluelinelabs.logansquare.LoganSquare;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import java.util.Date;

import xyz.louiscad.popularmovies.rest.DateConverter;
import xyz.louiscad.popularmovies.rest.TheMovieDb;

@EApplication
public class MoviesApp extends Application {

    private TheMovieDb.API mAPI;

    @AfterInject
    void init() {
        FlowManager.init(this);
        Fresco.initialize(this);
        LoganSquare.registerTypeConverter(Date.class, new DateConverter());
        mAPI = TheMovieDb.createAPI(this);
    }

    public TheMovieDb.API getAPI() {
        return mAPI;
    }
}
