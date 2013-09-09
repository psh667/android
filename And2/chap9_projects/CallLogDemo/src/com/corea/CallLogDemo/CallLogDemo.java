package com.corea.CallLogDemo;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class CallLogDemo extends ListActivity{

    private static String[] PROJECTION = new String[] {
      Phone._ID, Phone.DISPLAY_NAME, Phone.NUMBER
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Phone.CONTENT_URI = vnd.android.cursor.dir/phone_v2
        Cursor c = getContentResolver().query(Phone.CONTENT_URI,	// Phone.CONTENT_URI이란 액션으로 가장 적합한 URI를 찾아서 커서 객체에 담는다. 여기서는 전화번호부URI
        		  PROJECTION, null, null, null);					// _ID, DISPLAY_NAME, NUMBER의 항목을 커서에 담는다.
        startManagingCursor(c);										// 커서와 엑티비티의 라이프사이클을 동기화 시켜준다.
        
        ListAdapter adapter = new SimpleCursorAdapter(this,			// List뷰에 올릴 어댑터 생성, 이 어댑터는 커서 항목을 받을 수 있게 만든다. 첫 파라미터는 context
                android.R.layout.simple_list_item_2, c, 			// 안드로이드에서 제공하는 2줄짜리 layout에 담을것이고, c는 커서객체
                new String[] {Phone.DISPLAY_NAME, Phone.NUMBER}, 	// DISPLAY_NAME, NUMBER 항목만 어댑터에 담을 것이다.
                new int[] {android.R.id.text1,android.R.id.text2}); // 담을 view 선택.
        setListAdapter(adapter);								    // 어댑터를 inflate시킨다.
    }
}