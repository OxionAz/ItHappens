package com.oxionaz.ithappens.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.rest.RestService;
import com.oxionaz.ithappens.ui.adapters.MyFragmentsAdapter;
import com.oxionaz.ithappens.ui.fragments.AboutFragment_;
import com.oxionaz.ithappens.ui.fragments.FavoritesFragment_;
import com.oxionaz.ithappens.ui.fragments.StoryFragment_;
import com.oxionaz.ithappens.util.NetworkConnectionUtil;
import com.oxionaz.ithappens.util.RealmService;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;

@EActivity(R.layout.activity_main)
    public class MainActivity extends AppCompatActivity {

    MyFragmentsAdapter myFragmentsAdapter;

    @ViewById
    ViewPager vpPager;

    @ViewById
    View main_content;

    @ViewById
    PagerTabStrip pager_header;

    @ViewById
    Toolbar toolbar;

    @AfterViews
    void ready(){
        if (getStories().isEmpty())
        if (NetworkConnectionUtil.isNetworkConnected(this)){
            loadStory();
        } else {
        Snackbar.make(main_content, "Не удалось загрузить истории, проверьте интернет подключение", Snackbar.LENGTH_SHORT).show();
        }
        setSupportActionBar(toolbar);

        List<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new StoryFragment_());
        fragmentList.add(new FavoritesFragment_());
        fragmentList.add(new AboutFragment_());

        pager_header.setTextColor(getResources().getColor(R.color.white));
        pager_header.setTabIndicatorColor(getResources().getColor(R.color.white));
        myFragmentsAdapter = new MyFragmentsAdapter(getSupportFragmentManager(), fragmentList);
        vpPager.setAdapter(myFragmentsAdapter);
    }

    @Background
    void loadStory(){
        RestService restService = new RestService();
        Observable<List<Story>> storyList = restService.addStory();
        storyList.subscribe(new Action1<List<Story>>() {
            @Override
            public void call(List<Story> stories) {
                RealmService.saveAll(stories, getApplicationContext());
            }
        });
    }

    private List<Story> getStories(){
        Realm realm = Realm.getInstance(this);
        return realm.where(Story.class).findAll();
    }
}
