package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class PlayVideo extends Activity implements SurfaceHolder.Callback {
	private SurfaceView mPreview;
	private SurfaceHolder mHolder;
	MediaPlayer mPlayer;
	Button mPlayBtn;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playvideo);

		mPreview = (SurfaceView) findViewById(R.id.surface);
		mHolder = mPreview.getHolder();
		mHolder.addCallback(this);

		mPlayBtn = (Button)findViewById(R.id.play);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			if (mPlayer.isPlaying() == false) {
				mPlayer.start();
				mPlayBtn.setText("Pause");
			} else {
				mPlayer.pause();
				mPlayBtn.setText("Play");
			}
			break;
		case R.id.stop:
			mPlayer.stop();
			try {
				mPlayer.prepare();
			} catch (Exception e) {;}
			break;
		}
	}
	
	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (mPlayer == null) {
			mPlayer = new MediaPlayer();
		} else {
			mPlayer.reset();
		}
		try {
			String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
			mPlayer.setDataSource(sd + "/testvideo.mp4");
			mPlayer.setDisplay(holder);
			mPlayer.prepare();
			mPlayer.setOnCompletionListener(mComplete);
			mPlayer.setOnVideoSizeChangedListener(mSizeChange);
		} catch (Exception e) {
			Toast.makeText(PlayVideo.this, "error : " + e.getMessage(), 
					Toast.LENGTH_LONG).show();
		}
	}

	MediaPlayer.OnCompletionListener mComplete = 
		new MediaPlayer.OnCompletionListener() {
		public void onCompletion(MediaPlayer arg0) {
			mPlayBtn.setText("Play");
		}
	};

	MediaPlayer.OnVideoSizeChangedListener mSizeChange = 
		new MediaPlayer.OnVideoSizeChangedListener() {
		public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
		}
	}
}

