package org.androidtown.sns.faceapp;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

/**
 * 페이스북을 연동하는 방법에 대해 알 수 있습니다.
 * 가장 최신 버전은 Facebook SDK 3.0이며 페이스북의 개발자 지원 페이지에서 확인하실 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";

	TextView nameText;
	Button connectBtn;
	FeedListView feedList;
	FeedListAdapter feedAdapter;

	Button writeBtn;
	EditText writeInput;
	
	// 결과값을 UI에 보여주기 위한 핸들러
	Handler mHandler = new Handler();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 연결 버튼 이벤트 처리
        connectBtn = (Button) findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		connect();
        	}
        });

        nameText = (TextView) findViewById(R.id.nameText);

        // 리스트뷰 처리
        feedList = (FeedListView) findViewById(R.id.feedList);
        feedAdapter = new FeedListAdapter(this);
        feedList.setAdapter(feedAdapter);
        feedList.setOnDataSelectionListener(new OnDataSelectionListener() {
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				FeedItem curItem = (FeedItem) feedAdapter.getItem(position);
				String curText = curItem.getMessage();

				Toast.makeText(getApplicationContext(), "Selected : " + curText, 1000).show();
			}
		});

        // 글쓰기 버튼 이벤트 처리
        writeBtn = (Button) findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		String statusText = writeInput.getText().toString();
        		if (statusText.length() < 1) {
        			Toast.makeText(getApplicationContext(), "글을 입력하세요.", 1000).show();
        			return;
        		}

        		// Status 메시지 업데이트
        		UpdateStatusThread thread = new UpdateStatusThread(statusText);
        		thread.start();
        		
        	}
        });

        writeInput = (EditText) findViewById(R.id.writeInput);

    }
 
    /**
     * Status 메시지 업데이트를 위한 스레드 정의
     */
    class UpdateStatusThread extends Thread {
    	String statusText;
    	
    	public UpdateStatusThread(String txt) {
    		statusText = txt;
    	}
    	
    	public void run() {
    		try {
        		Bundle params = new Bundle();
        	    params.putString("message", statusText);
        	    params.putString("name", "Mike");
        	    params.putString("link", "");
        	    params.putString("description", "Message from Android.");
        	    //params.putString("picture", "");

        	    BasicInfo.FacebookInstance.request("me/feed", params, "POST");

        	    mHandler.post(new Runnable() {
        	    	public void run() {
        	    		Toast.makeText(getApplicationContext(), "글을 업데이트했습니다.", 1000).show();

                		showUserTimeline();
        	    	}
        	    });
        		
        	} catch(Exception ex) {
        		ex.printStackTrace();
        	}
    	}
    }
    
    
    /**
     * 연결하기
     */
    private void connect() {
    	try {
    		Facebook mFacebook = new Facebook(BasicInfo.FACEBOOK_APP_ID);
    		BasicInfo.FacebookInstance = mFacebook;

    		if (!BasicInfo.RetryLogin && BasicInfo.FacebookLogin == true) {
    			mFacebook.setAccessToken(BasicInfo.FACEBOOK_ACCESS_TOKEN);

    			showUserTimeline();
    		} else {
    			mFacebook.authorize(this, BasicInfo.FACEBOOK_PERMISSIONS, new AuthorizationListener());
    		}
    	} catch (Exception ex) {
			ex.printStackTrace();
		}
    }

    /**
     * 타임라인 보여주기
     */
	private void showUserTimeline() {
		Log.d(TAG, "showUserTimeline() called.");

		connectBtn.setVisibility(View.GONE);
        nameText.setVisibility(View.VISIBLE);
        nameText.setText(BasicInfo.FACEBOOK_NAME);

        
        // UserTimeline 을 가져오기 위한 스레드 실행
        GetUserTimelineThread thread = new GetUserTimelineThread();
        thread.start();
        

		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(writeInput.getWindowToken(), 0);
	}

	
	/**
	 * 네트워크 처리를 위해 getUserTimeline() 메소드를 스레드 안에서 처리
	 */
	class GetUserTimelineThread extends Thread {
		
		public void run() {
			getUserTimeline();
		}
		
		/**
		 * 타임라인 가져오기
		 */
		private void getUserTimeline() {
			try {
				final String responseStr = BasicInfo.FacebookInstance.request("me/feed");
				Log.d(TAG, "response string : " + responseStr);

				
				mHandler.post(new Runnable() {
					public void run() {
						showResult(responseStr);
					}
				});
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		/**
		 * 결과값을 UI에 업데이트
		 */
		private void showResult(String responseStr) {
			try {
				feedAdapter.clear();
	
				JSONObject resultObj = Util.parseJson(responseStr);
				JSONArray jArray = resultObj.getJSONArray("data");
				for(int i = 0; i < jArray.length(); i++) {
					JSONObject obj = jArray.getJSONObject(i);
	
					String curId = obj.getString("id");
	
					JSONObject fromObj = obj.getJSONObject("from");
					String curName = fromObj.getString("name");
	
					String curMessage = "";
					try {
						curMessage = obj.getString("message");
					} catch(Exception ex) {
						ex.printStackTrace();
					}
	
					String dateStr = "";
					try {
						String curDate = obj.getString("created_time");
						Date curDateObj = BasicInfo.OrigDateFormat.parse(curDate);
						dateStr = BasicInfo.DateFormat.format(curDateObj);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
	
					String curPicture = null;
					try {
						curPicture = obj.getString("picture");
					} catch(Exception ex) {
						ex.printStackTrace();
					}
	
	
					FeedItem curItem = new FeedItem(curName, dateStr, curMessage, curPicture);
					feedAdapter.addItem(curItem);
	
					Log.d(TAG, "#" + i + " " + curName + ", " + dateStr + ", " + curMessage + ", " + curPicture);
				}
	
				feedAdapter.notifyDataSetChanged();
			} catch(Exception ex) {
				ex.printStackTrace();
			} catch(FacebookError err) {
				err.printStackTrace();
			}
		}

	}
	

	/**
	 * 다른 액티비티로부터의 응답 처리
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
		super.onActivityResult(requestCode, resultCode, resultIntent);

		if (resultCode == RESULT_OK) {
			if (requestCode == 32665) {
				BasicInfo.FacebookInstance.authorizeCallback(requestCode, resultCode, resultIntent);
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
		SharedPreferences pref = getSharedPreferences("FACEBOOK", MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();

		editor.putBoolean("FacebookLogin", BasicInfo.FacebookLogin);
		editor.putString("FACEBOOK_ACCESS_TOKEN", BasicInfo.FACEBOOK_ACCESS_TOKEN);
		editor.putString("FACEBOOK_NAME", BasicInfo.FACEBOOK_NAME);

		editor.commit();
	}

	private void loadProperties() {
		SharedPreferences pref = getSharedPreferences("FACEBOOK", MODE_PRIVATE);

		BasicInfo.FacebookLogin = pref.getBoolean("FacebookLogin", false);
		BasicInfo.FACEBOOK_ACCESS_TOKEN = pref.getString("FACEBOOK_ACCESS_TOKEN", "");
		BasicInfo.FACEBOOK_NAME = pref.getString("FACEBOOK_NAME", "");

	}

	/**
	 * 로그인을 위한 리스너
	 */
	public class AuthorizationListener implements DialogListener {

		public void onCancel() {

		}

		public void onComplete(Bundle values) {
			try {
				String resultStr = BasicInfo.FacebookInstance.request("me");
				JSONObject obj = new JSONObject(resultStr);

				BasicInfo.FACEBOOK_NAME = obj.getString("name");

				BasicInfo.FacebookLogin = true;
				BasicInfo.FACEBOOK_ACCESS_TOKEN = BasicInfo.FacebookInstance.getAccessToken();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

			Log.d(TAG, "Authorization completed : " + BasicInfo.FACEBOOK_NAME);

			showUserTimeline();

		}

		public void onError(DialogError e) {

		}

		public void onFacebookError(FacebookError e) {

		}

	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
