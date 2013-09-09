package com.androidside.servicedemob2;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.androidside.service.IRemoteService;
 
public class ServiceDemoB2 extends Activity {
    IRemoteService remoteService;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button play = (Button)findViewById(R.id.play);
        play.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(new Intent(IRemoteService.class.getName()), serviceConn, Context.BIND_AUTO_CREATE);
            }
        });
        
        
        Button stop = (Button)findViewById(R.id.stop);
        stop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remoteService != null) {
                    unbindService(serviceConn);
                }
            }
        });     

        Button position = (Button)findViewById(R.id.position);
        position.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (remoteService != null) {
                    showPosition();
                }
            }
        }); 
    }
    
    private void showPosition() {
        String position = "";
        try {
            position = remoteService.getPosition();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Position " + position, Toast.LENGTH_LONG).show();
    }
    
    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("tag", "onServiceConnected");
            remoteService = IRemoteService.Stub.asInterface(service);
            try {
                remoteService.play();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            remoteService = null;            
        }
    };
}