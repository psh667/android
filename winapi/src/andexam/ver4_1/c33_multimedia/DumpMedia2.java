package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.MediaStore.*;
import android.view.*;
import android.widget.*;

public class DumpMedia2 extends Activity {
	ContentResolver mCr;
	TextView mResult;
	RadioGroup mMedia;
	ToggleButton mStorage;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dumpmedia);
		mCr = getContentResolver();
		mResult = (TextView)findViewById(R.id.result);
		mStorage = (ToggleButton)findViewById(R.id.storage);
		mMedia = (RadioGroup)findViewById(R.id.media);
		
		mMedia.setOnCheckedChangeListener(new 
				RadioGroup.OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				dumpQuery();
			}			
		});
		mStorage.setOnCheckedChangeListener(new 
				CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, 
					boolean isChecked) {
				dumpQuery();
			}
		});

		dumpQuery();

		// 미디어 변화에 대한 BR을 등록한다.
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);
		filter.addAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		filter.addDataScheme("file");
		registerReceiver(mScanReceiver, filter);
	}
	
	// 미디어 변경시 목록을 갱신한다.
	BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			dumpQuery();
		}
	};

	// 종료시 BR을 해제한다.
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mScanReceiver);
	}
	
	void dumpQuery() {
		StringBuilder result = new StringBuilder();
		Uri uri;
		boolean instorage = mStorage.isChecked();
		
		// 미디어 종류와 메모리 위치로부터 URI 결정
		switch (mMedia.getCheckedRadioButtonId()) {
		case R.id.image:
			default:
			uri = instorage ? Images.Media.INTERNAL_CONTENT_URI:
				Images.Media.EXTERNAL_CONTENT_URI;
			break;
		case R.id.audio:
			uri = instorage ? Audio.Media.INTERNAL_CONTENT_URI:
				Audio.Media.EXTERNAL_CONTENT_URI;
			break;
		case R.id.video:
			uri = instorage ? Video.Media.INTERNAL_CONTENT_URI:
				Video.Media.EXTERNAL_CONTENT_URI;
			break;
		}
		Cursor cursor = mCr.query(uri, null, null, null, null);
		
		// 필드 목록 출력
		int nCount = cursor.getColumnCount();
		result.append("num colume = " + nCount + "\n\n");
		for (int i = 0; i < nCount; i++) {
			result.append(i);
			result.append(":" + cursor.getColumnName(i) + "\n");
		}

		result.append("\n======================\n\n");
		
		// 레코드 목록 출력
		result.append("num media = " + cursor.getCount() + "\n\n");
		int count = 0;
		while (cursor.moveToNext()) {
			result.append(getColumeValue(cursor, MediaColumns._ID));
			result.append(getColumeValue(cursor, MediaColumns.DISPLAY_NAME));
			result.append(getColumeValue(cursor, MediaColumns.TITLE));
			result.append(getColumeValue(cursor, MediaColumns.SIZE));
			result.append(getColumeValue(cursor, MediaColumns.DATE_ADDED));
			result.append(getColumeValue(cursor, MediaColumns.MIME_TYPE));

			switch (mMedia.getCheckedRadioButtonId()) {
			case R.id.image:
				result.append(getColumeValue(cursor, Images.ImageColumns.DATE_TAKEN));
				result.append(getColumeValue(cursor, Images.ImageColumns.DESCRIPTION));
				result.append(getColumeValue(cursor, Images.ImageColumns.ORIENTATION));
				result.append(getColumeValue(cursor, Images.ImageColumns.LATITUDE));
				break;
			case R.id.audio:
				result.append(getColumeValue(cursor, Audio.AudioColumns.ALBUM));
				result.append(getColumeValue(cursor, Audio.AudioColumns.ARTIST));
				result.append(getColumeValue(cursor, Audio.AudioColumns.YEAR));
				result.append(getColumeValue(cursor, Audio.AudioColumns.DURATION));
				break;
			case R.id.video:
				result.append(getColumeValue(cursor, Video.VideoColumns.DURATION));
				result.append(getColumeValue(cursor, Video.VideoColumns.RESOLUTION));
				break;
			}
			result.append("\n");
			count++;
			if (count == 32) break;
		}
		cursor.close();

		mResult.setText(result.toString());
	}
	
	String getColumeValue(Cursor cursor, String cname) {
		String value = cname + " : " +
			cursor.getString(cursor.getColumnIndex(cname)) + "\n";
		return value;
	}
}
