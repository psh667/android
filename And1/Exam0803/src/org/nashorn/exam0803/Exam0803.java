package org.nashorn.exam0803;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TagFindingVisitor;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

public class Exam0803 extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        new loadWikipediaPage().execute("");
    }
    
    private class loadWikipediaPage extends AsyncTask<String, Void, Void> {   

        private ProgressDialog Dialog = new ProgressDialog(Exam0803.this);   
        private String htmlString = "";
           
        protected void onPreExecute() {   
            Dialog.setMessage("위키백과 페이지를 로딩 중입니다...");   
            Dialog.show();   
        }   
  
        protected Void doInBackground(String... urls) {   
            Parser parser = null; 
            try { 
            	String wikiUrl = "http://ko.wikipedia.org/wiki/%EC%84%9C%EC%9A%B8";
            	
                parser = new Parser (wikiUrl); 
                    String tags[] = { "TABLE" }; 
                    TagFindingVisitor visitor = new TagFindingVisitor(tags); 
                    try { 

                     parser.visitAllNodesWith (visitor); 
    	                 for (int i = 0;i<visitor.getTags(0).length;i++) {
    	                	 String textString = visitor.getTags(0)[i].getText();
    	                	 if (textString.equals("table class=\"infobox\""))
    	                	 {
    	                		 htmlString = 
    	                			 "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"ko\" dir=\"ltr\">"+
    	                			 "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head>"+
    	                			 "<body>"+visitor.getTags(0)[i].toHtml()+"</body></html>";

    	                		 break;
    	                	 }
    	                 }
                     
                 } catch (ParserException e) { 
                     //Toast.makeText(getBaseContext(),  e.toString(), Toast.LENGTH_SHORT).show();
                 } 
            } catch (ParserException e1) { 
            	//Toast.makeText(getBaseContext(),  e1.toString(), Toast.LENGTH_SHORT).show();
            } 
               
            return null;   
        }   
           
        protected void onPostExecute(Void unused) {   
            Dialog.dismiss();   
            
            WebView browser = (WebView)findViewById(R.id.webkit);
            browser.loadData(htmlString, "text/html", "UTF-8");
        }   
    }
}