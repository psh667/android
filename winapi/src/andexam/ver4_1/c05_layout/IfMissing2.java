package andexam.ver4_1.c05_layout;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class IfMissing2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ifmissing2);

		findViewById(R.id.btntoggle).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ImageView img = (ImageView)findViewById(R.id.icon);
				if (img.getVisibility() == View.VISIBLE) {
					img.setVisibility(View.GONE);
				} else {
					img.setVisibility(View.VISIBLE);
				}
			}
		});	
	}
}