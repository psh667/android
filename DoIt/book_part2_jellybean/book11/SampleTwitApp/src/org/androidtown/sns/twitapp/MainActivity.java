package org.androidtown.sns.twitapp;

import java.util.Date;
import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 트위터 연동 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";

	TextView nameText;
	Button connectBtn;
	StatusListView statusList;
	StatusListAdapter statusAdapter;

	Button writeBtn;
	EditText writeInput;
	
	Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBtn = (Button) findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		connect();
        	}
        });

        nameText = (TextView) findViewById(R.id.nameText);


        statusList = (StatusListView) findViewById(R.id.statusList);
        statusAdapter = new StatusListAdapter(this, mHandler);
        statusList.setAdapter(statusAdapter);
        statusList.setOnDataSelectionListener(new OnDataSelectionListener() {
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				Status curItem = (Status) statusAdapter.getItem(position);
				String curText = curItem.getText();

				Toast.makeText(getApplicationContext(), "Selected : " + curText, 1000).show();
			}
		});

        writeBtn = (Button) findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		String statusText = writeInput.getText().toString();
        		if (statusText.length() < 1) {
        			Toast.makeText(getApplicationContext(), "글을 입력하세요.", 1000).show();
        			return;
        		}

        		updateStatus(statusText);
        	}
        });

        writeInput = (EditText) findViewById(R.id.writeInput);

    }

    private void updateStatus(String statusText) {
    	
    	UpdateStatusThread thread = new UpdateStatusThread(statusText);
    	thread.start();
    
    }
    
    class UpdateStatusThread extends Thread {
    	String statusText;
    	
    	public UpdateStatusThread(String inText) {
    		statusText = inText;
    	}
    	
    	public void run() {
    		try {
        		Status status = BasicInfo.TwitInstance.updateStatus(statusText);
        		final Date curDate = status.getCreatedAt();

        		mHandler.post(new Runnable() {
        			public void run() {
        				Toast.makeText(getApplicationContext(), "글을 업데이트했습니다 : " + BasicInfo.DateFormat.format(curDate), Toast.LENGTH_SHORT).show();

                		showUserTimeline();
        			}
        		});
        		
        	} catch(Exception ex) {
        		ex.printStackTrace();
        	}

    	}
    }
    


    private void connect() {
		Log.d(TAG, "connect() called.");

		if (BasicInfo.TwitLogin) {
			Log.d(TAG, "twitter already logged in.");
			Toast.makeText(getBaseContext(), "twitter already logged in.", Toast.LENGTH_LONG).show();

			try {
				ConfigurationBuilder builder = new ConfigurationBuilder();

				builder.setOAuthAccessToken(BasicInfo.TWIT_KEY_TOKEN);
				builder.setOAuthAccessTokenSecret(BasicInfo.TWIT_KEY_TOKEN_SECRET);
				builder.setOAuthConsumerKey(BasicInfo.TWIT_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(BasicInfo.TWIT_CONSUMER_SECRET);

				Configuration config = builder.build();
				TwitterFactory tFactory = new TwitterFactory(config);
				BasicInfo.TwitInstance = tFactory.getInstance();

				Toast.makeText(getBaseContext(), "twitter connected.", Toast.LENGTH_LONG).show();

	    	} catch (Exception ex) {
				ex.printStackTrace();
			}

			showUserTimeline();

		} else {
			
			RequestTokenThread thread = new RequestTokenThread();
			thread.start();
			
		}

    }

    /**
     * RequestToken 요청 스레드
     */
    class RequestTokenThread extends Thread {
    	public void run() {

	    	try {
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setDebugEnabled(true);
				builder.setOAuthConsumerKey(BasicInfo.TWIT_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(BasicInfo.TWIT_CONSUMER_SECRET);

				TwitterFactory factory = new TwitterFactory(builder.build());
				Twitter mTwit = factory.getInstance();
				final RequestToken mRequestToken = mTwit.getOAuthRequestToken();
				String outToken = mRequestToken.getToken();
				String outTokenSecret = mRequestToken.getTokenSecret();

				Log.d(TAG, "Request Token : " + outToken + ", " + outTokenSecret);
				Log.d(TAG, "AuthorizationURL : " + mRequestToken.getAuthorizationURL());

				BasicInfo.TwitInstance = mTwit;
				BasicInfo.TwitRequestToken = mRequestToken;

				
				mHandler.post(new Runnable() {
					public void run() {

						Intent intent = new Intent(getApplicationContext(), TwitLogin.class);
						intent.putExtra("authUrl", mRequestToken.getAuthorizationURL());
						startActivityForResult(intent, BasicInfo.REQ_CODE_TWIT_LOGIN);

					}
				});
				
	    	} catch (Exception ex) {
				ex.printStackTrace();
			}

    	}
    }
    
    /**
     * 다른 액티비티로부터의 응답 처리
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
		super.onActivityResult(requestCode, resultCode, resultIntent);

		if (resultCode == RESULT_OK) {
			if (requestCode == BasicInfo.REQ_CODE_TWIT_LOGIN) {
				
				OAuthAccessTokenThread thread = new OAuthAccessTokenThread(resultIntent);
				thread.start();
				
			}
		}
	}

	
	class OAuthAccessTokenThread extends Thread {
		Intent resultIntent;
		
		public OAuthAccessTokenThread(Intent intent) {
			resultIntent = intent;
		}
		
		public void run() {
			try {
				Twitter mTwit = BasicInfo.TwitInstance;

				AccessToken mAccessToken = mTwit.getOAuthAccessToken(BasicInfo.TwitRequestToken, resultIntent.getStringExtra("oauthVerifier"));

				BasicInfo.TwitLogin = true;
				BasicInfo.TWIT_KEY_TOKEN = mAccessToken.getToken();
				BasicInfo.TWIT_KEY_TOKEN_SECRET = mAccessToken.getTokenSecret();

				BasicInfo.TwitAccessToken = mAccessToken;

				BasicInfo.TwitScreenName = mTwit.getScreenName();

				mHandler.post(new Runnable() {
					public void run() {
						Toast.makeText(getBaseContext(), "Twitter connection succeeded : " + BasicInfo.TWIT_KEY_TOKEN, Toast.LENGTH_LONG).show();
	
						showUserTimeline();
					}
				});

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	private void showUserTimeline() {
		Log.d(TAG, "showUserTimeline() called.");

		connectBtn.setVisibility(View.GONE);
        nameText.setVisibility(View.VISIBLE);
        nameText.setText(BasicInfo.TwitScreenName);

		// UserTimeline 요청
        GetUserTimelineThread thread = new GetUserTimelineThread();
        thread.start();

	}


	class GetUserTimelineThread extends Thread {
		public void run() {
			getUserTimeline();
		}
		
		/**
		 * UserTimeline 요청
		 */
		private void getUserTimeline() {
			Twitter mTwit = BasicInfo.TwitInstance;

			try {
				final List<Status> statuses = mTwit.getUserTimeline();

				mHandler.post(new Runnable() {
					public void run() {
						statusAdapter.setListItems(statuses);
						statusAdapter.notifyDataSetChanged();
					}
				});
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}

		}

	}
	

	protected void onPause() {
		super.onPause();

		saveProperties();
	}

	protected void onResume() {
		super.onResume();

		loadProperties();
	}

	private void saveProperties() {
		SharedPreferences pref = getSharedPreferences("TWIT", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putBoolean("TwitLogin", BasicInfo.TwitLogin);
		editor.putString("TWIT_KEY_TOKEN", BasicInfo.TWIT_KEY_TOKEN);
		editor.putString("TWIT_KEY_TOKEN_SECRET", BasicInfo.TWIT_KEY_TOKEN_SECRET);

		editor.commit();
	}

	private void loadProperties() {
		SharedPreferences pref = getSharedPreferences("TWIT", MODE_PRIVATE);

		BasicInfo.TwitLogin = pref.getBoolean("TwitLogin", false);
		BasicInfo.TWIT_KEY_TOKEN = pref.getString("TWIT_KEY_TOKEN", "");
		BasicInfo.TWIT_KEY_TOKEN_SECRET = pref.getString("TWIT_KEY_TOKEN_SECRET", "");

	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
