package com.oxionaz.ithappens.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity_.class));
        finish();
    }
}
