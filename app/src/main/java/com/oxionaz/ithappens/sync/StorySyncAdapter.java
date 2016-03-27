package com.oxionaz.ithappens.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.rest.Queries;
import com.oxionaz.ithappens.util.NotificationUtil;
import com.oxionaz.ithappens.util.SettingsUtil;

/**
 * Created by Александр on 27.03.2016.
 */
public class StorySyncAdapter extends AbstractThreadedSyncAdapter {

    private SettingsUtil settingsUtil = new SettingsUtil(getContext());
    private Queries queries = new Queries(getContext());
    private static int hours;

    public StorySyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        hours = Integer.parseInt(settingsUtil.getIntervalPref());
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        if(settingsUtil.getSyncCheck()){
            queries.loadStories();
            if(settingsUtil.getNotificationPref()) NotificationUtil.updateNotification(getContext());
        }
    }

    //Sync Immediately
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }
    //Check account
    public static Account getSyncAccount(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));
        if ( null == accountManager.getPassword(newAccount) ) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) { return null; }
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        int SYNC_INTERVAL = 60 * 60 * hours;
        final int SYNC_FLEXTIME = SYNC_INTERVAL/3;
        StorySyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount, context.getString(R.string.content_authority), Bundle.EMPTY, SYNC_INTERVAL);
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder(). syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority). setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }
}
