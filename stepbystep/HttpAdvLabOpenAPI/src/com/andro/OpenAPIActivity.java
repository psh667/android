package com.andro;

import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OpenAPIActivity extends Activity implements OnClickListener {
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
		
		// 웹문서 출력 영역 인식
		EditText et_webpage_src = (EditText)findViewById(R.id.webpage_src);
		
	    try {
	    	// 노선번호목록조회 URL
			String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";
			// 서울교통정보센터 OPen API 인증키 
			String serviceKey = "이 부분은 Open API 인증키로 대체하세요"; 
			// 서비스 키의 URL 인코딩
			serviceKey = URLEncoder.encode(serviceKey);
			// 검색할 버스 노선번호 
			String strSrch = "5500";
			// 노선번호목록조회 요청 URL
			String strURL = serviceUrl+"?ServiceKey="+serviceKey+"&strSrch="+strSrch;
	    	// 웹 상의 리소스 지정
	        URL url = new URL(strURL);
			
	        // XML Pull Parser를 만들기 위한 XmlPullParserFactory의 인스턴스 생성 
	        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	        // XML Pull Parser의 인스턴스 생성 
	        XmlPullParser parser = factory.newPullParser();
	        // 웹 리소스에 대한 Input Stream 설정 
	        parser.setInput(url.openStream(), "utf-8");
	        
            // 현재 이벤트 타입(START_TAG, END_TAG, TEXT, etc)을 반환함	        
	        int eventType = parser.getEventType();
	        // 태그 이름(<busRouteNm>, <stStationNm>, <edStationNm>)의 검색여부 초기치 
	        boolean bSet = false;
	        
	        // 이벤트 타입이 도큐먼트의 마지막이 아니면 반복 
	        while (eventType != XmlPullParser.END_DOCUMENT) {
	        	
	            switch (eventType) {
	            
	                // 이벤트 타입이 도큐먼트의 시작인 경우
	                case XmlPullParser.START_DOCUMENT:
	                    break;
	                    
	                // 이벤트 타입이 START_TAG인 경우(예: <busRouteNm>)
	                case XmlPullParser.START_TAG:
	                	// 태그 이름을 추출함
	                    String tag = parser.getName();
	                    // 태그 이름이 <busRouteNm>, <stStationNm>, <edStationNm>인 경우  
	                    if (tag.equals("busRouteNm") || tag.equals("stStationNm") || tag.equals("edStationNm")) {
	                        bSet = true;
	                    }
	                    break;
	                    
	                // 이벤트 타입이 END_TAG인 경우(예: </busRouteNm>)    
	                case XmlPullParser.END_TAG:
	                    break;
	                    
	                // 이벤트 타입이 태그 사이의 데이터인 경우는 데이터 추출 (예: <busRouteNm>데이터</busRouteNm>)       
	                case XmlPullParser.TEXT:
	                	// 태그(<busRouteNm>, <stStationNm>, <edStationNm>) 사이의 데이터 추출
	                    if (bSet) {
	                    	// 데이터 추출 
	                        String content = parser.getText();
	                        // 데이터 출력 
	                        et_webpage_src.append(content + "\n");
	                        // 검색여부 초기치 재설정
	                        bSet = false;
	                    }
	                    break;
	                    
		            // 이벤트 타입이 도큐먼트의 끝인 경우
	                case XmlPullParser.END_DOCUMENT:
	                    break;
	                    
	            } // switch
	            
	            // 다음 이벤트 타입을 할당 
	            eventType = parser.next();
	            
	        } // while
	    } catch (Exception e) {
	    	// 메시지 출력
	        Toast.makeText(v.getContext(), e.getMessage(), 0).show();
	    } // try 
    }
}