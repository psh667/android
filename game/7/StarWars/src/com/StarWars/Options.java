package com.StarWars;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Options extends Activity {
	
	RadioGroup rGroup1;
	ImageView image1, image2, image3;
	
	boolean onOff1 = true, onOff2 = true, onOff3 = true; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.options);

        // 라디오버튼 초기 설정은 Easy
        rGroup1 = (RadioGroup) findViewById(R.id.RadioGroup01);
        rGroup1.check(R.id.RButton01);
        
        image1 = (ImageView) findViewById(R.id.btn01); 
        image2 = (ImageView) findViewById(R.id.btn02); 
        image3 = (ImageView) findViewById(R.id.btn03); 
        
        image1.setOnClickListener(OnButtonClick);
        image2.setOnClickListener(OnButtonClick);
        image3.setOnClickListener(OnButtonClick);
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(OnButtonClick);
        
    }
    
    //-----------------------------------
    // Button Click
    //-----------------------------------
    Button.OnClickListener OnButtonClick = new Button.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn01 :		// PhotoGallery
				onOff1 = !onOff1;
				if (onOff1)
					image1.setImageResource(R.drawable.btn_on);
				else
					image1.setImageResource(R.drawable.btn_off);
				break;
			case R.id.btn02 :		// PhotoGallery
				onOff2 = !onOff2;
				if (onOff2)
					image2.setImageResource(R.drawable.btn_on);
				else
					image2.setImageResource(R.drawable.btn_off);
				break;
			case R.id.btn03 :		// PhotoGallery
				onOff3 = !onOff3;
				if (onOff3)
					image3.setImageResource(R.drawable.btn_on);
				else
					image3.setImageResource(R.drawable.btn_off);
				break;
			case R.id.btnBack :
				SetGlovalVars();
				finish();
			} // switch
		}
    };
    
    //-----------------------------------
    // 설정값을  전역 변수에 저장
    //-----------------------------------
	private void SetGlovalVars() {
		// 선택된 RadioButton 찾기
		int id = rGroup1.getCheckedRadioButtonId();
		
		RadioButton tmpRadio = (RadioButton) findViewById(id);
		int difficult = Integer.parseInt(tmpRadio.getTag().toString());

		// 전역 변수에 저장
		((GlobalVars) getApplicationContext()).setDifficult(difficult);
		((GlobalVars) getApplicationContext()).setIsMusic(onOff1);
		((GlobalVars) getApplicationContext()).setIsSound(onOff2);
		((GlobalVars) getApplicationContext()).setIsVibe(onOff3);
	}
    
}

