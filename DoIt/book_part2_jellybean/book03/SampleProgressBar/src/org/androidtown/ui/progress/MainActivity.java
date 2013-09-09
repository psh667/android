package org.androidtown.ui.progress;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 프로그레스바를 사용하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 프로그레스바를 보여줄 때 사용할 상수
	 */
	public static final int PROGRESS_DIALOG = 1001;
	
	/**
	 * 프로그레스 대화상자 객체
	 */
	ProgressDialog progressDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃에 추가된 프로그레스바 객체 참조
        ProgressBar proBar = (ProgressBar) findViewById(R.id.progressBar01);
        proBar.setIndeterminate(false);
        proBar.setMax(100);
        proBar.setProgress(80);

        // 아이콘 이미지 설정
        ImageView icon = (ImageView) findViewById(R.id.iconItem);
        Resources res = getResources();
        Drawable drawable = (Drawable) res.getDrawable(R.drawable.apple);
        icon.setImageDrawable(drawable);

        // 텍스트 설정
        TextView nameText = (TextView) findViewById(R.id.dataItem01);
        nameText.setText("사과");
		
        // 텍스트 설정
        TextView progressText = (TextView) findViewById(R.id.dataItem02);
		progressText.setText("80%");
		
		// 보여주기 버튼 이벤트 설정
		Button btnShow = (Button) findViewById(R.id.btnShow);
		btnShow.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				showDialog(PROGRESS_DIALOG);
			}
		});
		
		// 닫기 버튼 이벤트 설정
		Button btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (progressDialog != null) {
					progressDialog.dismiss();
				}
			}
		});
        
    }

    /**
     * 대화상자를 만들 수 있도록 자동으로 호출되는 메소드
     */
    public Dialog onCreateDialog(int id) {
    	switch (id) {
	    	case (PROGRESS_DIALOG):
	    		progressDialog = new ProgressDialog(this);
	    		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    		progressDialog.setMessage("데이터를 확인하는 중입니다.");
	    		
	    		return progressDialog;
    	}
    	
    	return null;
    }
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
