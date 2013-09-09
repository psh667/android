package com.paad.contactpicker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactPicker extends Activity {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);
        
        Intent intent = getIntent();
        String dataPath = intent.getData().toString();

	    final Uri data = Uri.parse(dataPath + "people/");
	    final Cursor c = managedQuery(data, null, null, null, null);

	    String[] from = new String[] {People.NAME};
	    int[] to = new int[] { R.id.itemTextView };

	    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                                                              R.layout.listitemlayout,
                                                              c,
                                                              from,
                                                              to);
	    ListView lv = (ListView)findViewById(R.id.contactListView);
	    lv.setAdapter(adapter);
	    lv.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
	            // 선택된 아이템으로 커서를 이동한다.
	            c.moveToPosition(pos);
	            // 행 ID를 얻어온다.
	            int rowId = c.getInt(c.getColumnIndexOrThrow("_id"));
	            // 결과 URI를 구성한다.
	            Uri outURI = Uri.parse(data.toString() + rowId);
	            Intent outData = new Intent();
	            outData.setData(outURI);
	            setResult(Activity.RESULT_OK, outData);
	            finish();
	        }
	    });

    }
}