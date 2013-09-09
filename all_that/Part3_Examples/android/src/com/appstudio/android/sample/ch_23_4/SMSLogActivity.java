package com.appstudio.android.sample.ch_23_4;



import java.text.SimpleDateFormat;
import java.util.Date;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SMSLogActivity extends Activity {
    
    final static private String ADDRESS = "address";
    final static private String DATE = "date";
    final static private String BODY = "body";
    final static private String URI = "content://sms/inbox";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_log_activity);
        ContentResolver cr = getContentResolver();
        Uri uri = Uri.parse(URI);
        Cursor cursor = 
            cr.query(uri, null, null, null, DATE+" DESC");
        startManagingCursor(cursor);
        SMSLogAdapter adapter = 
            new SMSLogAdapter(this, cursor);
        ListView listView = 
            (ListView)findViewById(R.id.smsLogList);
        listView.setAdapter(adapter);
    }
    
    private class SMSLogAdapter extends CursorAdapter { 
        private SimpleDateFormat mFormatter = 
            new SimpleDateFormat("MM/dd HH:mm");
        
        public SMSLogAdapter(Context context, Cursor cursor)  {
            super(context, cursor);
        }

        @Override
        public void bindView(View view, 
                Context context, Cursor cursor) {
            int col = cursor.getColumnIndex(ADDRESS);  
            String value = cursor.getString(col);
            TextView text = 
                (TextView) view.findViewById(R.id.address);
            text.setText(value);  
            
            col = cursor.getColumnIndex(DATE);  
            long lDate = cursor.getLong(col);
            String sDate = mFormatter.format(new Date(lDate));
            text = (TextView) view.findViewById(R.id.date);
            text.setText(sDate);  
                      
            col = cursor.getColumnIndex(BODY);  
            value = cursor.getString(col);
            text = (TextView) view.findViewById(R.id.body);
            text.setText(value);  
        }

        @Override
        public View newView(Context context, 
                Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = 
                LayoutInflater.from(context);  
            View v = inflater.inflate(R.layout.sms_log_row, 
                    parent, false);  
            return v;  
        } 
    }    
}