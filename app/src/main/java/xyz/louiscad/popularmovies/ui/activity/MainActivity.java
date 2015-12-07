package xyz.louiscad.popularmovies.ui.activity;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import trikita.log.Log;
import xyz.louiscad.popularmovies.R;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;

    @StringRes
    int mSubtitleResId = 0;

    @AfterViews
    void init() {
        setSupportActionBar(toolbar);
        if (mSubtitleResId != 0) setSubtitle(mSubtitleResId);
    }

    @OptionsItem
    void actionSettingsSelected() {
        Log.i("Settings clicked");
    }

    public void setSubtitle(@StringRes int subtitleResId) {
        if (toolbar == null) mSubtitleResId = subtitleResId;
        else toolbar.setSubtitle(subtitleResId);
    }
}
