package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnnotif;
    Button btnbig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnnotif = findViewById(R.id.btnNotif);
        btnbig = findViewById(R.id.btnBig);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        btnnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"My Notification");
                builder.setContentTitle("Message Notification");
                builder.setContentText("Hello This is Text Message");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
                managerCompat.notify( 1,builder.build());
            }
        });
        btnbig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Assign big picture notification
                NotificationCompat.BigPictureStyle bpStyle = new NotificationCompat.BigPictureStyle();
                bpStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.mountain)).build();
// Set the intent to fire when the user taps on
                Intent rIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://tutlane.com/"));
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, rIntent, 0);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Big Picture Notification Example")
                        .addAction(R.drawable.ic_share, "Share", pendingIntent)
                        .setStyle(bpStyle);
                mBuilder.setContentIntent(pendingIntent);
// Sets an ID for the notification
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// It will display the notification in notification bar
                notificationManager.notify(02, mBuilder.build());
            }
        });

    }
}