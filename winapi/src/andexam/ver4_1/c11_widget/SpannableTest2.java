package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.text.*;
import android.text.method.*;
import android.text.style.*;
import android.view.*;
import android.widget.*;

public class SpannableTest2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spannabletest);

		EditText Edit = (EditText)findViewById(R.id.edit);
		Spannable espan = Edit.getText(); 
		espan.setSpan(new StyleSpan(Typeface.ITALIC), 1, 7, 
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		espan.setSpan(new BackgroundColorSpan(0xffff0000), 8, 11, 
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		espan.setSpan(new UnderlineSpan(), 12, 17, 
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

		TextView Text = (TextView)findViewById(R.id.text);
		Spannable tspan = (Spannable)Text.getText();
		tspan.setSpan(new RelativeSizeSpan(0.5f), 0, 5, 
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tspan.setSpan(new ForegroundColorSpan(0xff0000ff), 5, 9, 
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		tspan.setSpan(new RelativeSizeSpan(1.5f), 9, 12, 
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		TextView link = (TextView)findViewById(R.id.textlink);
		Spannable lspan = (Spannable)link.getText();

		URLSpan profile = new URLSpan("") {
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "이 사람의 프로필을 검색한다.", 0).show();
			}
		};
		lspan.setSpan(profile, 10, 13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		URLSpan call = new URLSpan("") {
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "이 사람의 연락처를 찾는다.", 0).show();
			}
		};
		lspan.setSpan(call, 18, 21,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		link.setMovementMethod(LinkMovementMethod.getInstance());		
	}
}