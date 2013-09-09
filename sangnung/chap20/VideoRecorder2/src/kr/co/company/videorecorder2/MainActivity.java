package kr.co.company.videorecorder2;

import java.io.IOException;
import android.app.Activity;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

	Button myButton;
	MediaRecorder mediaRecorder;
	SurfaceHolder surfaceHolder;
	boolean is_recording;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		is_recording = false;

		mediaRecorder = new MediaRecorder();
		initMediaRecorder();

		setContentView(R.layout.main);

		SurfaceView myVideoView = (SurfaceView) findViewById(R.id.videoview);
		surfaceHolder = myVideoView.getHolder();
		surfaceHolder.addCallback(this);

		myButton = (Button) findViewById(R.id.mybutton);
		myButton.setOnClickListener(myButtonOnClickListener);
	}

	private Button.OnClickListener myButtonOnClickListener = new Button.OnClickListener() {

		public void onClick(View arg0) {
			if (is_recording) {
				mediaRecorder.stop();
				mediaRecorder.release();
				finish();
			} else {
				mediaRecorder.start();
				is_recording = true;
				myButton.setText("녹화중지");
			}
		}
	};

	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	public void surfaceCreated(SurfaceHolder arg0) {
		prepareMediaRecorder();
	}

	public void surfaceDestroyed(SurfaceHolder arg0) {
	}

	private void initMediaRecorder() {
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		mediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		CamcorderProfile camcorderProfile_HQ = CamcorderProfile
				.get(CamcorderProfile.QUALITY_LOW);
		mediaRecorder.setProfile(camcorderProfile_HQ);
		mediaRecorder.setOutputFile("/sdcard/video.mp4");
		mediaRecorder.setMaxDuration(60000); // 최대 시간을 60초로 한정한다. 
		mediaRecorder.setMaxFileSize(5000000); // 최대 파일  크기를 5MB로 한정한다. 
	}

	private void prepareMediaRecorder() {
		mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());
		try {
			mediaRecorder.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
