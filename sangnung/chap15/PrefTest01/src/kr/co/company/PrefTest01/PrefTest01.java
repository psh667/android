package kr.co.company.PrefTest01;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class PrefTest01 extends Activity {
    public static final String PREFS_NAME = "MyPrefs";
	TextView name;
	EditText value;
	String imageName;

    @Override
    protected void onCreate(Bundle state){
		super.onCreate(state);
		setContentView(R.layout.main);

		name = (TextView)findViewById(R.id.TextView01);
		value = (EditText)findViewById(R.id.EditText01);

    	// 프레퍼런스를 복원한다.
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		imageName = settings.getString("imageName", "");
		value.setText(imageName);
    }

    @Override
    protected void onStop(){
		super.onStop();

		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		// 프레퍼런스에 값을 기록하려면 Editor 객체가 필요
		SharedPreferences.Editor editor = settings.edit();
		imageName = value.getText().toString();
		editor.putString("imageName", imageName);
		// 변경을 최종 반영한다. 
		editor.commit();
    }
}