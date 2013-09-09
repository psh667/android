package org.androidtown.networking.xmlrpc;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

/**
 * Handler for the processing of response
 * 
 * @author angel
 *
 */
public class ResponseHandler extends Handler {
	
	public static final String TAG = "RESPONSE";
	TextView txtView;
	
	public ResponseHandler(TextView view) {
		super();
		
		txtView = view;
	}
	
	@Override
	public void handleMessage(Message message) {
		Bundle data = message.getData();
		String msg = data.getString("msg");
		println(msg);
	}
	
    private void println(String msg) {
    	Log.d(TAG, msg);
    	txtView.append("\n" + msg);
    }
    
}

