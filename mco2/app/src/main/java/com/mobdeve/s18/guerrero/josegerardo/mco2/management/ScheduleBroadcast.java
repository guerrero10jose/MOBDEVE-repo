package com.mobdeve.s18.guerrero.josegerardo.mco2.management;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mobdeve.s18.guerrero.josegerardo.mco2.R;

public class ScheduleBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String temp = intent.getStringExtra("Task");
        String tag = intent.getStringExtra("Tag");
        int broadcastid = intent.getIntExtra("BroadcastId", 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "MOBDEVE_FINAL")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Task Due!")
                .setContentText(tag + ": " + temp)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(broadcastid, builder.build());


    }
}
