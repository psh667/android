package org.androidtown.ui.seekbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * 시크바를 이용해 값을 설정하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 시크바를 담고 있는 레이아웃 객체
	 */
    private View panel;
    
    /**
     * 시크바 객체
     */
    private SeekBar seekbar;
    
    /**
     * 텍스트뷰
     */
    private TextView text01;
    
    /**
     * 화면밝기 값
     */
    private int brightness = 50;
	    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃에 정의된 객체 참조
        panel = findViewById(R.id.panel01);
        text01 = (TextView) findViewById(R.id.text01);
        seekbar = (SeekBar) findViewById(R.id.seekbar01);

        // 버튼 이벤트 처리
        Button showBtn = (Button) findViewById(R.id.showBtn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPanel();
            }
        });

        // 시크바에 리스너 설정
        seekbar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
        
    }

    
    /**
     * 시크바를 담고 있는 레이아웃을 보여주는 메소드
     */
    private void showPanel() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        seekbar.setProgress(this.brightness);
        panel.setVisibility(View.VISIBLE);
        panel.startAnimation(animation);
    }

    /**
     * 화면 밝기 설정
     */
    private void setBrightness(int value) {
        if (value < 10) {
            value = 10;
        } else if (value > 100) {
            value = 100;
        }
        
        brightness = value;
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = (float) value / 100;        
        getWindow().setAttributes(params);
    }

    /**
     * 시크바를 담고 있는 레이아웃을 보이지 않도록 함
     */
    private void hidePanel() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_right);
        panel.startAnimation(animation);
        panel.setVisibility(View.GONE);

    }
    
    /**
     * 시크바에 설정할 리스너
     */
    class MyOnSeekBarChangeListener implements OnSeekBarChangeListener {
    	/**
    	 * 시크바의 값이 바뀔 때 자동 호출됨
    	 * 
    	 * @param seekBar
    	 * @param progress
    	 * @param fromUser
    	 */
    	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            setBrightness(progress);
            text01.setText("밝기 수준 : " + progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            hidePanel();
        }
    }
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
