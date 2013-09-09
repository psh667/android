package andexam.ver4_1.c27_clipboard;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CopyIntent extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copyintent);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btncopy:
			copyIntent();
			break;
		case R.id.btnpaste:
			pasteIntent();
			break;
		}
	}

	void copyIntent() {
		ClipboardManager cm = (ClipboardManager)
				getSystemService(Context.CLIPBOARD_SERVICE);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
		ClipData clip = ClipData.newIntent("intent", intent);
		cm.setPrimaryClip(clip);
		Toast.makeText(this, "Intent Copied", 0).show();
	}

	void pasteIntent(){
		ClipboardManager cm = (ClipboardManager)
				getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm.hasPrimaryClip() == false) {
			Toast.makeText(this, "Clipboard Empty", 0).show();
			return;
		}
		if (cm.getPrimaryClipDescription().hasMimeType(
				ClipDescription.MIMETYPE_TEXT_INTENT) == false) {
			Toast.makeText(this, "Clip is not intent", 0).show();
			return;
		}

		ClipData clip = cm.getPrimaryClip();
		ClipData.Item item  = clip.getItemAt(0);
		Intent intent = item.getIntent();
		if (intent != null) {
			startActivity(intent);
		}
	}
}
