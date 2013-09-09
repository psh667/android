package com.appstudio.android.sample.ch_33_2;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class TwitterTestActivity extends Activity {
    private String LOG_TAG = "TAG";
    
    private String accessToken;
    private String accessTokenSecret;
    private Intent dataForTask;
    private Twitter mTwitter;
    private RequestToken mRqToken;
    private AccessToken mAccessToken;
    private String doingStatus;
    
    private TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_test_activity);
        init();
    }
    
    public void onClick(View v){
        if(v.getId( ) == R.id.login){
            twitter_login( );
        }else if(v.getId( ) == R.id.post){
            twitter_write( );
        }else if(v.getId( ) == R.id.logout){
            twitter_logout();
        }
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if(resultCode == RESULT_OK) 
        {
          if (requestCode == Constant.TWITTER_LOGIN_CODE)
          {
            try
            {
                tv.setText( "already login" );
                dataForTask= data;
                new AddStringTask().execute(Constant.ACCESS);
            }
            catch (Exception e)
            {
              Toast.makeText( this, "network error occur", Toast.LENGTH_LONG ).show( );
              Log.i(LOG_TAG,e.getMessage( ) );
            }
          }
        }
    }
    
    
    
    
    
    private void init(){
        
        tv = (TextView)findViewById( R.id.status );
        
        accessToken = getAppPreferences(this, Constant.ACCESS_TOKEN);
        accessTokenSecret = getAppPreferences(this, Constant.ACCESS_TOKEN_SECRET);
        
        if (accessToken != null && !"".equals(accessToken) && accessTokenSecret != null && !"".equals(accessTokenSecret))
        {
            mAccessToken = new AccessToken(accessToken, accessTokenSecret);
            tv.setText( "already login" );
        }
    }
    
    
    
    
    
    
    public void setAppPreferences(Activity context, String key, String value)
    {
      SharedPreferences pref = null;
      pref = context.getSharedPreferences(Constant.PREFER_NAME, 0);
      SharedPreferences.Editor prefEditor = pref.edit();
      prefEditor.putString(key, value);
      
      prefEditor.commit();
    }
     
    public String getAppPreferences(Activity context, String key)
    {
      String returnValue = null;
      
      SharedPreferences pref = null;
      pref = context.getSharedPreferences(Constant.PREFER_NAME, 0);
      
      returnValue = pref.getString(key, "");
      
      return returnValue;
    }
    
    
    
    private void twitter_login()
    {
        try
        {
            if (accessToken != null && !"".equals(accessToken) && accessTokenSecret != null && !"".equals(accessTokenSecret))
            {
                mAccessToken = new AccessToken(accessToken, accessTokenSecret);
            }
            else
            {
                  ConfigurationBuilder cb = new ConfigurationBuilder();
                  cb.setDebugEnabled(true);
                  cb.setOAuthConsumerKey(Constant.CONSUMER_KEY);
                  cb.setOAuthConsumerSecret(Constant.CONSUMER_SECRET);
                  TwitterFactory factory = new TwitterFactory(cb.build());
                  mTwitter = factory.getInstance();
                 
                  new AddStringTask().execute(Constant.REQUEST,null,null); 
            }
        }
        catch (Exception e)
        {
            Toast.makeText( this, "network error occur", Toast.LENGTH_LONG ).show( );
            Log.i(LOG_TAG,e.getMessage( ) );
        }
    }
    
    private void twitter_logout(){
        tv.setText( "not login" );        
        
        accessToken = "";
        accessTokenSecret="";
        setAppPreferences(this, Constant.ACCESS_TOKEN , "");
        setAppPreferences(this, Constant.ACCESS_TOKEN_SECRET, "");
        
        Intent intent = new Intent(this, TwitterTestLoginActivity.class);
        intent.putExtra(Constant.AUTH_URL, Constant.TWITTER_LOGOUT_URL);
        startActivity(intent);
    }
    private void twitter_write(){
        try{
            
          ConfigurationBuilder cb = new ConfigurationBuilder();
          String oAuthAccessToken = mAccessToken.getToken();
          String oAuthAccessTokenSecret = mAccessToken.getTokenSecret();
          String oAuthConsumerKey = Constant.CONSUMER_KEY;
          String oAuthConsumerSecret = Constant.CONSUMER_SECRET;
          cb.setOAuthAccessToken(oAuthAccessToken);
          cb.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
          cb.setOAuthConsumerKey(oAuthConsumerKey);
          cb.setOAuthConsumerSecret(oAuthConsumerSecret);
          Configuration config = cb.build();
         
          TwitterFactory tFactory = new TwitterFactory(config);
          mTwitter = tFactory.getInstance();
          
          Toast.makeText( this, "posting", Toast.LENGTH_SHORT ).show();
          new AddStringTask().execute(Constant.UPDATE,null,null);
        }
        catch (Exception e)
        {
            Toast.makeText( this, "network error occur", Toast.LENGTH_LONG ).show( );
            Log.i(LOG_TAG,e.getMessage( ) );
        }
        
      }
    
    class AddStringTask extends AsyncTask<String, Void, Void>{
        @Override
        protected Void doInBackground(String... inValue){
            try
            {
                if(inValue[0].equals( Constant.REQUEST )){
                    mRqToken = mTwitter.getOAuthRequestToken(Constant.CALLBACK_URL);
                    doingStatus = inValue[0];
                }else if ( inValue[0].equals(Constant.UPDATE )){
                    mTwitter.updateStatus("this mention is test.");
                }else if(inValue[0].equals( Constant.ACCESS )){
                    doingStatus = inValue[0];
                    mAccessToken = mTwitter.getOAuthAccessToken(mRqToken, dataForTask.getStringExtra("oauth_verifier"));
                }
            }
            catch ( TwitterException e )
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute( Void result )
        {
            super.onPostExecute( result );
            if(doingStatus.equals( Constant.REQUEST )){
                sendLoginRequest();
            }else if(doingStatus.equals(Constant.ACCESS )){
                saveAccessToken();
            }else {
               
            }
        }
    }
    
    public void saveAccessToken(){
        setAppPreferences(this, Constant.ACCESS_TOKEN, mAccessToken.getToken());
        setAppPreferences(this, Constant.ACCESS_TOKEN_SECRET, mAccessToken.getTokenSecret());
    }
    
    public void sendLoginRequest(){
        Intent intent = new Intent(this, TwitterTestLoginActivity.class);
        intent.putExtra(Constant.AUTH_URL, mRqToken.getAuthorizationURL());
        startActivityForResult(intent, Constant.TWITTER_LOGIN_CODE);
        doingStatus = "";
    }
    
}