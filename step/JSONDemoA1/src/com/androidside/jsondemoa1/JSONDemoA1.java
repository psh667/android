package com.androidside.jsondemoa1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class JSONDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TextView text = (TextView) findViewById(R.id.text);
        text.setText(getJsonText());
    }
    

    //JSON 데이터를 읽는다.
    public String getJsonText() {
        StringBuffer sb = new StringBuffer();
        try {
            String line = getStringFromUrl("http://www.androidside.com/book/data.json");
            
            //원격에서 읽어온 데이터로 JSON 객체 생성
            JSONObject object = new JSONObject(line);            
            
            sb.append("이름:").append(object.getString("이름")).append("\n");
            sb.append("나이:").append(object.getInt("나이")).append("\n");
            sb.append("성별:").append(object.getString("성별")).append("\n");
            sb.append("결혼:").append(object.getBoolean("결혼")).append("\n");
            
            //취미는 배열로 구성되어 있으므로 JSON 배열 생성
            sb.append("취미:");
            JSONArray hobbyArray = new JSONArray(object.getString("취미"));
            
            for (int i = 0; i < hobbyArray.length(); i++) {
                sb.append(hobbyArray.getString(i)).append(",");
            }
            sb.append("\n");
           
            sb.append("주소:").append(object.getString("주소")).append("\n");
            
            //가족 데이터는 내부 JSON이므로 JSON 객체 생성
            sb.append("가족:");
            JSONObject familyObject = new JSONObject(object.getString("가족"));
            sb.append(familyObject.getString("아버지")).append(",");
            sb.append(familyObject.getString("어머니"));
            
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    // 주어진 URL의 페이지를 문자열로 얻는다.
    public String getStringFromUrl(String url)
            throws UnsupportedEncodingException {
        // 입 력스트림을 "euc-kr"를 사용해서 읽은 후, 이를 사용해서 라인 단위로 데이터를 읽을 수 있는
        // BufferedReader를 생성한다.
        BufferedReader br = new BufferedReader(new InputStreamReader(
                getInputStreamFromUrl(url), "euc-kr"));

        // 읽은 데이터를 저장할 StringBuffer를 생성한다.
        StringBuffer sb = new StringBuffer();

        try {
            // 라인 단위로 읽은 데이터를 임시 저장할 문자열 변수
            String line = null;

            // 라인 단위로 데이터를 읽어서 StringBuffer에 저장한다.
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    // 주어진 URL에 대한 입력 스트림(InputStream)을 얻는다.
    public static InputStream getInputStreamFromUrl(String url) {
        InputStream contentStream = null;

        try {
            // HttpClient를 사용해서 주어진 URL에 대한 입력 스트림을 얻는다.
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            contentStream = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contentStream;
    }
}