package andexam.ver4_1.c28_network;

import andexam.ver4_1.*;

import java.io.*;
import java.net.*;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DownImage extends Activity {
	ImageView mImage;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downimage);
		
		mImage = (ImageView)findViewById(R.id.result);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btndown:
			(new DownThread("http://www.soenlab.com/data/child2.jpg")).start();
			break;
		}
	}
	
	class DownThread extends Thread {
		String mAddr;

		DownThread(String addr) {
			mAddr = addr;
		}

		public void run() {
			try {
				InputStream is = new URL(mAddr).openStream();
				Bitmap bit = BitmapFactory.decodeStream(is);
				is.close();
				Message message = mAfterDown.obtainMessage();
				message.obj = bit;
				mAfterDown.sendMessage(message);
			} catch (Exception e) {;}
		}
	}

	Handler mAfterDown = new Handler() {
		public void handleMessage(Message msg) {
			Bitmap bit = (Bitmap)msg.obj;
			if (bit == null) {
				Toast.makeText(DownImage.this, "bitmap is null", 0).show();
			} else {
				mImage.setImageBitmap(bit);
			}
		}
	};
}

