package org.androidtown.tutorial.ui;

import java.util.Vector;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

/**
 * 직접 만들어보는 설정 화면
 * API에서 제공하는 설정 화면은 다른 방법으로 만들어야 함
 * 
 * @author Mike
 */
public class SettingsActivity extends Activity {

	LinearLayout mainLayout;
	LinearLayout.LayoutParams params1, params2;
	
	Vector<String> titleList = new Vector<String>();
	Vector<EditText> inputList = new Vector<EditText>();
	
	public static final String PREF_NAME = "Settings";
	
	ViewFlipper flipper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 타이틀이 보이지 않도록 함
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.settings);
       
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        
        params1 = new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.WRAP_CONTENT,
        		LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.leftMargin = 10;
        params1.rightMargin = 10;
        
        params2 = new LinearLayout.LayoutParams(
        		LinearLayout.LayoutParams.FILL_PARENT,
        		LinearLayout.LayoutParams.WRAP_CONTENT);
        params2.rightMargin = 10;
      
        // 타이틀 부분을 새로 만든 것에 텍스트 설정
        TitleButton titleBtn = (TitleButton) findViewById(R.id.titleBtn);
        titleBtn.setTitleText("환경설정");
        
        // 플리퍼 객체
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        
        TextButtonItem item01 = new TextButtonItem(this);
        item01.setTitleText("New drug from USA");
        item01.setContentsText("Glaksos Inc. announced new drug.");
        flipper.addView(item01);
        
        TextButtonItem item02 = new TextButtonItem(this);
        item02.setTitleText("Aspirin proved");
        item02.setContentsText("Aspirin is effective to high BP.");
        flipper.addView(item02);
        
        TextButtonItem item03 = new TextButtonItem(this);
        item03.setTitleText("Medicine Conference");
        item03.setContentsText("10th medicine conference in Seoul.");
        flipper.addView(item03);
         
        flipper.setInAnimation(this, R.anim.push_down_in);
        flipper.setOutAnimation(this, R.anim.push_down_out);
        flipper.setFlipInterval(5000);
        
        // 저장 버튼 이벤트 처리
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		save();
        		finish();
        	}
        });

        // 취소 버튼 이벤트 처리
        Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });

        init();
        
    }
    
    /**
     * 초기화
     */
    private void init() {
    	addItem("Host");
    	addItem("Port");
    }
    
    
    protected void onPause() {
    	super.onPause();

    	// 플리핑
    	flipper.stopFlipping();
        
    }
    
    protected void onResume() {
    	super.onResume();
    	
    	load();
    	
    	// 플리핑
    	flipper.startFlipping();
        
    }
    
    /**
     * 아이템 추가
     * 
     * @param title
     */
    public void addItem(String title) {
        LinearLayout aRow = new LinearLayout(this);
        
        TextView aText = new TextView(this);
        aText.setText(title);
        aText.setTextSize(16);
        aRow.addView(aText, params1);

        EditText aInput = new EditText(this);
        aRow.addView(aInput, params2);
        
        mainLayout.addView(aRow, params2);
        
        titleList.add(title);
        inputList.add(aInput);
    }
    
    /**
     * Preferences 에 저장
     */
    public void save() {
    	Toast.makeText(this, "save() 호출됨.", 2000).show();
    	
    	SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        
        for (int i = 0; i < inputList.size(); i++) {
        	String title = titleList.get(i);
        	String itemStr = inputList.get(i).getText().toString().trim();
        	editor.putString(title, itemStr);
        }
        
        editor.commit();
    }
    
    /**
     * Preferences 에서 로딩
     */
    public void load() {
    	Toast.makeText(this, "load() 호출됨.", 2000).show();
    	
    	SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);       
        
    	if (settings != null) {
    		for (int i = 0; i < titleList.size(); i++) {
    			String title = titleList.get(i);
    			String itemStr = settings.getString(title, "");
    			EditText item = inputList.get(i);
    			item.setText(itemStr);
    			item.invalidate();
    			
    		}
    	}
    	
    }
    
}
