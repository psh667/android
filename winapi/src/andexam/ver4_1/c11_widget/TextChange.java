package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.text.*;
import android.widget.*;

public class TextChange extends Activity {
	EditText mEdit;
	TextView mText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textchange);

		mEdit = (EditText)findViewById(R.id.edit);
		mText = (TextView)findViewById(R.id.text);
		mEdit.addTextChangedListener(mWatcher);
	}

	TextWatcher mWatcher = new TextWatcher() {
		public void afterTextChanged(Editable s) {
		}

		public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
		}

		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mText.setText("echo:" + s);
		}
	};
}

