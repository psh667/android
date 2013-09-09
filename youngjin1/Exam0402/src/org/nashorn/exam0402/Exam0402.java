package org.nashorn.exam0402;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.EditText;

public class Exam0402 extends Activity {
	private EditText phoneText;
	private EditText messageText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
    	phoneText = (EditText)findViewById(R.id.phone);
    	messageText = (EditText)findViewById(R.id.message);
    	
    	startService(new Intent(this, MsgService.class));
    	
    	Intent bindIntent = new Intent(Exam0402.this, MsgService.class);
        bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        
        getMessage();
    }
    
    private MsgService serviceBinder;    
    private ServiceConnection serviceConnection = new ServiceConnection()
    {
    	public void onServiceConnected(ComponentName className, IBinder service)
    	{
    		serviceBinder = ((MsgService.MyBinder)service).getService();
    	}
    	
    	public void onServiceDisconnected(ComponentName className)
    	{
    		serviceBinder = null;
    	}
    };
    
    public void getMessage()
    {
    	if (serviceBinder != null)
    	{
        	phoneText.setText(serviceBinder.getPhoneNumber());
    		messageText.setText(serviceBinder.getMessageText());
    	}
		
		Handler mHandler = new Handler();
		   mHandler.postDelayed(new Runnable() { 
		      @Override
		      public void run() {
		    	  getMessage();
		      }
		   }, 1000);
    }
}