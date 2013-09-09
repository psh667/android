package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.telephony.*;
import android.view.*;
import android.widget.*;

public class FormatNumber extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formatnumber);
		
		EditText telNum = (EditText)findViewById(R.id.telNum);
		telNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		
		if (PhoneNumberUtils.isEmergencyNumber("911")) {
			Toast.makeText(this, "Emergency", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "No Emergency", Toast.LENGTH_LONG).show();
		}
	}
}
