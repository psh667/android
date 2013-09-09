package com.msi.manning.windwaves;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Splash screen startup Activity.
 * 
 * @author charliecollins
 * 
 */
public class StartActivity extends Activity {

    private static final String CLASSTAG = StartActivity.class.getSimpleName();

    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            startActivity(new Intent(StartActivity.this, MapViewActivity.class));
        }
    };

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        Log.v(Constants.LOGTAG, " " + StartActivity.CLASSTAG + " onCreate");

        this.setContentView(R.layout.start_activity);
    }

    @Override
    public void onStart() {
        super.onStart();
        // move to the next screen via a delayed message
        new Thread() {

            @Override
            public void run() {
                handler.sendMessageDelayed(handler.obtainMessage(), 3000);
            };
        }.start();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
