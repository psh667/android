/*
 * closejob.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */
package com.msi.manning.UnlockingAndroid;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

public class CloseJob extends Activity {

    ProgressDialog myprogress;
    Handler progresshandler;
    Message msg;
    JobEntry je = null;

    private closejobView sc = null;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // unwrap the bundle containing the Job we are interested in
        Intent startingIntent = getIntent();
        Log.i("CH12::CloseJob", "onCreate");
        if (startingIntent != null) {
            Log.i("CH12::CloseJob", "onCreate not null");
            Bundle b = startingIntent.getExtras();
            if (b == null) {
                Log.i("CH12::CloseJob", "bad bundle");
                // "bad bundle?";
            } else {
                // user our helper method to extract the Job data from the bundle
                this.je = JobEntry.fromBundle(b);
                Log.i("CH12::CloseJob", "from Bundle");
            }
        }

        this.sc = new closejobView(this);
        setContentView(this.sc);

        if (this.je == null) {
            // if we get here without a job, something went wrong!
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // add out menu options
        menu.add(0, 0, 0, "Sign & Close");
        menu.add(0, 1, 1, "Cancel");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Prefs myprefs = new Prefs(getApplicationContext());

        switch (item.getItemId()) {
            case 0:
                try {
                    this.myprogress = ProgressDialog.show(this, "Closing Job ", "Saving Signature to Network", true,
                        false);
                    this.progresshandler = new Handler() {

                        @Override
                        public void handleMessage(Message msg) {
                            // process incoming messages here
                            switch (msg.what) {
                                case 0:
                                    // update progress bar
                                    CloseJob.this.myprogress.setMessage("" + (String) msg.obj);
                                    break;
                                case 1:
                                    CloseJob.this.myprogress.cancel();
                                    finish();
                                    break;
                            }
                            super.handleMessage(msg);
                        }

                    };

                    Thread workthread = new Thread(new DoCloseJob(myprefs));

                    workthread.start();

                } catch (Exception e) {
                    // tell user we failed....
                    Log.d("closejob", e.getMessage());
                    this.msg = new Message();
                    this.msg.what = 1;
                    this.progresshandler.sendMessage(this.msg);
                }
                return true;
            case 1:
                // bail
                finish();
                return true;
        }
        return false;
    }

    class DoCloseJob implements Runnable {

        Prefs _myprefs;

        DoCloseJob(Prefs p) {
            this._myprefs = p;
        }

        public void run() {

            try {
                FileOutputStream os = getApplication().openFileOutput("sig.jpg", 0);
                CloseJob.this.sc.Save(os);
                os.flush();
                os.close();
                // reopen to so we can send this data to server
                File f = new File(getApplication().getFileStreamPath("sig.jpg").toString());
                long flength = f.length();

                FileInputStream is = getApplication().openFileInput("sig.jpg");
                byte data[] = new byte[(int) flength];
                int count = is.read(data);
                if (count != (int) flength) {
                    // bad read?
                }
                CloseJob.this.msg = new Message();
                CloseJob.this.msg.what = 0;
                CloseJob.this.msg.obj = ("Connecting to Server");
                CloseJob.this.progresshandler.sendMessage(CloseJob.this.msg);

                URL url = new URL(this._myprefs.getServer() + "/closejob.php?jobid=" + CloseJob.this.je.get_jobid());
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                BufferedOutputStream wr = new BufferedOutputStream(conn.getOutputStream());
                wr.write(data);
                wr.flush();
                wr.close();

                CloseJob.this.msg = new Message();
                CloseJob.this.msg.what = 0;
                CloseJob.this.msg.obj = ("Data Sent");
                CloseJob.this.progresshandler.sendMessage(CloseJob.this.msg);

                // Get the response
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                Boolean bSuccess = false;
                while ((line = rd.readLine()) != null) {
                    if (line.indexOf("SUCCESS") != -1) {
                        bSuccess = true;
                    }
                    // Process line...
                    Log.v("closejob", line);
                }
                wr.close();
                rd.close();

                if (bSuccess) {
                    CloseJob.this.msg = new Message();
                    CloseJob.this.msg.what = 0;
                    CloseJob.this.msg.obj = ("Job Closed Successfully");
                    CloseJob.this.progresshandler.sendMessage(CloseJob.this.msg);

                    // mark status as closed
                    CloseJob.this.je.set_status("CLOSED");

                    // setup an intent to return a result
                    Intent resultIntent = new Intent();
                    resultIntent.putExtras(CloseJob.this.je.toBundle());

                    // tell caller that we've succeeded in closing this job
                    CloseJob.this.setResult(1, resultIntent);

                } else {
                    CloseJob.this.msg = new Message();
                    CloseJob.this.msg.what = 0;
                    CloseJob.this.msg.obj = ("Failed to Close Job");
                    CloseJob.this.progresshandler.sendMessage(CloseJob.this.msg);

                    // tell caller we failed
                    CloseJob.this.setResult(0);
                }
            } catch (Exception e) {
                Log.d("CH12", "Failed to submit job close signature: " + e.getMessage());
            }
            CloseJob.this.msg = new Message();
            CloseJob.this.msg.what = 1;
            CloseJob.this.progresshandler.sendMessage(CloseJob.this.msg);
        }
    }

    public class closejobView extends View {

        Bitmap _bitmap;
        Canvas _canvas;
        final Paint _paint;
        int lastX;
        int lastY;

        public closejobView(Context c) {
            super(c);
            Log.i("CH12::CloseJob", "closejobView constructor");
            // setup our pen
            this._paint = new Paint();
            this._paint.setColor(Color.BLACK);
            this.lastX = -1;
        }

        public boolean Save(OutputStream os) {
            try {
                // let's add some text to our image
                // this could be for watermarking, etc.
                // for example, we could add GPS coordinates to the image to
                // prove that the image was recorded at a particular location, etc.
                this._canvas.drawText("Unlocking Android", 10, 10, this._paint);
                this._canvas.drawText("http://manning.com/ableson", 10, 25, this._paint);
                this._canvas.drawText("http://android12.msi-wireless.com", 10, 40, this._paint);
                this._bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                invalidate();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public void Reset() {
            // TODO: clear image
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            Log.i("CH12::CloseJob", "closejobView:onSizeChanged");
            Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas();
            canvas.setBitmap(img);

            if (this._bitmap != null) {
                canvas.drawBitmap(img, 0, 0, null);
            }
            this._bitmap = img;
            this._canvas = canvas;
            this._canvas.drawColor(Color.WHITE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            if (this._bitmap != null) {
                canvas.drawBitmap(this._bitmap, 0, 0, null);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            int action = event.getAction();

            int X = (int) event.getX();
            int Y = (int) event.getY();

            switch (action) {
                case MotionEvent.ACTION_UP:
                    // reset location
                    this.lastX = -1;
                    break;

                case MotionEvent.ACTION_DOWN:
                    if (this.lastX != -1) {
                        if ((int) event.getX() != this.lastX) {
                            this._canvas.drawLine(this.lastX, this.lastY, X, Y, this._paint);
                        }
                    }
                    this.lastX = (int) event.getX();
                    this.lastY = (int) event.getY();
                    break;

                case MotionEvent.ACTION_MOVE:

                    if (this.lastX != -1) {
                        this._canvas.drawLine(this.lastX, this.lastY, X, Y, this._paint);
                    }

                    this.lastX = (int) event.getX();
                    this.lastY = (int) event.getY();
                    break;
            }
            // make sure the screen refreshes....
            invalidate();

            return true;
        }
    }

}
