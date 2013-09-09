package org.androidtown.basic.parcelable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 새로운 액티비티
 * 
 * @author Mike
 */
public class AnotherActivity extends Activity {

	/**
	 * 부가 데이터를 위해 정의한 키 값
	 */
	public static final String KEY_SIMPLE_DATA = "data";
	
	/**
	 * 텍스트뷰
	 */
	TextView txtMsg;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);

        txtMsg = (TextView) findViewById(R.id.txtMsg);
        Button backBtn = (Button) findViewById(R.id.backBtn);
		
		// 버튼을 눌렀을 때 메인 액티비티로 돌아갑니다.
		backBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
        		// 객체를 만듭니다.
        		Intent resultIntent = new Intent();
        		resultIntent.putExtra("name", "mike");

                // 응답을 전달하고 이 액티비티를 종료합니다.
        		setResult(RESULT_OK, resultIntent);
                finish();
			}
		});

		// 전달된 인텐트를 처리합니다.
		processIntent();
    }

    /**
     * 전달된 인텐트 처리
     */
    private void processIntent() {
    	// 인텐트 안의 번들 객체를 참조합니다.
        Bundle bundle = getIntent().getExtras();
        
        // 번들 객체 안의 SimpleData 객체를 참조합니다.
        SimpleData data = (SimpleData)bundle.getParcelable(KEY_SIMPLE_DATA);

        // 텍스트뷰에 값을 보여줍니다.
        txtMsg.setText("Parcelable 객체로 전달된 값\nNumber : " + data.getNumber() + "\nMessage : " + data.getMessage());
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
