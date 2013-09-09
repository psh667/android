package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.os.*;
import android.provider.Contacts.*;
import android.widget.*;

public class ReadContactOld extends Activity {
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readcontact);

		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(People.CONTENT_URI,null,null,null,null);

		int nameidx = cursor.getColumnIndex(People.NAME);
		int phoneidx = cursor.getColumnIndex(People.NUMBER);

		StringBuilder result = new StringBuilder();
		while (cursor.moveToNext()) {
			result.append(cursor.getString(nameidx) + " : " + 
					cursor.getString(phoneidx) + "\n");
		}
		cursor.close();

		TextView txtResult =(TextView)findViewById(R.id.result);
		if (result.length() == 0) {
			result.append("주소록이 비어 있습니다.");
		}
		txtResult.setText(result);
	}
}
