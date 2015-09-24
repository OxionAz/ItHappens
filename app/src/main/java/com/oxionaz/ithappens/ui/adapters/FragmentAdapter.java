package com.oxionaz.ithappens.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.oxionaz.ithappens.ui.fragments.AboutFragment_;
import com.oxionaz.ithappens.ui.fragments.FavoritesFragment_;
import com.oxionaz.ithappens.ui.fragments.StoryFragment_;

/**
 * Created by Александр on 24.09.2015.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;

    public FragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return StoryFragment_.newInstance(0, "Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return FavoritesFragment_.newInstance(1, "Page # 2");
            case 2: // Fragment # 1 - This will show SecondFragment
                return AboutFragment_.newInstance(2, "Page # 3");
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

}
