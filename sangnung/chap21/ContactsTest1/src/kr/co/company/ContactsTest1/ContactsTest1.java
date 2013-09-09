package kr.co.company.ContactsTest1;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ContactsTest1 extends Activity {
	private TextView text;
	private StringBuilder display = new StringBuilder();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text = (TextView) findViewById(R.id.text);
		populateContactList();
	}

	/**
	 * 연락처의 전화번호로 화면을 채운다.
	 */
	private void populateContactList() {
		// 연락처를 생성한다.
		Cursor cursor = getContacts();

		// 연락처를 하나씩 처리한다.
		while (cursor.moveToNext()) {
			display.append(cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
			display.append("\n");
			String id = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));

			// 연락처의 전화번호 데이터를 구한다.
			Cursor cursor1 = getData(id);
			while (cursor1.moveToNext()) {
				display.append(cursor1.getString(cursor1
						.getColumnIndex(Phone.NUMBER)));
				display.append("\n");
			}
			display.append("\n\n");
		}
		text.setText(display);
	}

	/**
	 * 전체 연락처 리스트를 구한다.
	 */
	private Cursor getContacts() {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		String[] projection = new String[] { ContactsContract.Contacts._ID,
				ContactsContract.Contacts.DISPLAY_NAME };
		return managedQuery(uri, projection, null, null, null);
	}

	/**
	 * 아이디가 contactId인 연락처의 전화번호만을 구한다.
	 */
	private Cursor getData(String contactId) {
		return getContentResolver()
				.query(Data.CONTENT_URI,
						new String[] { Data._ID, Phone.NUMBER, Phone.TYPE,
								Phone.LABEL },
						Data.CONTACT_ID + "=?" + " AND " + Data.MIMETYPE + "='"
								+ Phone.CONTENT_ITEM_TYPE + "'",
						new String[] { contactId }, null);
	}
}
