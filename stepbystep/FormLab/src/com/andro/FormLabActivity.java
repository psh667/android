package com.andro;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FormLabActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ///// 스피너 출력: 시작 
        // (1) strings.xml에 정의한 string 배열(interest_array)를 ArrayAdapter로  바인딩하고 스피너의 모양을 지정함 
        ArrayAdapter<CharSequence> adapter = 
        	ArrayAdapter.createFromResource(this, R.array.interest_array, android.R.layout.simple_spinner_item);  
        // (2) ArrayAdapter객체에 할당된 데이터들을 스피너가 클릭될 때 보여줄 스피너 아이템의 출력형식을 지정함  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // (3) main.xml의 스피너 id 인식
        Spinner spinner = (Spinner) findViewById(R.id.spinner_interest);
        // (4) 스피너에 ArrayAdapter를 연결함 
        spinner.setAdapter(adapter);        
        ///// 스피너 출력: 끝 
    }
}