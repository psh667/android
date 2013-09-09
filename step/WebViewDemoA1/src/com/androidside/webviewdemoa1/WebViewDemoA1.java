package com.androidside.webviewdemoa1;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewDemoA1 extends Activity {
    private WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
      webView = (WebView) this.findViewById(R.id.webview);
      
      webView.getSettings().setJavaScriptEnabled(true);
      webView.getSettings().setBuiltInZoomControls(true);
      webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
      webView.getSettings().setLightTouchEnabled(true);
      webView.getSettings().setSavePassword(true);
      webView.getSettings().setSaveFormData(true);

      webView.loadUrl("http://m.androidside.com/");
    }
}