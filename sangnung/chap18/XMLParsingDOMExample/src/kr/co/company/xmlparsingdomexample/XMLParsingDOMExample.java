package kr.co.company.xmlparsingdomexample;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class XMLParsingDOMExample extends Activity {
	TextView textview;
	Document doc = null;
	LinearLayout layout = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		textview = (TextView) findViewById(R.id.textView1);
	}

	public void onClick(View view) {
		GetXMLTask task = new GetXMLTask(this);
		task.execute("http://www.kma.go.kr/wid/queryDFS.jsp?gridx=61&gridy=125");
	}

	// private inner class extending AsyncTask
	private class GetXMLTask extends AsyncTask<String, Void, Document> {
		private Activity context;

		public GetXMLTask(Activity context) {
			this.context = context;
		}

		@Override
		protected Document doInBackground(String... urls) {
			URL url;
			try {
				url = new URL(urls[0]);
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db;
				db = dbf.newDocumentBuilder();
				doc = db.parse(new InputSource(url.openStream()));
				doc.getDocumentElement().normalize();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(),"Parsing Error", Toast.LENGTH_SHORT).show();
			}
			return doc;
		}

		@Override
		protected void onPostExecute(Document doc) {
			String s="";
			NodeList nodeList = doc.getElementsByTagName("data");
			for (int i = 0; i < nodeList.getLength(); i++) {
				s += ""+i +": ³¯¾¾ Á¤º¸: ";
				Node node = nodeList.item(i);
				Element fstElmnt = (Element) node;
				NodeList nameList = fstElmnt.getElementsByTagName("temp");
				Element nameElement = (Element) nameList.item(0);
				nameList = nameElement.getChildNodes();
				s += "¿Âµµ = "+ ((Node) nameList.item(0)).getNodeValue() +" ,";

				NodeList websiteList = fstElmnt.getElementsByTagName("wfKor");
				Element websiteElement = (Element) websiteList.item(0);
				websiteList = websiteElement.getChildNodes();
				s += "³¯¾¾ = "+ ((Node) websiteList.item(0)).getNodeValue() +"\n";
			}
			textview.setText(s);
		}

	}
}
