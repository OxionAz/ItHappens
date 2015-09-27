package com.oxionaz.ithappens.ui.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;
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
@EFragment(R.layout.story_layout)
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
        Toast.makeText(getContext(),"Добавлено "+getStoriesCount().toString()+" историй", Toast.LENGTH_SHORT).show();
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
