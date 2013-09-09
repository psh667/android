package andexam.ver4_1.c28_network;

import andexam.ver4_1.*;
import java.io.*;
import java.net.*;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DownImage2 extends Activity {
	ImageView mImage;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downimage);
		
		mImage = (ImageView)findViewById(R.id.result);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btndown:
			String imageurl = "http://www.soenlab.com/data/child3.jpg";
			int idx = imageurl.lastIndexOf('/');
			String localimage = imageurl.substring(idx + 1);
			String path = Environment.getDataDirectory().getAbsolutePath();
			path += "/data/andexam.ver4_1/files/" + localimage;

			if (new File(path).exists()) {
				Toast.makeText(this, "bitmap is exist", 0).show();
				mImage.setImageBitmap(BitmapFactory.decodeFile(path));
			} else {
				Toast.makeText(this, "bitmap is not exist", 0).show();
				(new DownThread(imageurl, localimage)).start();
			}

			break;
		}
	}
	
	class DownThread extends Thread {
		String mAddr;
		String mFile;

		DownThread(String addr, String filename) {
			mAddr = addr;
			mFile = filename;
		}

		public void run() {
			URL imageurl;
			int Read;
			try {
				imageurl = new URL(mAddr);
				HttpURLConnection conn= (HttpURLConnection)imageurl.openConnection();
				int len = conn.getContentLength();
				byte[] raster = new byte[len];
				InputStream is = conn.getInputStream();
				FileOutputStream fos = openFileOutput(mFile, 0);

				for (;;) {
					Read = is.read(raster);
					if (Read <= 0) {
						break;
					}
					fos.write(raster,0, Read);
				}

				is.close();
				fos.close();
				conn.disconnect();
			} catch (Exception e) {
				mFile = null;
			}
			Message message = mAfterDown.obtainMessage();
			message.obj = mFile;
			mAfterDown.sendMessage(message);
		}
	}

	Handler mAfterDown = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.obj != null) {
				String path = Environment.getDataDirectory().getAbsolutePath();
				path += "/data/andexam.ver4_1/files/" + (String)msg.obj;
				mImage.setImageBitmap(BitmapFactory.decodeFile(path));
			} else {
				Toast.makeText(DownImage2.this, "File not found", 0).show();
			}
		}
	};
}

