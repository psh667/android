package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import java.text.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.database.*;
import android.os.*;
import android.provider.*;
import android.widget.*;

public class CallLogTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readcontact);

		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,null,null,null,
				CallLog.Calls.DATE + " DESC");

		int nameidx = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int dateidx = cursor.getColumnIndex(CallLog.Calls.DATE);
		int numidx = cursor.getColumnIndex(CallLog.Calls.NUMBER);
		int duridx = cursor.getColumnIndex(CallLog.Calls.DURATION);
		int typeidx = cursor.getColumnIndex(CallLog.Calls.TYPE);

		StringBuilder result = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
		result.append("총 기록 개수 : " + cursor.getCount() + "개\n");
		int count = 0;
		while (cursor.moveToNext()) {
			// 통화 대상자
			String name = cursor.getString(nameidx);
			if (name == null) {
				name = cursor.getString(numidx);
			}
			result.append(name);

			// 통화 종류
			int type = cursor.getInt(typeidx);
			String stype;
			switch (type) {
			case CallLog.Calls.INCOMING_TYPE:
				stype = "수신";
				break;
			case CallLog.Calls.OUTGOING_TYPE:
				stype = "발신";
				break;
			case CallLog.Calls.MISSED_TYPE:
				stype = "부재중";
				break;
			case 14:
				stype = "문자보냄";
				break;
			case 13:
				stype = "문자받음";
				break;
			default:
				stype = "기타:" + type;
				break;
			}
			result.append("(" + stype + ") : ");

			// 통화 날짜
			long date = cursor.getLong(dateidx);
			String sdate = formatter.format(new Date(date));
			result.append(sdate + ",");

			// 통화 시간
			int duration = cursor.getInt(duridx);
			result.append(duration + "초\n");

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
