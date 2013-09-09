package com.androidside.twitterdemoa4;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import android.app.Activity;
import android.app.ListActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TwitterDemoA4 extends ListActivity implements View.OnClickListener {    
    EditText idEdit;
    EditText pwEdit;
    TextView text;
    Button viewButton;
     
    List<Status> statuses = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        idEdit = (EditText) findViewById(R.id.id);
        pwEdit = (EditText) findViewById(R.id.pw);
        
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
        
        try {
            //사용자 타임라인 얻기
            statuses = twitter.getHomeTimeline();
            Log.d("tag", "statuses size : " + statuses.size());
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        
        //리스트뷰에 타임라인 설정하기
        setListAdapter(new TwitterAdapter(this));        
    }

    class TwitterAdapter extends BaseAdapter {        
        Activity activity;
        LayoutInflater inflater;

        TwitterAdapter(Activity activity) {
            super();
            this.activity = activity;
            
            inflater = activity.getLayoutInflater();
        }

        public View getView(int position, View convertView, ViewGroup parent) {            
            View row = inflater.inflate(R.layout.list_row, null);

            TextView textView = (TextView) row.findViewById(R.id.label);
            textView.setText(getItem(position).getText());
            
            //프로파일 이미지 얻기
            URL url = getItem(position).getUser().getProfileImageURL();
            
            Bitmap icon = null;
            try {
                icon = BitmapFactory.decodeStream(url.openStream());
            } catch (IOException e) {
                e.printStackTrace();
            }  

            ImageView imageView = (ImageView) row.findViewById(R.id.icon);
            imageView.setImageBitmap(icon);
            
            return row;
        }

        @Override
        public int getCount() {            
            return statuses.size();
        }

        @Override
        public Status getItem(int position) {
            return statuses.get(position);
        }

        @Override
        public long getItemId(int position) {            
            return 0;
        }
    }
}