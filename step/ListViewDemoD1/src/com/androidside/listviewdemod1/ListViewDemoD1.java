package com.androidside.listviewdemod1;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class ListViewDemoD1 extends ListActivity {    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        Cursor contactsCursor = this.managedQuery(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        startManagingCursor(contactsCursor);

        String[] columns = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        int[] ids = new int[]{android.R.id.text1};

        ListAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, contactsCursor,
                columns, ids);
        setListAdapter(adapter);
    }
}
