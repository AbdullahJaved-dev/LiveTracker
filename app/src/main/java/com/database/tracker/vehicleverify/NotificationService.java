package com.database.tracker.vehicleverify;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        //To deal with the Notification data
        Intent notifIn = new Intent(Intent.ACTION_VIEW);
        notifIn.setData(Uri.parse(remoteMessage.getData().get("link")));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIn, PendingIntent.FLAG_ONE_SHOT);

        dbHelper db=new dbHelper(this);

        db.insertNotification((int)System.currentTimeMillis(),remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),remoteMessage.getData().get("link"),null,null);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.notif_channel));
        builder.setContentTitle(remoteMessage.getNotification().getTitle());
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentText(remoteMessage.getNotification().getBody());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(21312, builder.build());

//        Date current= Calendar.getInstance().getTime();
//        int hours=current.getHours();
//        int mins=current.getMinutes();
//        int month=current.getMonth();
//        int day=current.getDay();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//        Date date = new Date();
//


    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();

    }
}
