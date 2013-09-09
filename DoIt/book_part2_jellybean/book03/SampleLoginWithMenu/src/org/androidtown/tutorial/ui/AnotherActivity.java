package org.androidtown.tutorial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 또다른 액티비티
 * 
 * @author Mike
 */
public class AnotherActivity extends Activity {
    // 색상값
	int[] colors = {0xff000000, 0xff440000, 0xff884400, 0xffaa8844, 0xffffaa88,
					0xffffffaa, 0xffffffff, 0xffaaffff, 0xff88aaff, 0xff4488aa};
	// 버튼 ID
	int[] buttonIds = {R.id.Button01, R.id.Button02, R.id.Button03, R.id.Button04, R.id.Button05,
			R.id.Button06, R.id.Button07, R.id.Button08, R.id.Button09, R.id.Button10};
 
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);
        
        // 버튼 이벤트 처리
        Button btn = null;
        ColorOnClickListener listener = null;
        
        for (int i = 0; i < buttonIds.length; i++) {
	        btn = (Button) findViewById(buttonIds[i]);
	        listener = new ColorOnClickListener(i);
	        btn.setBackgroundColor(colors[i]);
	        
	        // 리스너를 별도로 정의한 후 그 객체를 설정함
	        btn.setOnClickListener(listener);
        }
        
    }
    

    /**
     * 이벤트 처리를 위해 정의한 리스너 객체
     */
    class ColorOnClickListener implements OnClickListener {
    	 
    	int index;
    	
    	public ColorOnClickListener(int idx) {
    		index = idx;
    	}
    	
    	public void onClick(View v) {
    		// 메인 액티비티에 색상값을 전달함
    		Intent resultIntent = new Intent();        		
            resultIntent.putExtra("color", colors[index]);

            // 응답 전송 후 닫기
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
    	}
    	
    }
    
}
