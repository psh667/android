package com.androidside.statusbardemoa1;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StatusBarDemoA1 extends Activity implements
        View.OnClickListener {
    NotificationManager notiMgr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        notiMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        
        Button button = (Button) findViewById(R.id.button);        
        button.setOnClickListener(this);
    } 

    public void onClick(View arg0) {
        Intent intent = new Intent(Intent.ACTION_DIAL, 
                Uri.parse("tel:0101234567"));
        
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
         
        Notification notification = 
            new Notification(R.drawable.icon, "전화", System.currentTimeMillis());
        notification.setLatestEventInfo(StatusBarDemoA1.this, 
                "전화걸기",
                "전화 걸 시간입니다.", 
                pendingIntent);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notiMgr.notify(0, notification);
    }
}