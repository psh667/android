package kr.co.company.chap14lab;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Chap14LabActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button btnDownload = (Button) findViewById(R.id.btn_download);

		btnDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText etUrl = (EditText) findViewById(R.id.et_url);
				DownloadTask downloadTask = new DownloadTask();
				downloadTask.execute(etUrl.getText().toString());
			}
		});

	}

	private Bitmap downloadUrl(String strUrl) throws IOException {
		Bitmap bitmap = null;
		InputStream iStream = null;
		try {
			URL url = new URL(strUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.connect();
			iStream = urlConnection.getInputStream();
			bitmap = BitmapFactory.decodeStream(iStream);
		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
		}
		return bitmap;
	}

	private class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
		Bitmap bitmap = null;

		@Override
		protected Bitmap doInBackground(String... url) {
			try {
				bitmap = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			ImageView iView = (ImageView) findViewById(R.id.iv_image);
			iView.setImageBitmap(result);
			Toast.makeText(getBaseContext(), "Image downloaded successfully",
					Toast.LENGTH_SHORT).show();
		}
	}
}
