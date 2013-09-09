/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appstudio.android.sample.ch_23_7;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public final class ContactManager extends Activity  {
    public static final String TAG = "ContactManager";
    private Button mAddAccountButton;
    private ListView mContactList;

    /**
     * 연락처 목록 조회를 위한 화면
     */
    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_manager);
        // 연락처 추가를 위한 버튼
        mAddAccountButton = 
            (Button) findViewById(R.id.addContactButton);
        mContactList = 
            (ListView) findViewById(R.id.contactList);
        // 연락처 추가를 위한 액티비티로 진행
        mAddAccountButton.setOnClickListener(
                new View.OnClickListener() {
            public void onClick(View v) {
                launchContactAdder();
            }
        });
        // 연락처를 가져와서 목록에 설정
        populateContactList();
    }

    private void populateContactList() {
        Cursor cursor = getContacts();
        ContactAdapter adapter = 
            new ContactAdapter(this, cursor);
        mContactList.setAdapter(adapter);
    }

    private class ContactAdapter extends CursorAdapter { 
        public ContactAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        @Override
        public void bindView(
                View view, Context context, Cursor cursor) {
            int col = cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME);  
            String name = cursor.getString(col);
            String value = name;
            col = cursor.getColumnIndex(
                    ContactsContract.Contacts._ID);  
            String id = cursor.getString(col);
            ContentResolver cr = getContentResolver();
            
            Cursor cursor2 = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, 
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID+
                                                         "=?", 
            new String[]{id}, null);
            
            int typeIndex = cursor2.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.TYPE);
            int numberIndex = cursor2.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.NUMBER);
          
            while(cursor2.moveToNext())  {
                String number = cursor2.getString(numberIndex);
                switch(cursor2.getInt(typeIndex))  {
                case ContactsContract.CommonDataKinds.
                                      Phone.TYPE_MOBILE :
                    value += " 핸드폰:"+number;
                    break;
                case ContactsContract.CommonDataKinds.Phone.
                                      TYPE_HOME :
                    value += " 집:"+number;
                    break;
                case ContactsContract.CommonDataKinds.Phone.
                                      TYPE_WORK :
                    value += " 직장:"+number;
                    break;    
                }
            }
            cursor2.close();
            TextView text = (TextView) view.findViewById(
                    R.id.contactEntryText);
            text.setText(value);  
        }

        @Override
        public View newView(Context context, 
                Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = 
                LayoutInflater.from(context);  
            View v = inflater.inflate(
                    R.layout.contact_entry, parent, false);  
            return v;  
        } 
    }    

    // 연락처 정보를 커서로 조회한다.
    private Cursor getContacts()  {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String[] projection = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        String sortOrder = ContactsContract.Contacts.
                       DISPLAY_NAME + " COLLATE LOCALIZED ASC";
       Cursor c = 
          managedQuery(uri, projection, null, null, sortOrder);
        return c;
    }

    /**
     * 연락처 추가를 위한 액티비티를 기동한다.
     */
    protected void launchContactAdder() {
        Intent i = new Intent(this, ContactAdder.class);
        startActivity(i);
    }
}
