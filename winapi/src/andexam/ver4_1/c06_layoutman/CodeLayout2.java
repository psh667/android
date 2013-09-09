package andexam.ver4_1.c06_layoutman;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class CodeLayout2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.codelayout);

		LinearLayout MyLinear=(LinearLayout)findViewById(R.id.mylinear);
		MyLinear.setOrientation(LinearLayout.HORIZONTAL);

		Button MyBtn = (Button)findViewById(R.id.mybutton);
		MyBtn.setTextSize(40);

		EditText MyEdit = (EditText)findViewById(R.id.myedit);
		MyEdit.setBackgroundColor(0xff00ff00);
	}
}