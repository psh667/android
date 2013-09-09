package com.appstudio.android.sample.ch_16;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProgressDialogActivity extends Activity {
	
    /** Nested class that performs progress calculations (counting) */
    class ProgressThread extends Thread {
        Handler mHandler;
        final static int STATE_DONE = 0;
        final static int STATE_RUNNING = 1;
        int mState;
        int total;
       
        ProgressThread(Handler h) {
            mHandler = h;
        }
       
        public void run() {
            mState = STATE_RUNNING;   
            total = 0;
            while (mState == STATE_RUNNING) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Log.e("ERROR", "Thread Interrupted");
                }
                Message msg = mHandler.obtainMessage();
                msg.arg1 = total;
                mHandler.sendMessage(msg);
                total++;
            }
        }
        
        /* sets the current state for the thread,
         * used to stop the thread */
        public void setState(int state) {
            mState = state;
        }
    }

	static final int PROGRESS_DIALOG = 0;
    Button button;
    ProgressThread progressThread;
    ProgressDialog progressDialog;
    
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressdialogmain);
		
		// Setup the button that starts the progress dialog
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                showDialog(PROGRESS_DIALOG);
            }
        }); 
        
	}
	
	protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(ProgressDialogActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Loading...");
            return progressDialog;
        default:
            return null;
        }
    }

    // Define the Handler that receives messages from the thread and update the progress
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int total = msg.arg1;
            progressDialog.setProgress(total);
            if (total >= 100){
                dismissDialog(PROGRESS_DIALOG);
                progressThread.setState(ProgressThread.STATE_DONE);
            }
        }
    };
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog.setProgress(0);
            progressThread = new ProgressThread(handler);
            progressThread.start();
        }
    }
    
}



 

