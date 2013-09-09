package andexam.ver4_1.c28_network;

import andexam.ver4_1.*;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DomParser2 extends Activity {
	TextView mResult;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.domparser);

		mResult = (TextView)findViewById(R.id.result);
	}

	public void mOnClick(View v) {
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
		"<order>" + 
		"<item Maker=\"Samsung\" Price=\"23000\">Mouse</item>" +
		"<item Maker=\"LG\" Price=\"12000\">KeyBoard</item>" +
		"<item Price=\"156000\" Maker=\"Western Digital\">HDD</item>" +
		"</order>";

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream istream = new ByteArrayInputStream(xml.getBytes("utf-8"));
			Document doc = builder.parse(istream);

			Element order = doc.getDocumentElement();
			NodeList items = order.getElementsByTagName("item");
			String Result = "";
			for (int i = 0; i < items.getLength();i++) {
				Node item = items.item(i);
				Node text = item.getFirstChild();
				String ItemName = text.getNodeValue();
				Result += ItemName + " : ";

				NamedNodeMap Attrs = item.getAttributes();
				for (int j = 0;j < Attrs.getLength(); j++) {
					Node attr = Attrs.item(j);
					Result += (attr.getNodeName() + " = " + attr.getNodeValue() + "  ");
				}
				Result += "\n";
			}
			mResult.setText("주문 목록\n" + Result);
		}
		catch (Exception e) {
			Toast.makeText(v.getContext(), e.getMessage(), 0).show();
		}
	}	
}