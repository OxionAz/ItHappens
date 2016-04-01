package com.oxionaz.ithappens.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class StorySyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static StorySyncAdapter sStorySyncAdapter = null;

    @Override
    public void onCreate() {
        synchronized (sSyncAdapterLock) {
            if (sStorySyncAdapter == null) {
                sStorySyncAdapter = new StorySyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sStorySyncAdapter.getSyncAdapterBinder();
    }
}
