package com.appstudio.android.sample.ch_27_2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiverService extends Service {
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;

    private static final Object mServiceSync = new Object();
    private static PowerManager.WakeLock wakeLock;
    
    private static final String TAG = "appstudio";

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread(this.getClass().getName(), Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public void onStart(Intent intent, int startId) {    	
      Message msg = mServiceHandler.obtainMessage();
      msg.arg1 = startId;
      msg.obj = intent;
      mServiceHandler.sendMessage(msg);
    }

    @Override
    public void onDestroy() {
      mServiceLooper.quit();
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }

    private final class ServiceHandler extends Handler {
      public ServiceHandler(Looper looper) {
        super(looper);
      }

      @Override
      public void handleMessage(Message msg) {
        int serviceId = msg.arg1;
        Intent intent = (Intent) msg.obj;
        handleSmsReceived(intent);
        finishService(SmsReceiverService.this, serviceId);
      }
    }


    private void handleSmsReceived(Intent intent) {
      Bundle bundle = intent.getExtras();
      if (bundle != null) {
          Object[] pdus = (Object[]) bundle.get("pdus");

          for (int i = 0; i < pdus.length;) {
              SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
              String fromAddress = message.getOriginatingAddress();
              String strMessage = message.getMessageBody().toString();
              Log.d(TAG, fromAddress + " "+strMessage);
 
              // For the purposes of this demo, we'll only handle the first received message.
              break;
          }   	  
      }
    }


    public static void finishService(Service service, int startId) {
      synchronized (mServiceSync) {
        if (wakeLock != null) {
          if (service.stopSelfResult(startId)) {
            wakeLock.release();
          }
        }
      }
    }

    
    public static void beginService(Context context, Intent intent) {
        synchronized (mServiceSync) {
          if (wakeLock == null) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, SmsReceiverService.class.getName());
          }
          wakeLock.acquire();
          context.startService(intent);
        }
      }    
  }
