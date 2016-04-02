package com.oxionaz.ithappens.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.oxionaz.ithappens.sync.StorySyncAdapter;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StorySyncAdapter.initializeSyncAdapter(this);
        Fabric.with(this, new Crashlytics());
        delayedStart();
    }

    private void delayedStart(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 2500);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity_.class));
        finish();
    }
}