package andexam.ver4_1.c08_input;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

/* 임시 객체로 핸들러 만들기
public class Fruit extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fruit);

		Button btnApple=(Button)findViewById(R.id.apple);
		btnApple.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				TextView textFruit=(TextView)findViewById(R.id.fruit);
				textFruit.setText("Apple");
			}
		});

		Button btnOrange=(Button)findViewById(R.id.orange);
		btnOrange.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				TextView textFruit=(TextView)findViewById(R.id.fruit);
				textFruit.setText("Orange");
			}
		});
	}
}
//*/

/* 핸들러 통합하기 - 인터페이스 구현
public class Fruit extends Activity implements View.OnClickListener {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fruit);

		Button btnApple=(Button)findViewById(R.id.apple);
		btnApple.setOnClickListener(this);
		Button btnOrange=(Button)findViewById(R.id.orange);
		btnOrange.setOnClickListener(this);
	}

	public void onClick(View v) {
		TextView textFruit=(TextView)findViewById(R.id.fruit);
		switch (v.getId()) {
		case R.id.apple:
			textFruit.setText("Apple");
			break;
		case R.id.orange:
			textFruit.setText("Orange");
			break;
		}
	}
}
//*/

//* 핸들러 통합하기 - 인터페이스 구현 멤버 객체
public class Fruit extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fruit);

		findViewById(R.id.apple).setOnClickListener(mClickListener);
		findViewById(R.id.orange).setOnClickListener(mClickListener);
	}

	Button.OnClickListener mClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			TextView textFruit=(TextView)findViewById(R.id.fruit);
			switch (v.getId()) {
			case R.id.apple:
				textFruit.setText("Apple");
				break;
			case R.id.orange:
				textFruit.setText("Orange");
				break;
			}
		}
	};
}
//*/
