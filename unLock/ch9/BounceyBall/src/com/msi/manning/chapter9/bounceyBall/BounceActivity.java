package com.msi.manning.chapter9.bounceyBall;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.os.Handler;
import android.os.Message;

public class BounceActivity extends Activity {

     protected static final int GUIUPDATEIDENTIFIER = 0x101;

     Thread myRefreshThread = null;

     /* Our 'ball' is located within this View */
     BounceView myBounceView = null;

     Handler myGUIUpdateHandler = new Handler() {

          // @Override
          public void handleMessage(Message msg) {
               switch (msg.what) {
                    case BounceActivity.GUIUPDATEIDENTIFIER:
                         /* Repaint the BounceView
                          * (where the ball is in) */
                         myBounceView.invalidate();
                         break;
               }
               super.handleMessage(msg);
          }
     };

     /** Called when the activity is first created. */
     @Override
     public void onCreate(Bundle icicle) {
          super.onCreate(icicle);
          // Set fullscreen
          this.requestWindowFeature(Window.FEATURE_NO_TITLE);

          // Create a
          this.myBounceView = new BounceView(this);
          this.setContentView(this.myBounceView);

          /* create a Thread that will
           * periodically send messages
           * to our Handler */
          new Thread(new RefreshRunner()).start();
     }

     class RefreshRunner implements Runnable {
          // @Override
          public void run() {
               while (! Thread.currentThread().isInterrupted()) {
                    // Send Message to the Handler which will call the invalidate() method of the BoucneView
                    Message message = new Message();
                    message.what = BounceActivity.GUIUPDATEIDENTIFIER;
                    BounceActivity.this.myGUIUpdateHandler.sendMessage(message);

                    try {
                         Thread.sleep(100); // a 10th of a second
                    } catch (InterruptedException e) {
                         Thread.currentThread().interrupt();
                    }
               }
          }
     }
}