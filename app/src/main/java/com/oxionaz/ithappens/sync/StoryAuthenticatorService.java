package com.oxionaz.ithappens.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Александр on 27.03.2016.
 */
public class StoryAuthenticatorService extends Service {

    private StoryAuthenticator mStoryAuthenticator;

    @Override
    public void onCreate() {
        mStoryAuthenticator = new StoryAuthenticator(this);
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
