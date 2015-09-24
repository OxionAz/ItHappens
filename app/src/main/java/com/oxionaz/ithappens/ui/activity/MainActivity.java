package com.oxionaz.ithappens.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.ui.adapters.FragmentAdapter;
import com.oxionaz.ithappens.ui.fragments.StoryFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import io.realm.Realm;

@EActivity(R.layout.activity_main)
    public class MainActivity extends FragmentActivity {

    FragmentPagerAdapter adapterViewPager;

    @ViewById
    Toolbar toolbar;

    @ViewById
    ViewPager vpPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterViewPager = new FragmentAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
    }

    @AfterViews
    void ready(){
//        setSupportActionBar(toolbar);
    }




}
