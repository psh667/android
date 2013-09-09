package kr.co.company.chap13lab1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class DownloadService extends IntentService {

  private int result = Activity.RESULT_CANCELED;

  public DownloadService() {
    super("DownloadService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Uri data = intent.getData();
    String urlPath = intent.getStringExtra("urlpath");
    String buffer=" ";
    
    InputStream stream = null;
    try {

      URL url = new URL(urlPath);
      stream = url.openConnection().getInputStream();
      InputStreamReader reader = new InputStreamReader(stream);
      int i = 0, next = -1;
      while ((next = reader.read()) != -1) {
        buffer += " " + (char)next;
        if( ++i > 10 ) break;
      }
      result = Activity.RESULT_OK;

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    Bundle extras = intent.getExtras();
    if (extras != null) {
      Messenger messenger = (Messenger) extras.get("MESSENGER");
      Message msg = Message.obtain();
      msg.arg1 = result;
      msg.obj = buffer;
      try {
        messenger.send(msg);
      } catch (android.os.RemoteException e1) {
        Log.w(getClass().getName(), "Exception sending message", e1);
      }

    }
  }
} 