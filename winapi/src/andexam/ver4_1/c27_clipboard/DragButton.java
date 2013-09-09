package andexam.ver4_1.c27_clipboard;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DragButton extends Activity {
	Button btnSource;
	Button btnTarget;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dragbutton);

		btnSource = (Button)findViewById(R.id.source);
		btnSource.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ClipData clip = ClipData.newPlainText("dragtext", "dragtext");
				v.startDrag(clip, new View.DragShadowBuilder(v), null, 0);
				return false;
			}
		});

		Button btnSource2 = (Button)findViewById(R.id.source2);
		btnSource2.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View v) {
				ClipData clip = ClipData.newRawUri("uri", 
						Uri.parse("content://exam.andexam.EnglishWord/word/boy"));
				v.startDrag(clip, new View.DragShadowBuilder(v), null, 0);
				return false;
			}
		});

		btnTarget = (Button)findViewById(R.id.target);
		btnTarget.setOnDragListener(mDragListener);
	}

	View.OnDragListener mDragListener = new View.OnDragListener() {
		public boolean onDrag(View v, DragEvent event) {
			Button btn;
			if (v instanceof Button) {
				btn = (Button)v;
			} else {
				return false;
			}

			switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				if (event.getClipDescription().hasMimeType(
						ClipDescription.MIMETYPE_TEXT_PLAIN)) {
					btn.setText("Drop OK");
					return true;
				} else {
					return false;
				}
			case DragEvent.ACTION_DRAG_ENTERED:
				btn.setText("Enter");
				return true;
			case DragEvent.ACTION_DRAG_EXITED:
				btn.setText("Exit");
				return true;
			case DragEvent.ACTION_DROP:
				String text = event.getClipData().getItemAt(0).getText().toString();
				btn.setText(text);
				return true;
			case DragEvent.ACTION_DRAG_ENDED:
				if (event.getResult()) {
					Toast.makeText(DragButton.this, "Drag & Drop completed", 0).show();
				} else {
					btn.setText("Target");
				}
				return true;
			}
			return true;
		}
	};
}
