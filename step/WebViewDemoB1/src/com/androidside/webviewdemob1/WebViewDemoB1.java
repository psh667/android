package com.androidside.webviewdemob1;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class WebViewDemoB1 extends Activity {    
    String HOME_URL ="http://m.androidside.com";
    
    WebView webView;
    ProgressBar progressBar;
        
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.main);        
               
        webView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);        
                
        webView.setWebViewClient(new SmartWebViewClient());
        webView.setWebChromeClient(new SmartWebChromeClient());
        
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);             
        webView.getSettings().setDefaultFontSize(12);
        
        webView.loadUrl(HOME_URL);
        
        setListener();
    }
    
    //화면 모드가 변경되었을 때 기존 설정을 사용하여 액티비티를 다시 생성하지 않는다.
    @Override
    public void onConfigurationChanged(Configuration newConfig){      
        super.onConfigurationChanged(newConfig);
    }
    
    //하단에 배치되는 버튼에 대한 리스너를 설정한다.
    public void setListener() {
        ImageButton leftButton = (ImageButton) findViewById(R.id.left);
        leftButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                webView.goBack();                
            }
        });
        
        ImageButton rightButton = (ImageButton) findViewById(R.id.right);
        rightButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                webView.goForward();
            }
        });
        
        ImageButton refreshButton = (ImageButton) findViewById(R.id.refresh);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });
        
        ImageButton homeButton = (ImageButton) findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                webView.loadUrl(HOME_URL);                
            }
        });
    }

    
    private class SmartWebViewClient extends WebViewClient {
        //URL을 원하는데로 처리하고 싶을 때 정의하는 메소드
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        
        //웹 페이지 로딩이 끝났을 때 호출되는 메소드
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
        
        //웹 페이지가 로딩될 때 호출되는 메소드
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            
            progressBar.setVisibility(View.VISIBLE);
        }        
    }
        
    private class SmartWebChromeClient extends WebChromeClient {     
        //프로그래스바의 진행상태를 표시하는 메소드
        public void onProgressChanged(WebView view, int progress) {
            progressBar.setProgress(progress); 
        }
    }    
     
    //백 버튼이 클릭되었을 때 호출되는 메소드
    public void onBackPressed() {
        confirmExit();
    }
    
    //어플리케이션 종료를 확인하는 메소드
    public void confirmExit() {        
        new AlertDialog.Builder(this)        
        .setTitle("안드로이드사이드")
        .setMessage("종료하시겠습니까?")
        .setPositiveButton("예", new DialogInterface.OnClickListener() {            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .setNegativeButton("아니오", null)
        .show();
    }    
}
