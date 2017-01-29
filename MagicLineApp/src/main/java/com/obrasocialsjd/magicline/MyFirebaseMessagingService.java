package com.obrasocialsjd.magicline;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Debug;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.obrasocialsjd.magicline.Announcements.AnnouncementDB;
import com.obrasocialsjd.magicline.Announcements.DatabaseAnnouncements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.i(TAG, "From: " + remoteMessage.getFrom());

        long time = remoteMessage.getSentTime();
        String dateString = new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date(time));

        String title = "", body = "";

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            for (String key : remoteMessage.getData().keySet()){
                if (key.equals("Title")) title = remoteMessage.getData().get(key);
                if (key.equals("Body")) body = remoteMessage.getData().get(key);
            }
            Log.i(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        //Register the message on the database
        if (getApplicationContext() != null) {
            DatabaseAnnouncements databaseAnnouncements = new DatabaseAnnouncements(getApplicationContext());
            databaseAnnouncements.addAnnouncement(
                    new AnnouncementDB(-1, title, body, dateString), DatabaseAnnouncements.TABLE_ANNOUNCEMENTSDOWNLOADED
            );

            //Check the addition (optional)
            for (AnnouncementDB announcementDB :
                    databaseAnnouncements.getAllAnnouncement(DatabaseAnnouncements.TABLE_ANNOUNCEMENTSDOWNLOADED)){
                Log.i("DatabaseDB","Announcement title: "+ announcementDB.get_title());
                Log.i("DatabaseDB","Announcement text: "+ announcementDB.get_text());
                Log.i("DatabaseDB","Announcement date: "+ announcementDB.get_time());
            }
        }
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String titleNotification = (title != null) ? title : getResources().getString(R.string.title_notification_default);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_directions_walk_black_24dp)
                .setContentTitle(titleNotification)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}