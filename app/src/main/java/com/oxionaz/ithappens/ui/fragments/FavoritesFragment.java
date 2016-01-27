package com.oxionaz.ithappens.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.ui.adapters.StoryAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Александр on 22.09.2015.
 */
@EFragment(R.layout.favorite_layout)
public class FavoritesFragment extends Fragment {

    private StoryAdapter storyAdapter;

    @ViewById
    RecyclerView recycler_view_content;

    @ViewById
    FloatingActionButton fab;

    @AfterViews
    void ready(){
        recycler_view_content.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_content.setLayoutManager(linearLayoutManager);
        storyAdapter = new StoryAdapter(getFavoriteStories(), getContext());
        recycler_view_content.setAdapter(storyAdapter);
    }

//    @Click
//    void fab(){
////        List<Story> stories = getStories();
////        stories.
//        Realm realm = Realm.getInstance(getContext());
//        realm.beginTransaction();
//        realm.
//        realm.commitTransaction();
//    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(1, null, new LoaderManager.LoaderCallbacks<List<Story>>() {
            @Override
            public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
                final android.support.v4.content.AsyncTaskLoader<List<Story>> loader = new android.support.v4.content.AsyncTaskLoader<List<Story>>(getContext()) {
                    @Override
                    public List<Story> loadInBackground() {
                        return getFavoriteStories();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
                recycler_view_content.setAdapter(new StoryAdapter(getFavoriteStories(), getContext()));
            }

            @Override
            public void onLoaderReset(Loader<List<Story>> loader) {
            }
        });
    }

    private List<Story> getStories(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).findAll();
    }

    private List<Story> getFavoriteStories(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).equalTo("favorite", true).findAll();
    }
}
