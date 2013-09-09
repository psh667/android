package com.appstudio.android.sample.ch_10;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class BarActivity extends Activity {
	private static final int PROGRESS = 0x1;

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    Button progress_button;
    TextView progress_progress;
    
    private Handler mHandler = new Handler();
    SeekBar seek_bar; 
    TextView seek_progress;
    TextView seek_tracking;
    RatingBar rating_bar;
    TextView rating_progress;
    
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.barmain);
        
        progress_button = (Button) findViewById(R.id.progress_button);
        progress_button.setOnClickListener(new ProgressButtonClickHandler());
        
        seek_bar = (SeekBar) findViewById(R.id.seek_bar);
        seek_progress = (TextView) findViewById(R.id.seek_progress);
        seek_tracking = (TextView) findViewById(R.id.seek_tracking);
        seek_bar.setOnSeekBarChangeListener(new seekBarChangeHandler());
        
        rating_bar = (RatingBar) findViewById(R.id.rating_bar);
        rating_bar.setOnRatingBarChangeListener(new ratingBarChangeHandler());
        
        rating_progress = (TextView) findViewById(R.id.rating_progress);
    }

    class ratingBarChangeHandler implements OnRatingBarChangeListener
    {
		@Override
		 public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            rating_progress.setText("New Rating: " + rating);
        }
    }
    
    class seekBarChangeHandler implements OnSeekBarChangeListener
    {
		@Override
		public void onProgressChanged(SeekBar seek_bar, int progress, boolean fromUser) {
			seek_progress.setText(Integer.toString(progress));
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			seek_tracking.setText("Started...");
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			seek_tracking.setText("Stopped...");
		}
    }
    
    class ProgressButtonClickHandler implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			 mProgress = (ProgressBar) findViewById(R.id.progress_bar);
			 progress_progress = (TextView) findViewById(R.id.progress_progress);
			 mProgress.setMax(100);
			 mProgressStatus = 0; mProgress.setProgress(mProgressStatus);
		        new Thread(new Runnable() {
		            public void run() {
		                while (mProgressStatus < 100) {
		                    doWork();
		                    mHandler.post(new Runnable() {
		                        public void run() {
		                            mProgress.setProgress(mProgressStatus);
		                            progress_progress.setText("progressbar progress is " + mProgressStatus);
		                        }
		                    });
		                }
		            }

		        }).start();
		}
		
	}
    
 	protected void doWork() {
		 try {
             Thread.sleep(100);
             mProgressStatus++;
         } catch (InterruptedException e) {
             Log.e("ERROR", "Thread Interrupted");
         }
	}
    
}
