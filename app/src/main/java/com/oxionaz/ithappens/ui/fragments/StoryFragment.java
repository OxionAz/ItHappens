package com.oxionaz.ithappens.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.rest.RestService;
import com.oxionaz.ithappens.ui.adapters.StoryAdapter;
import com.oxionaz.ithappens.util.NetworkConnectionUtil;
import com.oxionaz.ithappens.util.RealmService;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;

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
    View main_content, list_item;

    @ViewById
    FloatingActionButton fab;

    @AfterViews
    void ready(){
        recycler_view_content.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_content.setLayoutManager(linearLayoutManager);
        storyAdapter = new StoryAdapter(getStories(), getContext());
        recycler_view_content.setAdapter(storyAdapter);
    }

//    @Click(R.id.favorite_button)
//    void setFavorite(){
//        int visiblePosition = recycler_view_content.getChildLayoutPosition(list_item);
//        View v = recycler_view_content.getChildAt(position - visiblePosition);
//        Button button  = (Button) v.findViewById(R.id.button);//id кнопки в разметке айтема
//        button.setBackground(background);// вставить свое значение для фона
//    }
//
//    Realm realm = Realm.getInstance(getContext());
//    realm.beginTransaction();
//    if(stories.getFavorite()){
//        stories.setFavorite(false);} else stories.setFavorite(true);
//    realm.commitTransaction();

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Story>>() {
            @Override
            public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
                final android.support.v4.content.AsyncTaskLoader<List<Story>> loader = new android.support.v4.content.AsyncTaskLoader<List<Story>>(getContext()) {
                    @Override
                    public List<Story> loadInBackground() {
                        return getStories();
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
                recycler_view_content.setAdapter(new StoryAdapter(getStories(), getContext()));
            }

            @Override
            public void onLoaderReset(Loader<List<Story>> loader) {
            }
        });
    }

    @Click
    void fab(){
        if (NetworkConnectionUtil.isNetworkConnected(getContext())){
            loadStory();
            Toast.makeText(getContext(),"Добавлено "+getStoriesCount().toString()+" историй", Toast.LENGTH_SHORT).show();
        } else {
            Snackbar.make(main_content, "Не удалось загрузить истории, проверьте интернет подключение", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Background
    void loadStory(){
        RestService restService = new RestService();
        Observable<List<Story>> storyList = restService.addStory();
        storyList.subscribe(new Action1<List<Story>>() {
            @Override
            public void call(List<Story> stories) {
                RealmService.saveAll(stories, getContext());
            }
        });
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
