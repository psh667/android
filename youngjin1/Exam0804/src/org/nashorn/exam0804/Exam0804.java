package org.nashorn.exam0804;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import twitter4j.Twitter;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Exam0804 extends Activity {
	private ArrayList<String> twitList;
        
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        twitList = new ArrayList<String>();
        
        try
		{
			String id = "아이디";
			String password = "패스워드";
			
			Twitter twitter = new TwitterFactory().getInstance(id, password);
			
			List<Status> statuses = null;
		    try {
		        statuses = twitter.getFriendsTimeline();
		
		    }catch (TwitterException e)
		    {
		        e.printStackTrace();
		    }
		    
	        for (Status status : statuses) 
	        {
	        	Date time = status.getCreatedAt();
	        	twitList.add(time.toString()+" - "+status.getUser().getName()+" : "+ status.getText());
	        }
		}catch (Exception e)
		{
			Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
		}
		
		ListView listView = (ListView)findViewById(R.id.list);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, twitList);

  		listView.setAdapter(arrayAdapter);	
    }
}