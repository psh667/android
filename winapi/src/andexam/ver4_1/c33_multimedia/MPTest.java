package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import java.io.*;

import android.app.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MPTest extends Activity {
	MediaPlayer mPlayer;
	String mSdPath;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mptest);

		mPlayer = MediaPlayer.create(this, R.raw.dingdong);
		mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public void mOnClick(View v) {
		MediaPlayer player;
		switch (v.getId()) {
		// 리소스 재생
		case R.id.btn1:
			player = MediaPlayer.create(this, R.raw.dingdong);
			player.start();
			break;
		// 파일 재생
		case R.id.btn2:
			player = new MediaPlayer();
			try {
				player.setDataSource(mSdPath + "/eagle5.mp3");
				player.prepare();
				player.start();
			} catch (Exception e) {
				Toast.makeText(this, "error : " + e.getMessage(), 0).show();
			}
			break;
		// 스트림 재생
		case R.id.btn3:
			player = new MediaPlayer();
			try {
				Uri uri = Uri.parse("http://www.soenlab.com/data/saemaul1.mp3");
				player.setDataSource(this, uri);
				player.prepare();
				player.start();
			} catch (Exception e) {
				Toast.makeText(this, "error : " + e.getMessage(), 0).show();
			}
			break;
		// 미리 준비된 객체로 재생
		case R.id.btn4:
			mPlayer.seekTo(0);
			mPlayer.start();
			break;
		// 준비하지 않은 상태로 재생
		case R.id.btn5:
			player = new MediaPlayer();
			try {
				player.setDataSource(mSdPath + "/eagle5.mp3");
				player.start();
			} catch (Exception e) {
				Toast.makeText(this, "error : " + e.getMessage(), 0).show();
			}
			break;
		// 다른 파일 열기
		case R.id.btn6:
			player = MediaPlayer.create(this, R.raw.dingdong);
			//player.reset();
			try {
				player.setDataSource(mSdPath + "/eagle5.mp3");
				player.prepare();
				player.start();
			} catch (IllegalArgumentException e) {
				Toast.makeText(this, "IllegalArgumentException", 0).show();
			} catch (IllegalStateException e) {
				Toast.makeText(this, "IllegalStateException", 0).show();
			} catch (IOException e) {
				Toast.makeText(this, "IOException", 0).show();
			}
			break;
		}
	}

	public void onDestroy() {
		super.onDestroy();
		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
}

