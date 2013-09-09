package com.msi.manning.chapter10.SimpleVideo;


import android.app.Activity;
import android.os.Bundle;
import android.widget.VideoView;
import android.graphics.PixelFormat;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;

public class SimpleVideo extends Activity {
	private VideoView myVideo;
	private MediaController mc;
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.main);
		Button bPlayVideo=(Button)findViewById(R.id.playvideo);
		bPlayVideo.setOnClickListener(new View.OnClickListener() {
		public void onClick(View view) {
			mc.show();	}
				});
			myVideo=(VideoView)findViewById(R.id.video);
			myVideo.setVideoPath("/tmp/test.mp4");
			mc=new MediaController(this);
			mc.setMediaPlayer(myVideo);
			myVideo.setMediaController(mc);
			myVideo.requestFocus();
		}
}
