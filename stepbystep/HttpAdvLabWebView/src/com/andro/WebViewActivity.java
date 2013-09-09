package com.andro;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // main.xml의 레이아웃에 있는 WebView 인식
        WebView mWebView = (WebView) findViewById(R.id.webview); 
        // WebView가 자바스크립트를 실행하도록 설정함
        mWebView.getSettings().setJavaScriptEnabled(true);  
        // 웹뷰에 지정한 URL의 웹문서 출력 
        mWebView.loadUrl("http://www.google.com");        
    }
}