package com.oxionaz.ithappens;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Александр on 22.09.2015.
 */
public class ItHappensApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.getInstance(this);
    }
}
