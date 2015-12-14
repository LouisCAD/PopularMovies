package xyz.louiscad.popularmovies.ui.fragment;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.popularmovies.R;
import xyz.louiscad.popularmovies.ui.fragment.MovieListFragment.Sort;

import static xyz.louiscad.popularmovies.ui.fragment.MovieListFragment.NEWEST;
import static xyz.louiscad.popularmovies.ui.fragment.MovieListFragment.POPULARITY;
import static xyz.louiscad.popularmovies.ui.fragment.MovieListFragment.VOTE_AVERAGE;

/**
 * Created by Louis Cognault on 14/12/15.
 */
@EFragment(R.layout.fragment_movies)
public class MoviesFragment extends Fragment {

    @ViewById TabLayout tabLayout;
    @ViewById ViewPager viewPager;

    @AfterViews
    void init() {
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        pagerAdapter.setUpTabIcons(tabLayout);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {

        private final Page[] PAGES = new Page[]{
                new Page(R.string.mostPopular, R.drawable.ic_whatshot_white_24dp, POPULARITY),
                new Page(R.string.topRated, R.drawable.ic_star_white_24dp, VOTE_AVERAGE),
                new Page(R.string.newest, R.drawable.ic_new_releases_white_24dp, NEWEST),
                new Page(R.string.favorites, R.drawable.ic_favorite_white_24dp)
        };

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position].getFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(PAGES[position].title);
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        public void setUpTabIcons(TabLayout tabLayout) {
            for (int i = 0; i < PAGES.length; i++) {
                //noinspection ConstantConditions
                tabLayout.getTabAt(i).setIcon(PAGES[i].icon);
            }
        }
    }

    private class Page {

        @StringRes
        public final int title;
        @DrawableRes
        public final int icon;
        @Sort
        public final String sort;

        private Page(@StringRes int title, @DrawableRes int icon, @Sort String sort) {
            this.title = title;
            this.sort = sort;
            this.icon = icon;
        }

        private Page(@StringRes int title, @DrawableRes int icon) {
            this.title = title;
            this.sort = null;
            this.icon = icon;
        }

        public Fragment getFragment() {
            if (sort == null) return FavoriteMoviesFragment_.builder()
                    .build();
            else return MovieListFragment_.builder()
                    .mSort(sort)
                    .build();
        }
    }
}
