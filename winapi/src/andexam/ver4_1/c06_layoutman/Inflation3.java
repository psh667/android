package andexam.ver4_1.c06_layoutman;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class Inflation3 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/* XML 전개를 직접 하기
		LayoutInflater inflater = (LayoutInflater)getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.inflation, null);
		setContentView(linear);
		//*/

		/* 컨텍스트로부터 전개자 구하기
		LayoutInflater inflater = LayoutInflater.from(this);
		LinearLayout linear = (LinearLayout)inflater.inflate(R.layout.inflation, null);
		setContentView(linear);
		//*/

		/* View의 정적 메소드 사용하여 전개하기
		LinearLayout linear = (LinearLayout)View.inflate(this, R.layout.inflation, null);
		setContentView(linear);
		//*/

		//* 가장 짧은 전개 코드
		setContentView(View.inflate(this, R.layout.inflation, null));
		//*/
	}
}