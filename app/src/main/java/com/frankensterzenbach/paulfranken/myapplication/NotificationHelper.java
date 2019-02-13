package com.frankensterzenbach.paulfranken.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.TaskStackBuilder;

public class NotificationHelper extends ContextWrapper {

private static final String paul_Channel_id="com.example.android.customnotifications.Paul";
private static final String paul_Channel_name="paul channel";
private NotificationManager manager;


    public NotificationHelper(Context base) {
        super(base);
        creatChannels();
    }

    public void creatChannels(){
        NotificationChannel channel= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(paul_Channel_id, paul_Channel_name, NotificationManager.IMPORTANCE_HIGH);

            channel.enableLights(true);
            channel.enableVibration(true);
            channel.enableVibration(true);

            channel.setLightColor(Color.GREEN);


            getManager().createNotificationChannel(channel);

        }
    }

    public NotificationManager getManager() {
        if(manager==null){
            manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification.Builder getChanelNotification(String title, String body){



        Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        return new Notification.Builder(getApplicationContext(),paul_Channel_id)
                .setTicker("Stundenplan 2.0")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Stundenplan 2.0")
                .setContentText("Ein neuer Vertretungsplan ist online.")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)


                .setContentIntent(resultPending)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL); // notification intent






    }

}
