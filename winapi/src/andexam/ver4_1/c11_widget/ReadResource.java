package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.res.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class ReadResource extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readresource);

		Resources res = getResources();
		TextView text = (TextView)findViewById(R.id.text);

		String str = res.getString(R.string.textstr);
		text.setText(str);
		// 리소스 ID를 전달해도 잘 동작한다.
		//text.setText(R.string.textstr);
		int textcolor = res.getColor(R.color.textcolor);
		text.setTextColor(textcolor);
		float textsize = res.getDimension(R.dimen.textsize);
		text.setTextSize(TypedValue.COMPLEX_UNIT_PX, textsize);
		// 두 호출문은 제대로 동작하지 않음
		//text.setTextColor(R.color.textcolor);
		//text.setTextSize(R.dimen.textsize);
	}
}