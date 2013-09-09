package com.paad.emergencyresponder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class AutoResponder extends Activity {
	
	public static final String alarmAction = "com.paad.emergencyresponder.AUTO_RESPONSE_EXPIRED";

	Spinner respondForSpinner;
	CheckBox locationCheckbox;
	EditText responseTextBox;
	
	PendingIntent intentToFire;
	
	private BroadcastReceiver stopAutoResponderReceiver =
		new BroadcastReceiver() {
        	@Override
        	public void onReceive(Context context, Intent intent) {
        		if (intent.getAction().equals(alarmAction)) {
        			String preferenceName = getString(R.string.user_preferences);
        			SharedPreferences sp = getSharedPreferences(preferenceName,0);

        			Editor editor = sp.edit();
        			editor.putBoolean(getString(R.string.autoRespondPref), false);
        			editor.commit();
        		}
        	}
    	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.autoresponder);
	    
	    respondForSpinner = (Spinner)findViewById(R.id.spinnerRespondFor);
	    locationCheckbox = (CheckBox)findViewById(R.id.checkboxLocation);
	    responseTextBox = (EditText)findViewById(R.id.responseText);

	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	                                                                         R.array.respondForDisplayItems,
	                                                                         android.R.layout.simple_spinner_item);

	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    respondForSpinner.setAdapter(adapter);

	    Button okButton = (Button) findViewById(R.id.okButton);
	    okButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            savePreferences();
	            setResult(RESULT_OK, null);
	            finish();
	        }
	    });

	    Button cancelButton = (Button) findViewById(R.id.cancelButton);
	    cancelButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View view) {
	            respondForSpinner.setSelection(-1);
	            savePreferences();
	            setResult(RESULT_CANCELED, null);
	            finish();
	        }
	    });
	    
	    // 저장된 환경설정을 바탕으로 UI를 업데이트한다.
	    updateUIFromPreferences();
	}
	
	private void updateUIFromPreferences() {
	    // 저장된 설정내용을 얻어온다.
	    String preferenceName = getString(R.string.user_preferences);
	    SharedPreferences sp = getSharedPreferences(preferenceName, 0);

	    String autoResponsePref = getString(R.string.autoRespondPref);
	    String responseTextPref = getString(R.string.responseTextPref);
	    String includeLocationPref = getString(R.string.includeLocationPref);
	    String respondForPref = getString(R.string.respondForPref);

	    boolean autoRespond = sp.getBoolean(autoResponsePref, false);
	    String respondText = sp.getString(responseTextPref, "");
	    boolean includeLoc = sp.getBoolean(includeLocationPref, false);
	    int respondForIndex = sp.getInt(respondForPref, 0);

	    // 저장된 설정내용을 UI에 적용한다.
	    if (autoRespond)
	        respondForSpinner.setSelection(respondForIndex);
	    else
	        respondForSpinner.setSelection(0);

	    locationCheckbox.setChecked(includeLoc);
	    responseTextBox.setText(respondText);
	}
	
	private void savePreferences() {
	    // UI에 설정된 내용을 얻어온다.
	    boolean autoRespond =
	        respondForSpinner.getSelectedItemPosition() > 0;
	    int respondForIndex = respondForSpinner.getSelectedItemPosition();
	    boolean includeLoc = locationCheckbox.isChecked();
	    String respondText = responseTextBox.getText().toString();

	    // 이를 공유 환경설정 파일에 저장한다.
	    String preferenceName = getString(R.string.user_preferences);
	    SharedPreferences sp = getSharedPreferences(preferenceName, 0);

	    Editor editor = sp.edit();
	    editor.putBoolean(getString(R.string.autoRespondPref),
	                           autoRespond);
	    editor.putString(getString(R.string.responseTextPref),
	                          respondText);
	    editor.putBoolean(getString(R.string.includeLocationPref),
	                           includeLoc );
	    editor.putInt(getString(R.string.respondForPref),respondForIndex);
	    editor.commit();

	    // 자동 응답기를 끄기 위한 알람을 설정한다.
	    setAlarm(respondForIndex);
	}

	private void setAlarm(int respondForIndex) {
	    // 알람을 생성하고, 알람 브로드캐스트 리시버를 등록한다.
	    AlarmManager alarms =
	        (AlarmManager)getSystemService(ALARM_SERVICE);

	    if (intentToFire == null) {
	        Intent intent = new Intent(alarmAction);
	        intentToFire = PendingIntent.getBroadcast(getApplicationContext(), 0,intent,0);
	        IntentFilter filter = new IntentFilter(alarmAction);

	        registerReceiver(stopAutoResponderReceiver, filter);
	    }

	    if (respondForIndex < 1)
	        // "사용 안함"이 선택된 경우, 알람을 취소한다.
	        alarms.cancel(intentToFire);
	    else {
	        // 그렇지 않으면, 사용자가 선택한 만료 시간을 알아낸 뒤,
	        // 해당 시간이 경과되면 알람이 발생하도록 설정한다.
	        Resources r = getResources();
	        int[] respondForValues = r.getIntArray(R.array.respondForValues);
	        int respondFor = respondForValues [respondForIndex];

	        long t = System.currentTimeMillis();
	        t = t + respondFor*1000*60;

	        // 알람을 설정한다.
	        alarms.set(AlarmManager.RTC_WAKEUP, t, intentToFire);
	    }
	}
}
