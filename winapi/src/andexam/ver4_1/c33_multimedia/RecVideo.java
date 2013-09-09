package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import java.io.*;

import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class RecVideo extends Activity  implements SurfaceHolder.Callback {
	MediaRecorder mRecorder = null;
	Button mStartBtn, mPlayBtn;
	boolean mIsStart = false;
	String Path = "";
	private SurfaceView mPreview;
	private SurfaceHolder mHolder;
	VideoView mVideo;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recvideo);

		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);
		mVideo = (VideoView)findViewById(R.id.videoview);

		mStartBtn = (Button)findViewById(R.id.start);
		mPlayBtn = (Button)findViewById(R.id.play);
		
		mStartBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mPreview.setVisibility(View.VISIBLE);
				mVideo.setVisibility(View.INVISIBLE);
				if (mIsStart == false) {
					String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
			        Path = sd + "/recvideo.mp4";
					if (mRecorder == null) {
						mRecorder = new MediaRecorder();
					} else {
						mRecorder.reset();
					}
					mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setOutputFile(Path);
					mRecorder.setPreviewDisplay(mHolder.getSurface());
					try {
						mRecorder.prepare();
						mRecorder.start();
					} catch (IllegalStateException e) {
						Toast.makeText(RecVideo.this, "IllegalStateException : " + 
								e.toString(), 1).show();
						return;
					} catch (IOException e) {
						Toast.makeText(RecVideo.this, "IOException : " + 
								e.toString(), 1).show();
						return;
					} catch (Exception e) {
						Toast.makeText(RecVideo.this, "Exception : " + 
								e.toString(), 1).show();
						return;
					}
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
					Toast.makeText(RecVideo.this, "녹화를 먼저 하십시오.", 0).show();
					return;
				}
				mPreview.setVisibility(View.INVISIBLE);
				mVideo.setVisibility(View.VISIBLE);
				mVideo.setVideoPath(Path);
				mVideo.start();
			}
		});
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
	}
	
	public void onDestroy() {
		super.onDestroy();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}
	}
}

