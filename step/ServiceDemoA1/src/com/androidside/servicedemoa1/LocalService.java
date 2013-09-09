package com.androidside.servicedemoa1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class LocalService extends Service {
    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        player = MediaPlayer.create(this, R.raw.lemontree);
    }
 
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        
        if (player != null && !player.isPlaying()) {
            player.start();
        }
    }

    @Override
    public void onDestroy() {        
        super.onDestroy();
        
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
    }
}