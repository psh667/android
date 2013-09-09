package com.andro;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class JSON2Activity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // 버튼 클릭 대기: 시작 
        Button btn = (Button)findViewById(R.id.button_call);
        btn.setOnClickListener(this);
        // 버튼 클릭 대기: 끝 
    }

	// @Override
	public void onClick(View v) {
		
		// 고객정보 출력 영역 인식
		EditText et_webpage_src = (EditText)findViewById(R.id.webpage_src);

		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedInputStream buf = null;
		
		try {    
			///// URL 지정과 접속 
			// 웹서버 URL 지정 
			url = new URL("http://이 부분은 서버 도메인명(또는 IP)을 적어 주세요/json/customers.html");  
			// URL 접속
			urlConnection = (HttpURLConnection) url.openConnection(); 
			
			///// 웹문서 소스를 버퍼에 저장  
            // 데이터 다운로드  			
			buf  = new BufferedInputStream(urlConnection.getInputStream());    
			// 데이터를 버퍼에 기록 
        	BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
        	
        	String line  = null;
        	String page  = "";
        	
        	// 버퍼의 웹문서 소스를 줄 단위로 읽어(line), page에 저장함 
        	while ((line = bufreader.readLine()) != null) {
        		page += line;
        	}
        	
	        // 읽어들인 JSON 포맷의 데이어틀 JSON 객체로 변환
    		JSONObject json = new JSONObject(page);
    		// customers에 해당하는 배열을 할당  
    		JSONArray  jArr = json.getJSONArray("customers");
    		
    		// 배열의 크기만큼 반복하면서, name과 address의 값을 추출함 
    		for (int i=0; i<jArr.length(); i++) {
    			
    			// i번째 배열 할당  
        		json = jArr.getJSONObject(i);
        		
        		// name과 address의 값을 추출함
        		String name    = json.getString("name");
        		String address = json.getString("address");
        		
        		// name과 address의 값을 출력함
    			et_webpage_src.append(name + "\n");
    			et_webpage_src.append(address + "\n");
    		}
    		
    		
        } catch (Exception e){
        	// 에러메시지 출력 
        	et_webpage_src.setText(e.getMessage());
		} finally {   
			// URL 연결 해제
			urlConnection.disconnect();  
		}			
    }
}