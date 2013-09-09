package com.corea.SharedPreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;

public class SharedPreference extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // Shared Preference를 불러옵니다.
        EditText edit1 = (EditText)findViewById(R.id.EditText);
    	CheckBox check1 = (CheckBox)findViewById(R.id.CheckBox01);
    	CheckBox check2 = (CheckBox)findViewById(R.id.CheckBox02);
    	
        // 저장된 값들을 불러옵니다.
    	String text = pref.getString("editText", "");
    	Boolean chk1 = pref.getBoolean("check1", false);
    	Boolean chk2 = pref.getBoolean("check2", false);
    	
    	edit1.setText(text);
    	check1.setChecked(chk1);
    	check2.setChecked(chk2);
    }
    
    public void onStop(){ // 어플리케이션이 종료될때
    	super.onStop();
        Log.i("HelloWorld", "Call ONStop");

    	SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE); // UI 상태를 저장합니다.
    	SharedPreferences.Editor editor = pref.edit(); // Editor를 불러옵니다.
    	
    	EditText edit1 = (EditText)findViewById(R.id.EditText);
    	CheckBox check1 = (CheckBox)findViewById(R.id.CheckBox01);
    	CheckBox check2 = (CheckBox)findViewById(R.id.CheckBox02);
    	
                // 저장할 값들을 입력합니다.
    	editor.putString("editText", edit1.getText().toString());
    	editor.putBoolean("check1", check1.isChecked());
    	editor.putBoolean("check2", check2.isChecked());
    	
    	editor.commit(); // 저장합니다.
    }
}