package org.androidtown.networking.xmlrpc;

import java.util.Vector;

import org.ubiworks.mobile.protocol.ibml.android.IBML;
import org.ubiworks.mobile.protocol.ibml.android.IBMLClient;
import org.ubiworks.mobile.protocol.ibml.android.IBMLError;
import org.ubiworks.mobile.protocol.ibml.android.IBMLPacketException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 연결을 유지하면서 요청하는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class SampleIBMLActivity2 extends Activity {

    /**
     * URL string
     */
    private String url = "http://147.46.109.56:10423/";

    /**
     * handler name
     */
    private String HANDLER_NAME = "echo";

    /**
     * TAG for logging
     */
    public static final String TAG = "SimpleIBML";

    /**
     * Message Text
     */
    private TextView txtMsg;

    /**
     * Handler
     */
    Handler handler = new Handler();
    

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button connectBtn = (Button) findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		RequestThread thread = new RequestThread();
        		thread.start();
        	}
        });

        txtMsg = (TextView) findViewById(R.id.txtMsg);

    }


    /**
     * Thread for request
     */
    class RequestThread extends Thread {
    	public void run() {
    		launch();
    	}
    	
	    /**
	     * Launch this example
	     */
	    public void launch() {
	
	    	try {
	    		// stateful
	            IBML.setKeepAlive(true);
	            println("KeepAlive mode is set to true.");
	
		        // initialize client
		        IBMLClient client = new IBMLClient(url);
		        println("Client initialized with URL [" + url + "].\n");
	
		        // add parameters
		        Vector params = new Vector();
		        params.add("Hello Android!");
	
		        // execute remote handler and wait for the response
		        Vector response = null;
	
	            println("Trying to run 3 times.");
	
	            for (int k = 0; k < 3; k++) {
	                // execute remote handler
	    	        println("Executing server side object [" + HANDLER_NAME + "]...");
	                response = (Vector)client.execute(HANDLER_NAME + ".execute", params);
	                println("Waiting response ...\n");
	
	                // process response
	                processResponse(response);
	            }
	
	            // sleep 5 seconds
	            println("Sleeping 5 seconds...");
	            Thread.sleep(5000);
	
	        } catch(Exception ex) {
	            ex.printStackTrace();
	        }
	
	
	    }
	
	
	    /**
	     * Process the received response
	     *
	     * @param response response vector
	     */
	    private void processResponse(Vector response)
	    throws IBMLPacketException {
	        println("Processing response ...\n");
	
	        // for each items in the vector
	        for (int i = 0; i < response.size(); i++) {
	            Object obj = response.get(i);
	
	            if (obj instanceof String) {  // normal
	            	String msg = "#" + i + " (String) : " + obj;
	                println(msg);
	
	            } else if (obj instanceof IBMLError) {  // error
	                IBMLError errorObj = (IBMLError) obj;
	                String msg = "#" + i + " (IBMLError) : " + errorObj.getCode() + ", " + errorObj.getMessage();
	                println(msg);
	
	            } else {
	            	String msg = "#" + i + " : " + response.get(i);
	            	println(msg);
	
	            }
	
	        }
	
	    }

    }
    
    /**
     * Print log message
     *
     * @param msg
     */
    private void println(String msg) {
    	Log.d(TAG, msg);

    	final String curMsg = msg;
    	handler.post(new Runnable() {
    		public void run() {
    			txtMsg.append("\n" + curMsg);
    		}
    	});
        
    }

}