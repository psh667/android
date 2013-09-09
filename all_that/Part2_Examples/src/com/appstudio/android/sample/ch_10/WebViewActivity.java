package com.appstudio.android.sample.ch_10;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.webviewmain);

	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.loadUrl("http://www.google.com");
	    mWebView.setWebViewClient(new HelloWebViewClient());
	}
	
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.d("KEY_DOWN",keyCode + ":" + KeyEvent.KEYCODE_BACK);
	    if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
	        mWebView.goBack();
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}
