package org.androidtown.gcm.push;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import static org.androidtown.gcm.push.BasicInfo.TOAST_MESSAGE_ACTION;

public class MainActivity extends Activity {
	public static final String TAG = "MainActivity";

	EditText messageInput;
	TextView messageOutput;

	/**
	 * 서버 : Task for sending messages
	 */
	AsyncTask<Void, Void, Void> mSendTask;
	
	/**
	 * 서버 : Sender 객체 선언
	 */
	Sender sender;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 서버 : GOOGLE_API_KEY를 이용해 Sender 초기화
        sender = new Sender(BasicInfo.GOOGLE_API_KEY);

        // 서버 : 전송할 메시지 입력 박스
        messageInput = (EditText) findViewById(R.id.messageInput);
        
        // 수신할 메시지 출력 박스
        messageOutput = (TextView) findViewById(R.id.messageOutput);

        // 서비스에서 보내오는 상태 메시지를 받아 토스트로 보여줄 수신자 등록
        registerReceiver(mToastMessageReceiver, new IntentFilter(TOAST_MESSAGE_ACTION));
        
        
        
        // 단말 등록하기 버튼
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		try {
        			// 단말 등록하고 등록 ID 받기
        			registerDevice();

        		} catch(Exception ex) {
        			ex.printStackTrace();
        		}
        	}
        });

        // 서버 : 전송하기 버튼
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
    			String msg = messageInput.getText().toString();
    			
    			sendToDevice(msg);
    			
        	}
        });

    }

    /**
     * 단말 등록
     */
    private void registerDevice() {
    	// 디바이스 체크
        GCMRegistrar.checkDevice(this);
        // 매니페스트 체크 개발 옵션
        GCMRegistrar.checkManifest(this);

        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
        	// 단말 등록 호출
        	GCMRegistrar.register(getBaseContext(), BasicInfo.PROJECT_ID);
        	
        } else {
            // 단말 등록되어 있음
        	if (GCMRegistrar.isRegisteredOnServer(this)) {
        		Log.d(TAG, "단말이 이미 등록되어 있습니다.");
        	} else {
        		// 단말 등록 호출
            	GCMRegistrar.register(getBaseContext(), BasicInfo.PROJECT_ID);
        	}
            
        }
    }
    
    /**
     * 서버 : 전송하기
     * 
     * @param msg
     */
    private void sendToDevice(final String msg) {
    	mSendTask = new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... params) {
            	Message.Builder messageBuilder = new Message.Builder();
    			messageBuilder.addData("msg", msg);
    			messageBuilder.addData("action", "show");
    			Message message = messageBuilder.build();
    			
        		try {
                	Result result = sender.send(message, BasicInfo.RegistrationId, 5);
        			Log.d(TAG, "Message sent. Result : " + result);
        			
        			// 토스트로 상태 메시지 보여주기
        			String statusMessage = "서버 : 단말로 메시지를 전송했습니다.\n결과 : " + result;
        			Intent intent = new Intent(TOAST_MESSAGE_ACTION);
        	        intent.putExtra("message", statusMessage);
        	        sendBroadcast(intent);
        			
        		} catch(Exception ex) {
        			ex.printStackTrace();
        		}
        		
                return null;
            }

            protected void onPostExecute(Void result) {
                mSendTask = null;
            }

        };
        mSendTask.execute(null, null, null);
      
    }
    
    
    @Override
    protected void onDestroy() {
    	if (mSendTask != null) {
            mSendTask.cancel(true);
        }
    	
    	unregisterReceiver(mToastMessageReceiver);
    	
        super.onDestroy();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
	protected void onNewIntent(Intent intent) {
    	Log.d(TAG, "onNewIntent() called.");

    	String msg = intent.getStringExtra("msg");
        String from = intent.getStringExtra("from");
        String action = intent.getStringExtra("action");

        Log.d(TAG, "DATA : " + from + ", " + action + ", " + msg);
        messageOutput.setText("[" + from + "]로부터 수신한 메시지 : " + msg);

		super.onNewIntent(intent);
	}

    /**
     * Receiver for showing status messages from GCMIntentService as a Toast
     */
    private final BroadcastReceiver mToastMessageReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String message = intent.getExtras().getString("message");
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    };
    
}
