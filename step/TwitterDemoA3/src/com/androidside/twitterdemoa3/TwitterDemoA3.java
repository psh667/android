package com.androidside.twitterdemoa3;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TwitterDemoA3 extends Activity implements View.OnClickListener {    
    EditText idEdit;
    EditText pwEdit;
    EditText receiverEdit;
    EditText messageEdit;
    Button viewButton;
    
    Twitter twitter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        idEdit = (EditText) findViewById(R.id.id);
        pwEdit = (EditText) findViewById(R.id.pw);
        receiverEdit = (EditText) findViewById(R.id.receiver);
        messageEdit = (EditText) findViewById(R.id.message);
        
        viewButton = (Button) findViewById(R.id.view);
        viewButton.setOnClickListener(this);
    }
    
    private boolean isLoginOK() {
        String id = idEdit.getText().toString();
        String pw = pwEdit.getText().toString();
        
        twitter = new TwitterFactory().getInstance(id, pw);
        
        try {
            twitter.verifyCredentials();
        } catch (TwitterException e1) {
            //로그인 정보가 올바르지 않은 경우, TwitterException.getStatusCode() == 401            
            return false;
        }
        
        return true;
    }

    @Override
    public void onClick(View v) {        
        String receiver = receiverEdit.getText().toString();
        String message = messageEdit.getText().toString();
        
        if (!isLoginOK()) {
            Toast.makeText(this, "로그인 정보가 올바르지 않습니다!!", Toast.LENGTH_LONG).show();
            return;
        }
        
        try {
            //메시지 보내기
            twitter.sendDirectMessage(receiver, message);
            Toast.makeText(this, receiver + "에게 메시지 전송이 성공하였습니다. - " + message, Toast.LENGTH_LONG).show();
        } catch (TwitterException e) {
            Toast.makeText(this, receiver + "에게 메시지 전송이 실패하였습니다. - " + message, Toast.LENGTH_LONG).show();
        }
    }
}