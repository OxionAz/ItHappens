package com.oxionaz.ithappens.ui.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.oxionaz.ithappens.R;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Александр on 22.09.2015.
 */
@EFragment(R.layout.about_layout)
public class AboutFragment extends Fragment {
    private static final String LOG_TAG = "About";

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Вызван метод onResume");
    }
}