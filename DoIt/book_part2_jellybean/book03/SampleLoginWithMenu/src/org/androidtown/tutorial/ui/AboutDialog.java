package org.androidtown.tutorial.ui;

import android.R.style;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

/**
 * About 화면
 * 
 * @author Mike
 */
public class AboutDialog extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 타이틀 부분이 안보이도록 함
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // XML 레이아웃 설정
        setContentView(R.layout.about);
       
        // 이미지가 보이는 비트맵 버튼의 클릭 이벤트 처리
        BitmapButton btn = (BitmapButton) findViewById(R.id.confirmBtn);
        btn.setBitmapId(R.drawable.confirm_btn_normal, R.drawable.confirm_btn_clicked);
        btn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
        
    }
    	
    /**
     * Theme Style 설정
     */
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {
        super.onApplyThemeResource(theme, resid, first);

        theme.applyStyle(style.Theme_Panel, true);
    }
	
}
