package ex.LayoutParam;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class LayoutParam extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout manager = new LinearLayout(this);
		manager.setOrientation(LinearLayout.VERTICAL);

		Button button1 = new Button(this);
		button1.setText("테스트 버튼1");
		Button button2 = new Button(this);
		button2.setText("테스트 버튼2");

		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		manager.addView(button1, param);
		manager.addView(button2, param);
		setContentView(manager);
	}
}