package org.nashorn.exam0404;

import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Exam0404 extends Activity {
	private String[] nameList;
	private String[] phoneList;
	
	private ArrayList<String> list;
	private ArrayAdapter<String> arrayAdapter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        list = new ArrayList<String>();
		
        getContactsData();
        
        ListView listView = (ListView)findViewById(R.id.list);
		arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
			android.R.layout.simple_list_item_1, list);
  		listView.setAdapter(arrayAdapter);
    }
    
    public void getContactsData()
	{
		Cursor c = this.getContentResolver().query(
        	Contacts.CONTENT_URI, new String[] { BaseColumns._ID },
        	null, null, null);
        c.moveToFirst();
        
        if (c.getCount() > 0)
        {
            nameList = new String[c.getCount()];
            phoneList = new String[c.getCount()];
            for ( int i = 0 ; i < c.getCount() ; i++ ) 
            {
            	try
            	{
                    String _id = c.getString(c.getColumnIndex(BaseColumns._ID));
                    Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Integer.parseInt(_id));
                    Uri dataUri = Uri.withAppendedPath(contactUri, Data.CONTENT_DIRECTORY);
                    Cursor c2 = this.getContentResolver().query(
                        dataUri,
                        new String[] {
                            BaseColumns._ID, Data.MIMETYPE, Data.DATA1, Data.DATA2, Data.DATA3,
                            Data.DATA4, Data.DATA5, Data.DATA6, Data.DATA7, Data.DATA8,
                            Data.DATA9, Data.DATA10, Data.DATA11, Data.DATA12, Data.DATA13, 
                            Data.DATA14, Data.DATA15 },
                        null, null, null );    
                    
                    try 
                    {
                        while ( c2.moveToNext() ) 
                        {    
                    		if ( !c2.isNull(c.getColumnIndex(BaseColumns._ID)) ) 
                            {
                                String mimeType = c2.getString(c2.getColumnIndex(Data.MIMETYPE));
                                if ( mimeType.equals(StructuredName.CONTENT_ITEM_TYPE) ) {
                                	nameList[i] = c2.getString(c2.getColumnIndex(StructuredName.DISPLAY_NAME));
                                } else if ( mimeType.equals(Phone.CONTENT_ITEM_TYPE) ) {
                                    phoneList[i] = c2.getString(c2.getColumnIndex(Phone.NUMBER));
                                }
                            }
                        }
                    } finally {
                        c2.close();
                    }
                	list.add(nameList[i]+" ("+phoneList[i]+")");

                	c.moveToNext();
            	} catch (Exception e)
            	{
   
            	}
            }
        }
	}
}