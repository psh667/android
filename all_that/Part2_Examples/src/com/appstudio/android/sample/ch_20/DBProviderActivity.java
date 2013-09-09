package com.appstudio.android.sample.ch_20;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class DBProviderActivity extends Activity {

	private EditText et;
	  private ListView lv;
	  private ArrayList<String> listArray;
	  private ArrayAdapter<String> adapter;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.atv_db_provider);
	    
	    et = (EditText)findViewById(R.id.et_input);
	    lv = (ListView)findViewById(R.id.list);
	    
	  }
	  
	  
	   public void btn_click(View v){
	    if(v.getId() == R.id.btn_insert){
	        
	      if(et.getText().toString().length()>0){
	        ContentValues values = new ContentValues();
	        values.put(DBManager.DESC, et.getText().toString());
	        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
	        Toast.makeText(DBProviderActivity.this, "Item Added " + uri.toString(),Toast.LENGTH_SHORT).show();  
	      }else{
	        Toast.makeText(DBProviderActivity.this, "please insert content",Toast.LENGTH_SHORT).show();
	        et.setFocusable(true);
	      }
	        
	    }else if(v.getId() == R.id.btn_select){
	      Toast.makeText(DBProviderActivity.this, "btn_select",Toast.LENGTH_SHORT).show();
	      String resultStr = "";
	      listArray = new ArrayList<String>();
	      ContentResolver cr = getContentResolver();
	      Cursor c = cr.query(MyContentProvider.CONTENT_URI, null, null, null, "");
	      if (c.moveToFirst()) {
	        do {
	          resultStr = c.getString(c.getColumnIndex(DBManager.DESC));
	          listArray.add(resultStr);
	        } while (c.moveToNext());
	        
	      }
	      adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listArray);
	      lv.setAdapter(adapter);
	      Toast.makeText(DBProviderActivity.this, "Item selected " +listArray.size(),Toast.LENGTH_SHORT).show();
	      
	    }else if(v.getId() == R.id.btn_delete){
	      
	      String mSelectionClause = DBManager.DESC+ "= ?";
	      String[] mSelectionArgs = {listArray.get(0)};
	      int rtn = getContentResolver().delete(MyContentProvider.CONTENT_URI, mSelectionClause, mSelectionArgs);
	      Toast.makeText(DBProviderActivity.this, "Item deleted " + rtn,Toast.LENGTH_SHORT).show();
	      
	    }else if(v.getId() == R.id.btn_update){
	      ContentValues values = new ContentValues();
	      values.put(DBManager.DESC, et.getText().toString());
	      String mSelectionClause = DBManager.DESC+ "= ?";
	      String[] mSelectionArgs = {listArray.get(0)};
	      int rtn = getContentResolver().update(MyContentProvider.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
	      Toast.makeText(DBProviderActivity.this, "Item updated " + rtn,Toast.LENGTH_SHORT).show();
	    }
	   }


}
