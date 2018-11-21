package shelton.c169.wgustudentplanner;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {

    static int notificationID;
    String channel_ID = "notification";

    @Override
    public void onReceive(Context context, Intent intent) {

        String[] alarmText = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
        String title = alarmText[0];
        String text = alarmText[1];

        createNotificationChannel(context, channel_ID);
        Notification notification = new NotificationCompat.Builder(context, channel_ID)
                .setSmallIcon(R.drawable.ic_access_alarm_black_24dp)
                .setContentTitle(title)
                .setContentText(text).build();

        NotificationManager notificationManager = (NotificationManager)context
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);

    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
               NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
