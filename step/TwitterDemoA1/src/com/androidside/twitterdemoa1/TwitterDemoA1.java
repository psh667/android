package com.androidside.twitterdemoa1;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterDemoA1 extends Activity implements View.OnClickListener {    
    EditText idEdit;
    EditText pwEdit;
    TextView text;
    Button viewButton;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        idEdit = (EditText) findViewById(R.id.id);
        pwEdit = (EditText) findViewById(R.id.pw);
        text = (TextView) findViewById(R.id.text);
        
        viewButton = (Button) findViewById(R.id.view);
        viewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String id = idEdit.getText().toString();
        String pw = pwEdit.getText().toString();
        
        Twitter twitter = new TwitterFactory().getInstance(id, pw);
                    
        try {
            twitter.verifyCredentials();
        } catch (TwitterException e1) {
            //로그인 정보가 올바르지 않은 경우, TwitterException.getStatusCode() == 401
            Toast.makeText(this, "로그인 정보가 올바르지 않습니다!!", Toast.LENGTH_LONG).show();
            return;
        }
        
        List<Status> statuses = null;
        
        try {
            //사용자 타임라인 얻기
            statuses = twitter.getHomeTimeline();            
        } catch (TwitterException e) {
            e.printStackTrace();
        } 
        
        //타임라인을 화면에 보여주기
        StringBuffer sb = new StringBuffer();
        
        for (Status status : statuses) {
            sb.append(status.getId() + " " + status.getUser().getName() + ":" + status.getText()+"\n\n");            
        }
        
        text.setText(sb.toString());
        
    }
    
}