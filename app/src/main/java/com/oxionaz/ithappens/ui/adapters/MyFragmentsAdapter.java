package com.oxionaz.ithappens.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Александр on 27.09.2015.
 */
public class MyFragmentsAdapter extends FragmentPagerAdapter {

    private final String titles[] = new String[]{"Истории","Избранное","О нас"};
    List<Fragment> fragmentList;

    public MyFragmentsAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
