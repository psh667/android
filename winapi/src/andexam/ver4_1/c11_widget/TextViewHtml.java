package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.drawable.*;
import android.os.*;
import android.text.*;
import android.widget.*;

public class TextViewHtml extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textviewhtml);
		
		TextView text = (TextView)findViewById(R.id.text);
		text.setText(Html.fromHtml(
			"This <b>text</b> is <i>spanned</i> from " + 
			"<u>html</u> <font color='#ff0000'>" + 
			"document</font>"));

		TextView img = (TextView)findViewById(R.id.image);
		img.setText(Html.fromHtml(
			"This is a androboy <img src=\"androboy\"/> image.", 
			new ImageGetter(), null));
	}
	
	public class ImageGetter implements Html.ImageGetter {
		public Drawable getDrawable(String source) {
			int id = 0;
			
			if (source.equals("androboy")) {
				id = R.drawable.androboy;
			}
			
			if (id != 0) {
				Drawable drawable = getResources().getDrawable(id);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), 
						drawable.getIntrinsicHeight());
				return drawable;
				
			}
			return null;
		}
	}
}