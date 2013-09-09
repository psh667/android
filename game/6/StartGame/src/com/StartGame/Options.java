
//-------------------------------------
// Options 메뉴  - StartGame에서 호출
//-------------------------------------
package com.StartGame;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Options extends Activity {
	
	RadioGroup rGroup1, rGroup2, rGroup3, rGroup4;	// RadioGroup

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        // 전역변수 읽기
		int xCnt = ((GlobalVars) getApplicationContext()).getXCount();
		int yCnt = ((GlobalVars) getApplicationContext()).getYCount();
		int backGround = ((GlobalVars) getApplicationContext()).getBackground();
		int imgType = ((GlobalVars) getApplicationContext()).getImageType();
        
		if (xCnt == 0) xCnt = 3;
		if (yCnt == 0) yCnt = 3;
		if (backGround == 0) backGround = 1;
		if (imgType == 0) imgType = 1;
        
        // RadioGroup 찾기
        rGroup1 = (RadioGroup) findViewById(R.id.RadioGroup01);
        rGroup2 = (RadioGroup) findViewById(R.id.RadioGroup02);
        rGroup3 = (RadioGroup) findViewById(R.id.RadioGroup03);
        rGroup4 = (RadioGroup) findViewById(R.id.RadioGroup04);
        
        // RadioGroup에 초기값 / 저장된 값 설정
        rGroup1.check(R.id.xRadio1 + xCnt - 3);
        rGroup2.check(R.id.yRadio1 + yCnt - 3);
        rGroup3.check(R.id.backRadio1 + backGround - 1);
        rGroup4.check(R.id.imgRadio1 + imgType - 1);
        
        // ImageView 리스너 처리
        ((ImageView) findViewById(R.id.btnGallery)).setOnClickListener(OnButtonClick);
        ((ImageView) findViewById(R.id.btnBack)).setOnClickListener(OnButtonClick);
    }
    
    //-----------------------------------
    // Button Click
    //-----------------------------------
    Button.OnClickListener OnButtonClick = new Button.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnGallery :		// PhotoGallery
				startActivity(new Intent(Options.this, PhotoGallery.class));
				break;
			case R.id.btnBack :
				SetGlovalVars();		// 현재 상태 전역변수에 저장
				finish();
			} // switch
		}

    };
    
    //-----------------------------------
    // RadioButton의 설정값을  전역 변수에 저장
    //-----------------------------------
	private void SetGlovalVars() {
		RadioButton tmpRadio;	// RadioButton
		int id;					// 선택된 버튼 id 검색용

		// xCnt 찾기 - Text가 이니라 Tag()를 읽어서 처리한다
		id = rGroup1.getCheckedRadioButtonId();
		tmpRadio = (RadioButton) findViewById(id);
		int xCnt = Integer.parseInt(tmpRadio.getTag().toString());
		
		// yCnt 찾기
		id = rGroup2.getCheckedRadioButtonId();
		tmpRadio = (RadioButton) findViewById(id);
		int yCnt = Integer.parseInt(tmpRadio.getTag().toString());
		
		// Background
		id = rGroup3.getCheckedRadioButtonId();
		tmpRadio = (RadioButton) findViewById(id);
		int backGround = Integer.parseInt(tmpRadio.getTag().toString());

		// 이미지 종류 - 1:기본. 2:사용자 지정
		id = rGroup4.getCheckedRadioButtonId();
		tmpRadio = (RadioButton) findViewById(id);
		int imgKind = Integer.parseInt(tmpRadio.getTag().toString());

		// 전역 변수에 저장
		((GlobalVars)getApplicationContext()).setXCount(xCnt);
		((GlobalVars)getApplicationContext()).setYCount(yCnt);
		((GlobalVars)getApplicationContext()).setBackground(backGround);
		((GlobalVars)getApplicationContext()).setImageType(imgKind);
	}

} // Activity
