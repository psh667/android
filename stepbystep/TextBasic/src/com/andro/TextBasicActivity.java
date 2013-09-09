// TextViewActivity 클래스가 정의되어 있는 패키지
package com.andro;

//Activity 클래스가 정의되어 있는 패키지
import android.app.Activity;
//Bundle 클래스가 정의되어 있는 패키지
import android.os.Bundle;

//TextBasicActivity는 Activity 클래스의 서브 클래스임
public class TextBasicActivity extends Activity {
    /** Called when the activity is first created. */
	
	// 수퍼 클래스인 Activity 클래스의 onCreate() 메소드를 오버라이드(재정의)함
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// 액티비티를 생성함(수퍼 클래스의 메소드 이용)
        super.onCreate(savedInstanceState);
        // res/layout 폴더에 있는 main.xml의 레이아웃을 출력함
        setContentView(R.layout.main);
    }
}