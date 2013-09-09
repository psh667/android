package andexam.ver4_1.c28_network;

import java.io.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ApacheDownHtml extends Activity {
	ProgressDialog mProgress;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.downhtml);

		Button btn = (Button)findViewById(R.id.down);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				mProgress = ProgressDialog.show(ApacheDownHtml.this, 
						"Wait", "Downloading...");
				DownThread thread = new DownThread("http://www.google.com");
				thread.start();
			}
		});
	}

	class DownThread extends Thread {
		String mAddr;

		DownThread(String addr) {
			mAddr = addr;
		}

		public void run() {
			HttpGet get = new HttpGet(mAddr);
			DefaultHttpClient client = new DefaultHttpClient();
			try {
				client.execute(get,mResHandler);
			} 
			catch (Exception e) {;}
		}
	}

	ResponseHandler<String> mResHandler = new ResponseHandler<String>() {
		public String handleResponse(HttpResponse response) {
			StringBuilder html = new StringBuilder(); 
			try {
				BufferedReader br = new BufferedReader(new 
						InputStreamReader(response.getEntity().getContent()));
				for (;;) {
					String line = br.readLine();
					if (line == null) break;
					html.append(line + '\n'); 
				}
				br.close();

				Message message = mAfterDown.obtainMessage();
				message.obj = html.toString();
				mAfterDown.sendMessage(message);
			} catch (Exception e) {;}
			return html.toString();
		}
	};

	Handler mAfterDown = new Handler() {
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			TextView result = (TextView)findViewById(R.id.result);
			result.setText((String)msg.obj);
		}
	};
}

