package com.androidside.webviewdemoa2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewDemoA2 extends Activity {
    private WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);      
      requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);      
      setContentView(R.layout.main);
      
      webView = (WebView) this.findViewById(R.id.webview);
      
      webView.setWebViewClient(new WebViewClient() {
         @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            
            setProgressBarIndeterminateVisibility(true);
        }
         
         @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            
            setProgressBarIndeterminateVisibility(false);
        }
      });

      webView.loadUrl("http://www.androidside.com");
    }
  }