package ex.ProviderTest1;

import java.io.FileInputStream;
import java.io.IOException;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.content.ContentValues;
import android.database.Cursor;

public class ProviderTest1 extends Activity {
    /** Called when the activity is first created. */
	EditText name;
	EditText phone;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] projection = new String[]{
                People.NAME,
                People.NUMBER
             };

        Cursor c = this.getContentResolver().query(Contacts.Phones.CONTENT_URI, projection, null, null, People.NAME + " ASC");
        c.moveToFirst();
        int nameColumn = c.getColumnIndex(People.NAME);
        int phoneColumn = c.getColumnIndex(People.NUMBER);
        if( c.getCount() > 0 ) {
        	do{
        		String name = c.getString(nameColumn);
        		String phoneNumber = c.getString(phoneColumn);
            
        		Toast.makeText(this, name+":"+phoneNumber, Toast.LENGTH_SHORT).show();
        	} while(c.moveToNext());
        }
    }

}