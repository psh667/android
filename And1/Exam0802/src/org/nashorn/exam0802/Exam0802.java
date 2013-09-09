package org.nashorn.exam0802;

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
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Exam0802 extends Activity {
	private EditText keywordText;
	private EditText explainText;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        keywordText = (EditText)findViewById(R.id.keyword);
        explainText = (EditText)findViewById(R.id.explain);
        explainText.setFocusableInTouchMode(false);
        explainText.setGravity(Gravity.TOP);
        
        Button searchButton = (Button)findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (keywordText.getText().toString().length() > 0)
				{
					new loadKeywordExplain().execute("");
				}
				
				
			}
        });
    }
    
    private class loadKeywordExplain extends AsyncTask<String, Void, Void> {   
        private ProgressDialog Dialog = new ProgressDialog(Exam0802.this);
        private String titleString = "";
        private String descriptionString = "";
        private String linkString = "";
        private String originString = "";
           
        protected void onPreExecute() {   
            Dialog.setMessage("키워드 사전 검색 결과 로딩 중...");   
            Dialog.show();
        }   
  
        protected Void doInBackground(String... urls) {   
	    	String request = "http://apis.daum.net/dic/krdic?apikey=기본키&q="
	    		+keywordText.getText().toString()+"&kind=WORD";
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
	                    if (xpp.getName().equals("title")) {
	                    	String text = xpp.nextText();
	                    	if (text.equals("Search Daum Open API"))
	                    	{
	                    		//nothing
	                    	}
	                    	else
	                    	{
	                    		if (titleString.length() == 0)
	                    		{
	                    			titleString = text;
	                    		}
	                    	}
	                    }else if (xpp.getName().equals("description")) {
	                    	if (descriptionString.length() == 0)
                    		{
	                    		descriptionString = xpp.nextText();
                    		}
	                    }else if (xpp.getName().equals("link")) {
	                    	String text = xpp.nextText();
	                    	if (text.equals("http://dna.daum.net/apis"))
	                    	{
	                    		//nothing
	                    	}
	                    	else
	                    	{
		                    	if (linkString.length() == 0)
	                    		{
		                    		linkString = text;
	                    		}
	                    	}
	                    }else if (xpp.getName().equals("origin")) {
	                    	if (originString.length() == 0)
                    		{
	                    		originString = xpp.nextText();
                    		}
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

            String resultString = titleString+" ("+originString+")\n"+descriptionString+"\n"+
            	linkString;
            explainText.setText(resultString);
        }   
           
    }
}