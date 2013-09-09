package pro.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;


public class WtGtActivity extends Activity 
{
	@Override 
	protected void  onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weightgravity);

		EditText wget1 = (EditText)this.findViewById(R.id.wtgt_et1);
		wget1.setText("one");
		EditText wget2 = (EditText)this.findViewById(R.id.wtgt_et2);
		wget2.setText("two");
		EditText wget3 = (EditText)this.findViewById(R.id.wtgt_et3);
		wget3.setText("three");
	}
}
