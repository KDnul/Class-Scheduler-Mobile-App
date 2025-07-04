package com.example.c196mobiledevelopment.UI;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.c196mobiledevelopment.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id = "test";
    static int notificationId;

    /**
     * When called, will grab the key from the appropriate caller and receive their context.
     * Then it will pop a notification in the system for the user with the appropriate context.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("key"))
                .setContentTitle("School App Alert").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, n);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
    }

    /**
     * Creates a notification and grabs the appropriate data to use.
     */
    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        CharSequence name = "mychannelname";
        String description = "mychanneldescription";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}