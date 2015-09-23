package com.oxionaz.ithappens.ui.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.database.StoryS;
import com.oxionaz.ithappens.rest.RestService;
import com.oxionaz.ithappens.rest.model.StoryModel;
import com.oxionaz.ithappens.ui.adapters.StoryAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import io.realm.Realm;

/**
 * Created by Александр on 22.09.2015.
 */
@EFragment(R.layout.news_layout)
public class StoryFragment extends Fragment {

    private static final String LOG_TAG = "Story";
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
        storyAdapter = new StoryAdapter(getStories());
        recycler_view_content.setAdapter(storyAdapter);
        addStories();
        Toast.makeText(getContext(), getStoriesCount().toString(), Toast.LENGTH_SHORT).show();
    }

    @Background
    void addStories(){
        RestService restService = new RestService();
        List<StoryModel> response = restService.addStory();
        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        realm.copyToRealm(response);
        realm.commitTransaction();

//        Log.d(LOG_TAG, response.getSite()
//                + ", " + response.getName()
//                + ", " + response.getLink()
//                + ", " + response.getDesc()
//                + ", " + response.getElementPureHtml());
    }

    private List<StoryModel> getStories(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(StoryModel.class).findAll();
    }

    private Long getStoriesCount(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).count();
    }
}
