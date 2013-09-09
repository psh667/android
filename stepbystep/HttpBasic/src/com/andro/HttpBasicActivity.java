package com.andro;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class HttpBasicActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 웹문서 소스 출력 영역 인식
		EditText et_webpage_src = (EditText)findViewById(R.id.webpage_src);
		
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedInputStream buf = null;
		
		try {    
			///// URL 지정과 접속 
			// 웹서버 URL 지정 
			url = new URL("http://www.google.co.kr/");  
			// URL 접속
			urlConnection = (HttpURLConnection) url.openConnection(); 
			
			///// 웹문서 소스를 버퍼에 저장  
            // 데이터 다운로드  			
			buf  = new BufferedInputStream(urlConnection.getInputStream());    
			// 데이터를 버퍼에 기록 
        	BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "euc-kr"));
        	
        	String line  = null;
        	String page  = "";
        	
        	// 버퍼의 웹문서 소스를 줄 단위로 읽어(line), page에 저장함 
        	while ((line = bufreader.readLine()) != null) {
        		page += line;
        	}
        	
        	// page 내용을 화면에  출력
        	et_webpage_src.setText(page);
        	
        } catch (Exception e){
        	// 예외사항 발생 시 출력 
        	et_webpage_src.setText(e.getMessage());
		} finally {   
			// URL 연결 해제
			urlConnection.disconnect();  
		}		
	}
}