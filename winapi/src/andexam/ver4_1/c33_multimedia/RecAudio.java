package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import java.io.*;

import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class RecAudio extends Activity {
	MediaRecorder mRecorder = null;
	Button mStartBtn, mPlayBtn;
	boolean mIsStart = false;
	String Path = "";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recaudio);

		mStartBtn = (Button)findViewById(R.id.start);
		mPlayBtn = (Button)findViewById(R.id.play);
		
		mStartBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (mIsStart == false) {
					String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
					Path = sd + "/recaudio.3gp";
					if (mRecorder == null) {
						mRecorder = new MediaRecorder();
					} else {
						mRecorder.reset();
					}
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setOutputFile(Path);
					try {
						mRecorder.prepare();
					} catch (IllegalStateException e) {
						Toast.makeText(RecAudio.this, "IllegalStateException", 1).show();
					} catch (IOException e) {
						Toast.makeText(RecAudio.this, "IOException", 1).show();
					}
					mRecorder.start();
					mIsStart = true;
					mStartBtn.setText("Stop");
				} else {
					mRecorder.stop();
					mRecorder.release();
					mRecorder = null;
					mIsStart = false;
					mStartBtn.setText("Start");
				}
			}
		});

		mPlayBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (Path.length() == 0 || mIsStart) {
					Toast.makeText(RecAudio.this, "녹음을 먼저 하십시오.", 0).show();
					return;
				}
				MediaPlayer player = new MediaPlayer();
				try {
					player.setDataSource(Path);
					player.prepare();
					player.start();
				} catch (Exception e) {
					Toast.makeText(RecAudio.this, "error : " + e.getMessage(), 0).show();
				}
			}
		});
	}

	public void onDestroy() {
		super.onDestroy();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}
	}
}

