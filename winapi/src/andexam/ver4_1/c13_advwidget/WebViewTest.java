package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;

public class WebViewTest extends Activity {
	WebView mWeb;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webviewtest);

		mWeb = (WebView)findViewById(R.id.web);
		mWeb.setWebViewClient(new MyWebClient());
		WebSettings set = mWeb.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		mWeb.loadUrl("http://www.google.com");
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btngo:
			String url;
			EditText addr = (EditText)findViewById(R.id.address);
			url = addr.getText().toString();
			mWeb.loadUrl(url);
			break;
		case R.id.btnback:
			if (mWeb.canGoBack()) {
				mWeb.goBack();
			}
			break;
		case R.id.btnforward:
			if (mWeb.canGoForward()) {
				mWeb.goForward();
			}
			break;
		case R.id.btnlocal:
			mWeb.loadUrl("file:///android_asset/test.html");
			break;
		}
	}
	
	class MyWebClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
}