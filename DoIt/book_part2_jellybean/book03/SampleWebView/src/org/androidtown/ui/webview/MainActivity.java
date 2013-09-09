package org.androidtown.ui.webview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

/**
 * 웹뷰를 화면안에 넣고 앱과 웹 사이에 상호 호출하는 기능을 알아볼 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 로그를 위한 태그
	 */
	private static final String TAG = "MainActivity";

	/**
	 * 웹뷰 객체
	 */
	private WebView webView;
	
	/**
	 * 웹사이트 로딩을 위한 버튼
	 */
	private Button loadBtn;

	/**
	 * 핸들러 객체
	 */
	private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 웹뷰 객체 참조
        webView = (WebView) findViewById(R.id.webview);

        // 웹뷰 설정 정보
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);

        webView.setWebChromeClient(new WebBrowserClient());
        webView.addJavascriptInterface(new JavaScriptMethods(), "sample");
        
        // assets 폴더에 있는 메인 페이지 로딩
        webView.loadUrl("file:///android_asset/sample.html");

        final EditText urlInput = (EditText) findViewById(R.id.urlInput);
        
        // 버튼 이벤트 처리
        loadBtn = (Button) findViewById(R.id.loadBtn);
        loadBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// 입력한 URL의 페이지 로딩
        		webView.loadUrl(urlInput.getText().toString());
        	}
        });

    }

    /**
     * 자바스크립트 함수를 호출하기 위한 클래스 정의
     */
    final class JavaScriptMethods {

    	JavaScriptMethods() {
        
    	}

        public void clickOnFace() {
            mHandler.post(new Runnable() {
                public void run() {
                	// 버튼의 텍스트 변경
                	loadBtn.setText("클릭후열기");
                	// 자바스크립트 함수 호출
                	webView.loadUrl("javascript:changeFace()");
                }
            });

        }
    }

    final class WebBrowserClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d(TAG, message);
            result.confirm();

            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
