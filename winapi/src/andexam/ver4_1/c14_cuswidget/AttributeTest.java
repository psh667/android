package andexam.ver4_1.c14_cuswidget;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.widget.*;

public class AttributeTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attributetest);
		
		AttrButton btn = (AttrButton)findViewById(R.id.attrbtn);
		TextView text = (TextView)findViewById(R.id.attrtext);
		text.setText(btn.mText);
	}
}

class AttrButton extends Button {
	String mText = "";
	
	public AttrButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		int i;
		String Name;
		String Value;
		for (i=0;i<attrs.getAttributeCount();i++) {
			Name = attrs.getAttributeName(i);
			Value = attrs.getAttributeValue(i);
			mText += (Name + " = " + Value + "\n");
		}
		
		//* 아래 ??? 자리에 구하고자 하는 속성의 id 배열을 전달해야 하는데 표준 위젯은 속성이 숨겨져 있다.
		// TypedArray ar = context.obtainStyledAttributes(attrs, android.R.styleable.TextView);
		//*/
	}
}