package com.oxionaz.ithappens.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.rest.Queries;
import com.oxionaz.ithappens.sync.StorySyncAdapter;
import com.oxionaz.ithappens.ui.adapters.StoryAdapter;
import com.oxionaz.ithappens.util.NetworkConnectionUtil;
import com.oxionaz.ithappens.util.UpdaterCallBack;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import java.util.List;
import io.realm.Realm;

@EFragment(R.layout.story_layout)
public class StoryFragment extends Fragment implements UpdaterCallBack {

    private StoryAdapter storyAdapter;
    private boolean check = true;

    @ViewById
    FloatingActionButton fab;

    @ViewById(R.id.refresh_story)
    SwipeRefreshLayout swipeRefreshLayout;

    @ViewById
    RecyclerView recycler_view_content;

    @ViewById
    View main_content, list_item;

    @StringRes
    String internet_error, alert_positive, alert_negative, alert_message_story, alert_title_story;

    @Bean
    Queries queries = new Queries(getActivity());

    @AfterViews
    void ready(){
        queries.registerCallBack(this);
        queries.setFragmentView(main_content);
        recycler_view_content.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view_content.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionUtil.isNetworkConnected(getActivity())) {
                    queries.loadStories();
                } else {
                    Snackbar.make(main_content, internet_error, Snackbar.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        if (check) checkDB();
    }

    @Click
    void fab(){
        if (!getStories().isEmpty()) {
            AlertDialog confirmDelete = new AlertDialog.Builder(getActivity(), R.style.AlertDialog)
                    .setPositiveButton(alert_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteAndUpdateStories();
                        }
                    }).setNegativeButton(alert_negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setMessage(alert_message_story)
                    .setTitle(alert_title_story)
                    .create();
            confirmDelete.getWindow().setWindowAnimations(R.style.AlertDialogDel);
            confirmDelete.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @UiThread
    public void loadData(){
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
                swipeRefreshLayout.setRefreshing(false);
                storyAdapter = new StoryAdapter(getStories(), getContext());
                recycler_view_content.setAdapter(storyAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Story>> loader) {
            }
        });
    }

    public void checkDB(){
        if (getStories().isEmpty()) if (NetworkConnectionUtil.isNetworkConnected(getActivity())){
            swipeRefreshLayout.setRefreshing(true);
            queries.loadStories();
        } else {
            Snackbar.make(main_content, internet_error, Snackbar.LENGTH_SHORT).show();
        }
        check = false;
    }

    private void deleteAndUpdateStories(){
        Realm realm = Realm.getInstance(getContext());
        List<Story> storyList = realm.where(Story.class).equalTo("favorite", false).findAll();
        realm.beginTransaction();
        storyList.clear();
        realm.commitTransaction();
        loadData();
    }

    private List<Story> getStories(){
        Realm realm = Realm.getInstance(getActivity());
        return realm.where(Story.class).findAll();
    }

    @Override
    public void callBackSuccess() {
        loadData();
    }
}