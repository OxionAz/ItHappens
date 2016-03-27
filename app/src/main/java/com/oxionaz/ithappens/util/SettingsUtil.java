package com.oxionaz.ithappens.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.oxionaz.ithappens.R;

/**
 * Created by Александр on 27.03.2016.
 */
public class SettingsUtil {

    private SharedPreferences pref;
    private Context context;

    public SettingsUtil(Context context){
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public boolean getSyncCheck() {
        return pref.getBoolean(context.getString(R.string.pref_update_key),
                Boolean.parseBoolean(context.getString(R.string.pref_update_default)));
    }

    public String getIntervalPref() {
        return pref.getString(context.getString(R.string.pref_update_interval_key),
                context.getString(R.string.pref_update_interval_default));
    }

    public boolean getNotificationPref() {
        return pref.getBoolean(context.getString(R.string.pref_notification_key),
                Boolean.parseBoolean(context.getString(R.string.pref_notification_default)));
    }
}
