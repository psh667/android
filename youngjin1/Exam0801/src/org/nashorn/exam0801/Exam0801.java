package org.nashorn.exam0801;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Exam0801 extends Activity {
	
	private String[] keywordList;
	private String[] incDecList;
	private String[] valueList;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        keywordList = new String[10];
        incDecList = new String[10];
        valueList = new String[10];
        
        new loadKeywordList().execute("");
    }
    
    private class loadKeywordList extends AsyncTask<String, Void, Void> {   
        private ProgressDialog Dialog = new ProgressDialog(Exam0801.this);
        private int keywordCount = 0;
        private int incDecCount = 0;
        private int valueCount = 0;
           
        protected void onPreExecute() {   
            Dialog.setMessage("실시간 급상승 검색어 로딩 중...");   
            Dialog.show();
        }   
  
        protected Void doInBackground(String... urls) {   
	    	String request = "http://openapi.naver.com/search?key=기본키&target=rank&query=nexearch";
	        String resultString = "";
	
	        HttpClient client = new HttpClient();
	        GetMethod method = new GetMethod(request);
	        
	        try {
	            // Send GET request
	            int statusCode = client.executeMethod(method);
	
	            if (statusCode != HttpStatus.SC_OK) {
	                System.err.println("Method failed: " + method.getStatusLine());
	            }
	            InputStream rstream = null;
	
	            // Get the response body
	            rstream = method.getResponseBodyAsStream();
	
	            // Process the response from Yahoo! Web Services
	            BufferedReader br = new BufferedReader(new InputStreamReader(rstream));
	
	            String line;
	            while ((line = br.readLine()) != null) {
	                resultString += line;
	            }
	            br.close();
	        } catch (Exception e) { }
	
	        // XmlPullParser 구문 시작
	        try {
	            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	            factory.setNamespaceAware(true);
	            XmlPullParser xpp = factory.newPullParser();
	
	            xpp.setInput(new StringReader(resultString));
	            int eventType = xpp.getEventType();
	            while (eventType != XmlPullParser.END_DOCUMENT) {
	                if (eventType == XmlPullParser.START_DOCUMENT) {
	                    System.out.println("Start document");
	                } else if (eventType == XmlPullParser.END_DOCUMENT) {
	                    System.out.println("End document");
	                }else if (eventType == XmlPullParser.START_TAG) {
	                    if (xpp.getName().equals("K")) {
	                    	keywordList[keywordCount++] = xpp.nextText();
	                    }else if (xpp.getName().equals("S")) {
	                    	incDecList[incDecCount++] = xpp.nextText();
	                    }else if (xpp.getName().equals("V")) {
	                    	valueList[valueCount++] = xpp.nextText();
	                    }
	                } else if (eventType == XmlPullParser.END_TAG) {
	                    System.out.println("End tag " + xpp.getName());
	                } else if (eventType == XmlPullParser.TEXT) { }
	                eventType = xpp.next();
	            }
	        } catch (Exception e) { }

            return null;   
        }   
           
        protected void onPostExecute(Void unused) {   
            Dialog.dismiss();   
            
            ListView list = (ListView)findViewById(R.id.list);
            
            IconicAdapter iAdapter = new IconicAdapter(Exam0801.this);
        	list.setAdapter(iAdapter);             
        }   
           
    }
    
    class IconicAdapter extends ArrayAdapter {
		Activity context;
		IconicAdapter(Activity context)
		{
			super(context, R.layout.list_item, keywordList);
			this.context = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.list_item, null);
			
			TextView numText = (TextView)row.findViewById(R.id.list_txt_num);
			numText.setText(String.valueOf(position+1));
			
			TextView keywordText = (TextView)row.findViewById(R.id.list_txt_keyword);
			keywordText.setText(keywordList[position]);
			
			TextView incDecText = (TextView)row.findViewById(R.id.list_txt_incdec);
			incDecText.setText(incDecList[position]);
			
			TextView valueText = (TextView)row.findViewById(R.id.list_txt_value);
			if (incDecList[position].equals("new"))
			{
				valueText.setText("");
			}
			else
				valueText.setText(valueList[position]);
			
			return row;
		}
	}
}