package com.oxionaz.ithappens.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.oxionaz.ithappens.sync.StorySyncAdapter;

/**
 * Created by Александр on 09.02.2016.
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 2500);
        StorySyncAdapter.initializeSyncAdapter(this);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity_.class));
        finish();
    }
}
