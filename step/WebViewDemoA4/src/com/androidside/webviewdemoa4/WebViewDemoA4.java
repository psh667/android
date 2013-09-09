package com.androidside.webviewdemoa4;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewDemoA4 extends Activity {
    private WebView webView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main);
      
      webView = (WebView) this.findViewById(R.id.webview);
      
      webView.getSettings().setJavaScriptEnabled(true);

      webView.loadUrl("http://m.androidside.com/");
    }

    @Override 
    public void onConfigurationChanged(Configuration newConfig){         
        super.onConfigurationChanged(newConfig);   
    }
}