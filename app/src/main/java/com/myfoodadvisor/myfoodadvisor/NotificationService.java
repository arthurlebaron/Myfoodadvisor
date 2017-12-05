package com.myfoodadvisor.myfoodadvisor;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Palencar on 30/11/2017.
 */

public class NotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(){
        //Creation d'un Channel pour les notif
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String id = "mmmmmhhh_Channel";
        CharSequence name = "MyFoodAdvisor Notification Channel";
        String description = "Channel utilisé pour les notifications de MyFoodAdvisor, Gardez la ligne!";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;

        //Paramètres du channel de notification de l'appli
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mNotificationManager.createNotificationChannel(mChannel);


        //Creation de la notification
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR);
        int PAM= calendar.get(Calendar.AM_PM);

        NotificationCompat.Builder builder;

        Intent intent1 = new Intent(this.getApplicationContext(), Acceuil.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        if (PAM==0) {
            String longText="Hey il est " + h + "h, N'oubliez pas de préparer votre lunch... Et de le déguster!";
            builder = new NotificationCompat.Builder(this, id)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pIntent)
                    .setContentTitle("MyFoodAdvisor")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(longText));
        } else{
            String longText = "Hey il est " + h + "h, Si vous ne sentez pas encore cette bonne odeur, il est temps d'aller préparer le souper! ";
            builder = new NotificationCompat.Builder(this, id)
                    .setSmallIcon(R.drawable.logo)
                    .setContentIntent(pIntent)
                    .setContentTitle("MyFoodAdvisor")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(longText));
        }
        Intent notificationIntent = new Intent(this, Acceuil.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        //Id de la notif aléatoire
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
        manager.notify(m, notification);
        Log.d("D","Notification");
    }
}


   /*


    public NotificationService() {
        super(NotificationService.class.getSimpleName());
    }

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification();
            }
            if (ACTION_DELETE.equals(action)) {
                processDeleteNotification(intent);
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification() {
        // Do something. For example, fetch fresh data from backend to create a rich notification?

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Scheduled Notification")
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorAccent))
                .setContentText("This notification has been triggered by Notification Service")
                .setSmallIcon(R.drawable.logo);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                new Intent(this, Acceuil.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}*/
