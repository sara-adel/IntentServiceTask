package com.sara.intentservicetask;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.sara.intentservicetask.Tools.CurrentLocation;

import java.io.IOException;

public class MyIntentService extends IntentService {

    public static final String Message = "message";
    public static final int NOTIFICATION_ID = 1365;

    public static final int LOCATION_Interval = 5 * 600000;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            try {
              // wait(10000);
               new CurrentLocation(this , LOCATION_Interval);
               //current.notify();

            } finally {

            }
        }
        String text = intent.getStringExtra(Message);
        showText(text);
    }

    private void showText(final String text){
       //   Toast.makeText(this , text , Toast.LENGTH_LONG).show();
        Log.e("msg", "start");
        Intent i = new Intent(this , MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(i);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Hello Sara")
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setContentText(text)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID ,notification);

    }

}
