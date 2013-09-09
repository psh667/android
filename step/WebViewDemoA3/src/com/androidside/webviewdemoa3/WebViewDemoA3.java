package com.androidside.webviewdemoa3;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewDemoA3 extends Activity {
    private WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);      
      setContentView(R.layout.main);
      
      webView = (WebView) this.findViewById(R.id.webview);
      webView.getSettings().setJavaScriptEnabled(true);
      
      webView.setWebChromeClient(new WebChromeClient() {
        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                JsResult result) {
            
            Toast.makeText(WebViewDemoA3.this, message, Toast.LENGTH_SHORT).show();
            result.confirm();
            
            return true;
        }
      });
      
      webView.loadUrl("file:///android_asset/jsalert.html");
    }
  }