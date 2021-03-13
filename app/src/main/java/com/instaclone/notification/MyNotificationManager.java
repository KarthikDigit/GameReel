package com.instaclone.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.instaclone.R;
import com.instaclone.dashboard.DashBoardActivity;

import java.util.Random;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyNotificationManager {

    private Context mCtx;
    private static MyNotificationManager mInstance;

    private MyNotificationManager(Context context) {
        mCtx = context;
    }

    public static synchronized MyNotificationManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNotificationManager(context);
        }
        return mInstance;
    }

    public void displayNotification(String title, String body, String type, String id, String status) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setContentText(body);


        /*
         *  Clicking on the notification will take us to this intent
         *  Right now we are using the MainActivity as this is the only activity we have in our application
         *  But for your project you can customize it as you want
         * */
        Intent resultIntent;
//        if (type.toLowerCase().equalsIgnoreCase("service")) {
//            resultIntent = new Intent(mCtx, ServiceManActivity.class);
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//            resultIntent.putExtra("status", status);
//
//        } else if (type.toLowerCase().equalsIgnoreCase("task")) {
//            resultIntent = new Intent(mCtx, SalesManDashboardScreenActivity.class);
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//            resultIntent.putExtra("status", status);
//
//        } else {
        resultIntent = new Intent(mCtx, DashBoardActivity.class);
//        }
        resultIntent.putExtra("type", type);
        resultIntent.putExtra("id", id);

//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        /*
         *  Now we will create a pending intent
         *  The method getActivity is taking 4 parameters
         *  All paramters are describing themselves
         *  0 is the request code (the second parameter)
         *  We can detect this code in the activity that will open by this we can get
         *  Which notification opened the activity
         * */
        PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        /*
         *  Setting the pending intent to notification builder
         * */

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr =
                (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);

        /*
         * The first parameter is the notification id
         * better don't give a literal here (right now we are giving a int literal)
         * because using this id we can modify it later
         * */
        if (mNotifyMgr != null) {

            Random random = new Random();

            int n = random.nextInt(999);

            mNotifyMgr.notify(n, mBuilder.build());

        }
    }
}
