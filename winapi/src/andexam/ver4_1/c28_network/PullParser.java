package andexam.ver4_1.c28_network;

import andexam.ver4_1.*;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xmlpull.v1.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class PullParser extends Activity {
	TextView mResult;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.domparser);

		mResult = (TextView)findViewById(R.id.result);
	}

	public void mOnClick(View v) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
			"<order><item>Mouse</item></order>";
		boolean initem = false;
		String ItemName = "";

		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			parser.setInput(new StringReader(xml));
			
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.END_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals("item")) {
						initem = true;
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				case XmlPullParser.TEXT:
					if (initem) {
						ItemName = parser.getText();
						initem = false;
					}
					break;
				}
				eventType = parser.next();
			}
			mResult.setText("주문 항목 : " + ItemName);
		}
		catch (Exception e) {
			Toast.makeText(v.getContext(), e.getMessage(), 0).show();
		}
	}	
}