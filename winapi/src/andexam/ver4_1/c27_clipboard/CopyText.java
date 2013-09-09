package andexam.ver4_1.c27_clipboard;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CopyText extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.copytext);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btncopy:
			copyText();
			break;
		case R.id.btnpaste:
			pasteText();
			break;
		}
	}

	void copyText() {
		EditText copyedit = (EditText)findViewById(R.id.copyedit);
		String text = copyedit.getText().toString();
		if (text.length() != 0) {
			ClipData clip = ClipData.newPlainText("text", text);
			ClipboardManager cm = (ClipboardManager)
					getSystemService(Context.CLIPBOARD_SERVICE);
			cm.setPrimaryClip(clip);
			Toast.makeText(this, "Text Copied", 0).show();
		}
	}

	void pasteText(){
		ClipboardManager cm = (ClipboardManager)
				getSystemService(Context.CLIPBOARD_SERVICE);
		if (cm.hasPrimaryClip() == false) {
			Toast.makeText(this, "Clipboard Empty", 0).show();
			return;
		}
		if (cm.getPrimaryClipDescription().hasMimeType(
				ClipDescription.MIMETYPE_TEXT_PLAIN) == false) {
			Toast.makeText(this, "Clip is not text", 0).show();
			return;
		}

		ClipData clip = cm.getPrimaryClip();
		ClipData.Item item  = clip.getItemAt(0);
		TextView pastetext = (TextView)findViewById(R.id.pastetext);
		pastetext.setText(item.getText());
	}
}
