package com.oxionaz.ithappens.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.sync.StorySyncAdapter;
import com.oxionaz.ithappens.ui.adapters.MyFragmentsAdapter;
import com.oxionaz.ithappens.ui.fragments.AboutFragment_;
import com.oxionaz.ithappens.ui.fragments.FavoritesFragment_;
import com.oxionaz.ithappens.ui.fragments.StoryFragment_;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
    public class MainActivity extends AppCompatActivity {

    private MyFragmentsAdapter myFragmentsAdapter;
    private static boolean exit = false;

    @ViewById
    ViewPager vpPager;

    @ViewById
    View main_content;

    @ViewById
    PagerTabStrip pager_header;

    @ViewById
    static
    Toolbar toolbar;

    @OptionsItem(R.id.action_settings)
    void menuClick(){
        startActivity(new Intent(this, SettingsActivity_.class));
    }

    @AfterViews
    void ready(){
        setSupportActionBar(toolbar);
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new StoryFragment_());
        fragmentList.add(new FavoritesFragment_());
        fragmentList.add(new AboutFragment_());

        pager_header.setTextColor(getResources().getColor(R.color.silver_text));
        pager_header.setTabIndicatorColor(getResources().getColor(R.color.white));
        pager_header.setTextSize(1, 16);
        myFragmentsAdapter = new MyFragmentsAdapter(getSupportFragmentManager(), fragmentList);
        vpPager.setAdapter(myFragmentsAdapter);
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1 || position == 0)
                    (myFragmentsAdapter.getItem(position)).onResume();
                //onUpdateView is public function at 'FirstFragment', insert your code here
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (exit) super.onBackPressed();
        exit = true;
        Snackbar.make(vpPager, "Нажмите еще раз для выхода", Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                exit = false;
            }
        }, 2000);
    }
}
