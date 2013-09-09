package com.androidside.xmlpullparserdemoa1;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class XMLPullParserDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ArrayList<String> titleList = getXmlData();
        
        TextView text = (TextView) findViewById(R.id.text);
        
        StringBuffer sb = new StringBuffer();
        
        for (int i = 0; i < titleList.size(); i++) {
            sb.append(titleList.get(i)).append("\n\n");
        }
        
        text.setText(sb.toString());
    }
    
    public ArrayList<String> getXmlData() {
        String rss = "http://www.androidside.com/book/data.xml";
        
        ArrayList<String> titleList = new ArrayList<String>();

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();            

            URL url = new URL(rss);
            InputStream is = url.openStream();
            xpp.setInput(is, "euc-kr");

            int eventType = xpp.getEventType();
            
            while (eventType != XmlPullParser.END_DOCUMENT) {                
                if (eventType == XmlPullParser.START_TAG) {
                    if (xpp.getName().equals("title")) {
                        String title = xpp.nextText();
                        titleList.add(title);
                    }
                }
                eventType = xpp.next();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return titleList;
    }
}