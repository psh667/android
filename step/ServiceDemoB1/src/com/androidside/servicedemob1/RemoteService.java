package com.androidside.servicedemob1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

import com.androidside.service.IRemoteService;

public class RemoteService extends Service {
    private MediaPlayer player;
    
    public class RemoteServiceImpl extends IRemoteService.Stub {
        @Override
        public String getPosition() throws RemoteException {
            if (player != null && player.isPlaying()) {
                return player.getCurrentPosition()/1000 + "√ /" + player.getDuration()/1000 + "√ ";
            } else {
                return "";
            }
        }

        @Override
        public void play() throws RemoteException {
            if (player != null && !player.isPlaying()) {
                player.start();
            }
        }        
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return new RemoteServiceImpl();
    }
    
    @Override
    public void onCreate() {
        super.onCreate();        
        player = MediaPlayer.create(this, R.raw.lemontree);
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