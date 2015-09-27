package com.oxionaz.ithappens.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.ui.adapters.StoryAdapter;

import org.androidannotations.annotations.AfterViews;
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
        storyAdapter = new StoryAdapter(getFavoriteStories());
        recycler_view_content.setAdapter(storyAdapter);
    }

    private List<Story> getFavoriteStories(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).equalTo("favorite", true).findAll();
    }
}
