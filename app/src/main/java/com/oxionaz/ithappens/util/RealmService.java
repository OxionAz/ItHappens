package com.oxionaz.ithappens.util;

import android.content.Context;
import android.util.Log;
import com.oxionaz.ithappens.database.Story;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmService {

    public static void saveAll(List<Story> stories, Context context) {
        Realm realm = Realm.getInstance(context);
        if (getStories(context).isEmpty()) {
            realm.beginTransaction();
            realm.copyToRealm(stories);
            realm.commitTransaction();
            RealmResults<Story> result = realm.where(Story.class).findAll();
            Log.d("total", "" + result.size());
        } else {
            List<Story> storyListDB = new ArrayList<>();
            for (int i = 0; i< getStories(context).size(); i++){
                Story story = new Story();
                story.setDesc(getStories(context).get(i).getDesc());
                story.setElementPureHtml(getStories(context).get(i).getElementPureHtml());
                story.setFavorite(getStories(context).get(i).getFavorite());
                storyListDB.add(story);
            }
            int i;
            for (i = 0; i < stories.size(); i++) {
                if (!stories.get(i).getElementPureHtml().equalsIgnoreCase(storyListDB.get(i).getElementPureHtml())) {
                    Story story = new Story();
                    story.setDesc(stories.get(i).getDesc());
                    story.setElementPureHtml(stories.get(i).getElementPureHtml());
                    storyListDB.add(i, story);
                } else break;
            }
            if (i != 0) {
                realm.beginTransaction();
                realm.clear(Story.class);
                realm.copyToRealm(storyListDB);
                realm.commitTransaction();
            }
        }
    }

    private static List<Story> getStories(Context context){
        Realm realm = Realm.getInstance(context);
        return realm.where(Story.class).findAll();
    }
}