package andexam.ver4_1.c36_tel;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.os.*;
import android.provider.*;
import android.widget.*;

public class ReadContact extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readcontact);

		ContentResolver cr = getContentResolver();
		Cursor cursor = cr.query(
				ContactsContract.Contacts.CONTENT_URI,null,null,null,null);

		int ididx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
		int nameidx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

		StringBuilder result = new StringBuilder();
		while (cursor.moveToNext()) {
			result.append(cursor.getString(nameidx) + " :"); 

			// 전화 번호는 서브 쿼리로 조사해야 함.
			String id = cursor.getString(ididx);
			Cursor cursor2 = cr.query(ContactsContract.CommonDataKinds.
					Phone.CONTENT_URI, null, 
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
					new String[]{id}, null);

			int typeidx = cursor2.getColumnIndex(
					ContactsContract.CommonDataKinds.Phone.TYPE);
			int numidx = cursor2.getColumnIndex(
					ContactsContract.CommonDataKinds.Phone.NUMBER);

			// 전화의 타입에 따라 여러 개가 존재한다.
			while (cursor2.moveToNext()) {
				String num = cursor2.getString(numidx);
				switch (cursor2.getInt(typeidx)) {
				case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
					result.append(" Mobile:" + num);
					break;
				case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
					result.append(" Home:" + num);
					break;
				case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
					result.append(" Work:" + num);
					break;
				}
			}
			cursor2.close();
			result.append("\n");
		}
		cursor.close();

		TextView txtResult =(TextView)findViewById(R.id.result);
		txtResult.setText(result);
	}
}
