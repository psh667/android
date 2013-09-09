package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import java.text.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.widget.*;

public class SmsLog extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readcontact);

		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(Uri.parse("content://sms/inbox"),null,null,null,null);

		int nameidx = cursor.getColumnIndex("address");
		int dateidx = cursor.getColumnIndex("date");
		int bodyidx = cursor.getColumnIndex("body");

		StringBuilder result = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
		result.append("총 기록 개수 : " + cursor.getCount() + "개\n");
		int count = 0;
		while (cursor.moveToNext()) {
			String name = cursor.getString(nameidx);
			result.append(name + " : ");

			// 날짜
			long date = cursor.getLong(dateidx);
			String sdate = formatter.format(new Date(date));
			result.append(sdate + ",");

			// 내용
			String body = cursor.getString(bodyidx);
			result.append(body + "\n\n");

			// 최대 100개까지만
			if (count++ == 100) {
				break;
			}
		}
		cursor.close();

		TextView txtResult =(TextView)findViewById(R.id.result);
		txtResult.setText(result);
	}
}
