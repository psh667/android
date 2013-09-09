package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ImageButtonTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagebuttontest);

		ImageButton imgbtn = (ImageButton)findViewById(R.id.imagebtn);
		imgbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(ImageButtonTest.this, "Image Button Clicked", 
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}