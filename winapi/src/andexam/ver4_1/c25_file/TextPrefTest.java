package andexam.ver4_1.c25_file;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;

public class TextPrefTest extends Activity {
	TextPref mPref;
	TextView textName;
	TextView textStNum;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textpreftest);

		textName = (TextView)findViewById(R.id.name);
		textStNum = (TextView)findViewById(R.id.stnum);
		
		try {
			mPref = new TextPref("/sdcard/textpref.pref");
		} catch (Exception e) {
			e.printStackTrace();
		}

		mPref.Ready();
		String Name = mPref.ReadString("Name", "이름없음");
		textName.setText(Name);
		int StNum = mPref.ReadInt("StNum",20101234);
		textStNum.setText("" + StNum);
		mPref.EndReady();
	}

	public void onPause() {
		super.onPause();

		String Name = textName.getText().toString();
		int StNum = 0;
		try {
			StNum = Integer.parseInt(textStNum.getText().toString());
		}
		catch (Exception e) {}

		/* 임의 순서대로 쓰기
		mPref.Ready();
		mPref.WriteString("Name", Name);
		mPref.WriteInt("StNum", StNum);
		mPref.CommitWrite();
		//*/

		//* 일괄 쓰기
		mPref.BulkWriteReady(1000);
		mPref.BulkWrite("Name", Name);
		mPref.BulkWrite("StNum", Integer.toString(StNum));
		mPref.CommitWrite();
		//*/
	}
}
