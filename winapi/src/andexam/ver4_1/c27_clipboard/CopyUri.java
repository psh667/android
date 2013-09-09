package andexam.ver4_1.c27_clipboard;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CopyUri extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copyuri);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btncopy:
			copyUri();
			break;
		case R.id.btnpasteuri:
			pasteUri();
			break;
		case R.id.btnpastetext:
			pasteUriText();
			break;
		}
	}

	void copyUri() {
		ClipboardManager cm = (ClipboardManager)
				getSystemService(Context.CLIPBOARD_SERVICE);
		Uri copyuri = Uri.parse("content://andexam.EnglishWord/word/boy");
		ClipData clip = ClipData.newUri(getContentResolver(), "URI", copyuri);
		cm.setPrimaryClip(clip);
		Toast.makeText(this, "Uri Copied", 0).show();
	}

	void pasteUri(){
		ClipboardManager cm = (ClipboardManager)
				getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm.hasPrimaryClip() == false) {
			Toast.makeText(this, "Clipboard Empty", 0).show();
			return;
		}
		if (cm.getPrimaryClipDescription().hasMimeType(
				ClipDescription.MIMETYPE_TEXT_URILIST) == false) {
			Toast.makeText(this, "Clip is not uri", 0).show();
			return;
		}

		ClipData clip = cm.getPrimaryClip();
		ClipData.Item item  = clip.getItemAt(0);
		Uri pasteuri = item.getUri();
		ContentResolver cr = getContentResolver();
		String uriMime = cr.getType(pasteuri);
		if (uriMime == null || 
				uriMime.equals("vnd.EnglishWord.andexam.cursor.dir/word") == false) {
			Toast.makeText(this, "Clip is not EnglishWord", 0).show();
		}

		Cursor pastecursor = cr.query(pasteuri, null, null, null, null);
		if (pastecursor != null) {
			if (pastecursor.moveToFirst()) {
				TextView pastetext = (TextView)findViewById(R.id.pastetext1);
				pastetext.setText(pastecursor.getString(0) + ":" + pastecursor.getString(1));
			}
			pastecursor.close();
		} else {
			Toast.makeText(this, "Data not found", 0).show();
		}
	}

	void pasteUriText(){
		ClipboardManager cm = (ClipboardManager)
				getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm.hasPrimaryClip() == false) {
			Toast.makeText(this, "Clipboard Empty", 0).show();
			return;
		}

		ClipData clip = cm.getPrimaryClip();
		ClipData.Item item  = clip.getItemAt(0);
		TextView pastetext = (TextView)findViewById(R.id.pastetext2);
		pastetext.setText(item.coerceToText(this).toString());
	}
}
