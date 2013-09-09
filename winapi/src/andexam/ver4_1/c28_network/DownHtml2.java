package andexam.ver4_1.c28_network;

import andexam.ver4_1.*;
import java.io.*;
import java.net.*;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DownHtml2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downhtml);
		
		StrictMode.ThreadPolicy pol = new StrictMode.ThreadPolicy.Builder()
			.permitNetwork().build();
		StrictMode.setThreadPolicy(pol);

		Button btn = (Button)findViewById(R.id.down);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				String html;
				html = DownloadHtml("http://www.google.com"); 
				TextView result = (TextView)findViewById(R.id.result);
				result.setText(html);
			}
		});
	}

	String DownloadHtml(String addr) {
		StringBuilder html = new StringBuilder(); 
		try {
			URL url = new URL(addr);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			if (conn != null) {
				conn.setConnectTimeout(10000);
				conn.setUseCaches(false);
				if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					for (;;) {
						String line = br.readLine();
						if (line == null) break;
						html.append(line + '\n'); 
					}
					br.close();
				}
				conn.disconnect();
			}
		} catch (NetworkOnMainThreadException e) {
			return "Error : 메인 스레드 네트워크 작업 에러 - " + e.getMessage();
		} catch (Exception e) {
			return "Error : " + e.getMessage();
		}
		return html.toString();
	}
}

