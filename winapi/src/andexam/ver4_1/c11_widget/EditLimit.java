package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.text.*;
import android.widget.*;

public class EditLimit extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editlimit);

		EditText LimitEdit = (EditText)findViewById(R.id.limit);
		LimitEdit.setFilters(new InputFilter[] {
				new InputFilter.LengthFilter(3)
		});
	}
}