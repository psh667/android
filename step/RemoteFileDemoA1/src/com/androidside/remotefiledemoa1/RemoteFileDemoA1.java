package com.androidside.remotefiledemoa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RemoteFileDemoA1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //주어진 URL의 데이터를 저장할 EditText를 얻는다.
        final EditText text = (EditText) findViewById(R.id.viewarea);        
        
        //사용자가 버튼을 눌렀을 때, 데이터를 읽기 시작할 리스너를 등록한다.
        Button button = (Button) findViewById(R.id.geturl);
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                try {
                    //주어진 URL(http://www.androidside.com)의 데이터를 얻는다.
                    String string = getStringFromUrl("http://www.androidside.com");
                    
                    text.setText(string);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        });
    }
    
    //주어진 URL의 페이지를 문자열로 얻는다.
    public String getStringFromUrl(String url) throws UnsupportedEncodingException {
        //입 력스트림을 "euc-kr"를 사용해서 읽은 후, 이를 사용해서 라인 단위로 데이터를 읽을 수 있는 BufferedReader를 생성한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "euc-kr"));
        
        //읽은 데이터를 저장할 StringBuffer를 생성한다.
        StringBuffer sb = new StringBuffer();        
        
        try {
            //라인 단위로 읽은 데이터를 임시 저장할 문자열 변수 
            String line = null;
            
            //라인 단위로 데이터를 읽어서 StringBuffer에 저장한다.
            while ( (line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return sb.toString();
    }
    
    //주어진 URL에 대한 입력 스트림(InputStream)을 얻는다.
    public static InputStream getInputStreamFromUrl(String url) {   
        InputStream contentStream = null;   
        
        try {
          //HttpClient를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.
          HttpClient httpclient = new DefaultHttpClient();
          HttpResponse response = httpclient.execute(new HttpGet(url));
          contentStream = response.getEntity().getContent();          
        } catch (Exception e) {   
            e.printStackTrace();
        }
        
        return contentStream;   
    }
}
