package com.oxionaz.ithappens.util;

import android.content.Context;
import android.util.Log;

import com.oxionaz.ithappens.database.Story;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Александр on 28.09.2015.
 */
public class RealmService {

    public static void saveAll(List<Story> stories, Context context) {
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.clear(Story.class);
        realm.copyToRealm(stories);
        realm.commitTransaction();
        RealmResults<Story> result = realm.where(Story.class).findAll();
        Log.d("total", "" + result.size());
    }
}
