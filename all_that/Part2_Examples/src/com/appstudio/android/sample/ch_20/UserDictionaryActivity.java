package com.appstudio.android.sample.ch_20;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.appstudio.android.sample.R;

public class UserDictionaryActivity extends Activity {

	private EditText wordName = null;
	private ListView lv = null;
	private long dicID = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dic_layout);
        
        init();
    }
    public void user_dic_click(View v) {
    	 if(v.getId() == R.id.btn_insert){
    		 insertDicData(wordName.getText().toString());
    	 }else if(v.getId() == R.id.btn_delete){
    		 deleteDicData();
    	 }else if(v.getId() == R.id.btn_update){
    		 updateDicData(wordName.getText().toString());
    	 }
    	 
    	 wordName.setText("");
		 dicID = 0;
		 setListView();
    }

    private void init(){
    	lv = (ListView)findViewById(R.id.listView);
    	wordName = (EditText)findViewById(R.id.et_word_name);
    	
    	lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView titleTv = (TextView)arg1.findViewById(R.id.tv_title);
				wordName.setText( titleTv.getText().toString() );
				
				TextView idTv = (TextView)arg1.findViewById(R.id.tv_id);
				dicID = Long.parseLong(idTv.getText().toString());
				
			}
		});
    	setListView();
    }
    private void deleteDicData(){
    	String mSelectionClause = UserDictionary.Words._ID + " = ?";
    	String[] mSelectionArgs = {String.valueOf(dicID)};

    	int mRowsDeleted = 0;
    	mRowsDeleted = getContentResolver().delete(UserDictionary.Words.CONTENT_URI, 
    	mSelectionClause,
    	mSelectionArgs
    	);

    }
    private void updateDicData(String strWord){
    	ContentValues mUpdateValues = new ContentValues();

    	String mSelectionClause = UserDictionary.Words._ID + " = ?";
    	String[] mSelectionArgs = {String.valueOf(dicID)};

    	int mRowsUpdated = 0;
    	mUpdateValues.put(UserDictionary.Words.WORD, strWord);

    	mRowsUpdated = getContentResolver().update(
    			UserDictionary.Words.CONTENT_URI, 
    			mUpdateValues,                           
    			mSelectionClause,                    
    			mSelectionArgs
    	);

    }
    private void insertDicData(String strWord){
    	ContentValues mNewValues = new ContentValues();

    	mNewValues.put(UserDictionary.Words.APP_ID, "example.user");
    	mNewValues.put(UserDictionary.Words.LOCALE, "en_US");
    	mNewValues.put(UserDictionary.Words.WORD, strWord);
    	mNewValues.put(UserDictionary.Words.FREQUENCY, "100");

    	Uri mNewUri = getContentResolver().insert(UserDictionary.Words.CONTENT_URI, mNewValues);
    	
    	String aaa = mNewUri.toString();

    }
    private void setListView(){
    	Cursor curs =  getUserDicData();
    	
    	String[] cols = new String[]{UserDictionary.Words.WORD,UserDictionary.Words._ID};
    	int[] fields = new int[]{R.id.tv_title, R.id.tv_id};
    	SimpleCursorAdapter adapter = new SimpleCursorAdapter(lv.getContext(), R.layout.udp_list_rows, curs, cols, fields,0);
    	lv.setAdapter(adapter);
    }
    
    private Cursor getUserDicData(){
    	String[] mProjection = { 
				UserDictionary.Words._ID,
				UserDictionary.Words.WORD, 
				UserDictionary.Words.LOCALE };

		String mSelectionClause = UserDictionary.Words.WORD + " = ?";
		String[] mSelectionArgs = { "abc" };

		Cursor mCursor = getContentResolver().query(
				UserDictionary.Words.CONTENT_URI, 
				mProjection,
				null, 
				null, 
				null);

		if (null == mCursor) {

		} else {
			while (mCursor.moveToNext()) {
				String id = mCursor.getString(0);
				String word = mCursor.getString(1);
				String locale = mCursor.getString(2);
			}
		}
		
		return mCursor;

    }

}
