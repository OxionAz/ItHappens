package com.oxionaz.ithappens.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class StoryAuthenticatorService extends Service {

    private StoryAuthenticator mStoryAuthenticator;

    @Override
    public void onCreate() {
        mStoryAuthenticator = new StoryAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
