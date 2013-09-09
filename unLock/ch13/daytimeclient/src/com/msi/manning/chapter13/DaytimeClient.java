/*
 * UnlockingAndroid
 * 
 * Author: Frank Ableson fableson@msiservices.com
 * 
 * 
 */

package com.msi.manning.chapter13;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DaytimeClient extends Activity {

    Handler h;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);

        final TextView statuslabel = (TextView) findViewById(R.id.statuslabel);

        this.h = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // process incoming messages here
                switch (msg.what) {
                    case 0:
                        // update progress bar
                        Log.d("CH13", "data [" + (String) msg.obj + "]");
                        statuslabel.setText((String) msg.obj);
                        break;
                }
                super.handleMessage(msg);
            }

        };

        Button test = (Button) findViewById(R.id.testit);
        test.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {
                    // Perform action on click
                    Requester r = new Requester();
                    r.start();
                } catch (Exception e) {
                    Log.d("CH13 exception caught : ", e.getMessage());
                }
            }
        });
    }

    public class Requester extends Thread {

        Socket requestSocket;
        OutputStream out;
        InputStream in;
        String message;
        StringBuilder returnStringBuffer = new StringBuilder();
        Message lmsg;
        int ch;

        @Override
        public void run() {
            try {

                this.requestSocket = new Socket("127.0.0.1", 1024);
                InputStreamReader isr = new InputStreamReader(this.requestSocket.getInputStream(), "ISO-8859-1");
                while ((this.ch = isr.read()) != -1) {
                    this.returnStringBuffer.append((char) this.ch);
                }
                this.message = this.returnStringBuffer.toString();
                this.lmsg = new Message();
                this.lmsg.obj = this.message;
                this.lmsg.what = 0;
                DaytimeClient.this.h.sendMessage(this.lmsg);
                this.requestSocket.close();
            } catch (Exception ee) {
                Log.d("CH13", "failed to read data" + ee.getMessage());
            }
        }
    }
}
