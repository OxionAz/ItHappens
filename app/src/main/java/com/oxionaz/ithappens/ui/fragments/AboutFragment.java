package com.oxionaz.ithappens.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.rest.api.Api;
import com.oxionaz.ithappens.ui.adapters.StoryAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by Александр on 22.09.2015.
 */
@EFragment(R.layout.news_layout)
public class AboutFragment extends Fragment {

    private static final String LOG_TAG = "Story";
    private StoryAdapter storyAdapter;

    @ViewById
    RecyclerView recycler_view_content;

    @ViewById
    FloatingActionButton fab;

    public static AboutFragment newInstance(int page, String title) {
        AboutFragment fragmentFirst = new AboutFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @AfterViews
    void ready(){
        fetchStories();
        recycler_view_content.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_content.setLayoutManager(linearLayoutManager);
        storyAdapter = new StoryAdapter(getStories());
        recycler_view_content.setAdapter(storyAdapter);
        Toast.makeText(getContext(),"Добавлено "+getStoriesCount().toString()+" историй", Toast.LENGTH_SHORT).show();
    }

    private void fetchStories() {
        Observable<List<Story>> listObservable = new Api().loadStories();

        listObservable.subscribe(new Action1<List<Story>>() {
            @Override
            public void call(List<Story> stories) {
                saveAll(stories);
            }
        });
    }

    private void saveAll(List<Story> stories) {
        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        realm.clear(Story.class);
        realm.copyToRealm(stories);
        realm.commitTransaction();
        RealmResults<Story> result = realm.where(Story.class).findAll();
        Log.d("total", "" + result.size());
    }

    private List<Story> getStories(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).findAll();
    }

    private Long getStoriesCount(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).count();
    }
}
