package com.oxionaz.ithappens.ui.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.ui.adapters.FavoriteStoryAdapter;
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

    private static final String LOG_TAG = "Favorite";
    private FavoriteStoryAdapter favoriteStoryAdapter;

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
    }

    @Click
    public void fab(){
        if (!getFavoriteStories().isEmpty()) {
            AlertDialog confirmDelete = new AlertDialog.Builder(getActivity(), R.style.AlertDialog)
                    .setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteFavorite();
                        }
                    }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setMessage("Удалить все истории из избранного?")
                    .setTitle("Очистка избранного")
                    .create();
            confirmDelete.getWindow().setWindowAnimations(R.style.AlertDialogDel);
            confirmDelete.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Вызван метод onResume");
        loadData();
    }

    void loadData(){
        getLoaderManager().restartLoader(0, null, new LoaderManager.LoaderCallbacks<List<Story>>() {
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
                favoriteStoryAdapter = new FavoriteStoryAdapter(getFavoriteStories(), getContext());
                recycler_view_content.setAdapter(favoriteStoryAdapter);
            }

            @Override
            public void onLoaderReset(Loader<List<Story>> loader) {
            }
        });
    }

    private void deleteFavorite(){
        List<Story> storyList = getStories();
        Realm realm = Realm.getInstance(getContext());
        realm.beginTransaction();
        for (int i = 0; i < storyList.size(); i++) if (storyList.get(i).getFavorite()) {
            storyList.get(i).setFavorite(false);
        }
        realm.commitTransaction();
        loadData();
    }

    private List<Story> getFavoriteStories(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).equalTo("favorite", true).findAll();
    }

    private List<Story> getStories(){
        Realm realm = Realm.getInstance(getContext());
        return realm.where(Story.class).findAll();
    }
}
