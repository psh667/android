package kr.co.company.chap18lab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Chap18LabActivity extends Activity {
	protected EditText mUrl, mSearch;
	protected TextView mResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mUrl = (EditText) findViewById(R.id.url_to_search);
		mSearch = (EditText) findViewById(R.id.search_string);
		mResult = (TextView) findViewById(R.id.result);
	}

	public void onClick(View clickedButton) {
		String url = mUrl.getText().toString();
		DownloadTask downloadTask = new DownloadTask();
		downloadTask.execute(url);
	}

	private class DownloadTask extends AsyncTask<String, Integer, String> {
		StringBuilder matches = new StringBuilder("");
		String search = null;
		int lineNum = 0;
		int numMatches = 0;

		@Override
		protected String doInBackground(String... s) {
			String search = mSearch.getText().toString();
			HttpURLConnection urlConnection = null;
			try {
				URL url = new URL(s[0]);
				urlConnection = (HttpURLConnection) url.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					lineNum++;
					if (line.contains(search)) {
						numMatches++;
						matches.append(String.format("LINE %s: %s%n", lineNum,
								line));
					}
				}
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return matches.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			if (numMatches > 0) {
				mResult.setText(result);
			}
		}
	}
}