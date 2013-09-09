package ex.ProviderTest2;

import java.io.FileInputStream;
import java.io.IOException;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProviderTest2 extends Activity {
	/** Called when the activity is first created. */
	EditText nameEdit;
	EditText phoneEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		nameEdit = (EditText) findViewById(R.id.name);
		phoneEdit = (EditText) findViewById(R.id.phonenumber);
		Button read = (Button) findViewById(R.id.read);
		Button write = (Button) findViewById(R.id.write);
		write.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ContentValues values = new ContentValues();

				// Add Abraham Lincoln to contacts and make him a favorite.
				String name = nameEdit.getText().toString();
				String phone = phoneEdit.getText().toString();
				values.put(People.NAME, name);
				// 1 = the new contact is added to favorites
				// 0 = the new contact is not added to favorites
				values.put(People.STARRED, 1);
				Uri uri = getContentResolver().insert(People.CONTENT_URI,
						values);

				Uri phoneUri = Uri.withAppendedPath(uri,
						People.Phones.CONTENT_DIRECTORY);
				values.clear();
				values.put(People.Phones.TYPE, People.Phones.TYPE_MOBILE);
				values.put(People.Phones.NUMBER, phone);
				getContentResolver().insert(phoneUri, values);
			}
		});
	}

}