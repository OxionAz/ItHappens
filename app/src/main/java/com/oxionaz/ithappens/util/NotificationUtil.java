package com.oxionaz.ithappens.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.ui.activity.MainActivity_;

public class NotificationUtil {
    private static final int NOTIFICATION_ID = 12345;

    public static void updateNotification(Context context){

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            Intent intent = new Intent(context, MainActivity_.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            builder.setSmallIcon(R.mipmap.ic_launcher);
            builder.setLights(Color.GREEN, 300, 1500);
            builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            builder.setAutoCancel(true);

            String title = context.getString(R.string.app_name);
            String contentText = context.getString(R.string.notification_message);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            builder.setLargeIcon(largeIcon);
            builder.setContentTitle(title);
            builder.setContentText(contentText);

            Notification notification = builder.build();

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(NOTIFICATION_ID, notification);
    }
}
