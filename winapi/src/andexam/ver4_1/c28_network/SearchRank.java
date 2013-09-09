package andexam.ver4_1.c28_network;

import java.io.*;
import java.net.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SearchRank extends Activity {
	ProgressDialog mProgress;
	boolean mRaw;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchrank);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.getrankraw:
			mRaw = true;
			break;
		case R.id.getrank:
			mRaw = false;
			break;
		}
		mProgress = ProgressDialog.show(this, "Wait", "Downloading...");
		DownThread thread = new DownThread("http://openapi.naver.com/search?" + 
				"key=8f8c1668efc16f634110fb9fd51f15c8&query=nexearch&target=rank");
		thread.start();
	}	
	
	class DownThread extends Thread {
		String mAddr;

		DownThread(String addr) {
			mAddr = addr;
		}

		public void run() {
			String result = DownloadHtml(mAddr);
			Message message = mAfterDown.obtainMessage();
			message.obj = result;
			mAfterDown.sendMessage(message);
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

	Handler mAfterDown = new Handler() {
		public void handleMessage(Message msg) {
			mProgress.dismiss();
			TextView result = (TextView)findViewById(R.id.result);
			String html = (String)msg.obj;
			
			if (mRaw) {
				result.setText(html);
			} else {
				String Result = "";
				try {
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					InputStream istream = new ByteArrayInputStream(html.getBytes("utf-8"));
					Document doc = builder.parse(istream);

					Element root = doc.getDocumentElement();
					Node item = root.getElementsByTagName("item").item(0);
					Node rank = item.getFirstChild();
					for (int i = 1; i <= 10;i++) {
						Node k = rank.getFirstChild();
						String sK = k.getFirstChild().getNodeValue();
						Node s = k.getNextSibling();
						String sS = s.getFirstChild().getNodeValue();
						Node v = s.getNextSibling();
						String sV = v.getFirstChild().getNodeValue();
						rank = rank.getNextSibling();
						Result += "" + i + "위 : " + sK + ", " + sS + sV + "\n";
					}
					result.setText(Result);
				}
				catch (Exception e) { ; }
			}
		}
	};
}

