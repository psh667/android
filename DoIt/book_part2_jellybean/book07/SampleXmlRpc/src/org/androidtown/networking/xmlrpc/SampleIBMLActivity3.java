package org.androidtown.networking.xmlrpc;

import java.util.Vector;

import org.ubiworks.mobile.protocol.ibml.android.AsyncCallback;
import org.ubiworks.mobile.protocol.ibml.android.IBML;
import org.ubiworks.mobile.protocol.ibml.android.IBMLClient;
import org.ubiworks.mobile.protocol.ibml.android.IBMLError;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 비동기 방식으로 요청한 후 응답이 왔을 때 이벤트 처리하도록 하는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class SampleIBMLActivity3 extends Activity {

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

    ResponseHandler handler;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button connectBtn = (Button) findViewById(R.id.connectBtn);
        connectBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		launch();
        	}
        });

        txtMsg = (TextView) findViewById(R.id.txtMsg);


        handler = new ResponseHandler(txtMsg);
    }


    /**
     * Launch this example
     */
    public void launch() {

    	try {
    		// stateful
            IBML.setKeepAlive(true);
            println("KeepAlive mode is set to true.");

            // callback object
            EchoCallback CallbackObj = new EchoCallback();

	        // initialize client
	        IBMLClient client = new IBMLClient(url);
	        println("Client initialized with URL [" + url + "].\n");

	        // add parameters
	        Vector params = new Vector();
	        params.add("Hello 안드로이드!");

	        // execute remote handler
	        println("Executing server side object [" + HANDLER_NAME + "]...");
            client.executeAsync(HANDLER_NAME + ".execute", params, CallbackObj);
            println("Waiting response ...\n");

        } catch(Exception ex) {
            ex.printStackTrace();
        }


    }


    /**
     * Callback class for execAsync
     */
    public class EchoCallback implements AsyncCallback {

        public void handleResult(Object result, String url, String method) {
        	String debugMsg = "handleResult() called.";
        	Log.d(TAG, debugMsg);
        	sendMessage(debugMsg);

            Vector response = (Vector) result;

            for (int i = 0; i < response.size(); i++) {
                Object obj = response.get(i);

                if (obj instanceof String) {  // normal
                	String msg = "#" + i + " (String) : " + obj;
                	Log.d(TAG, msg);

                	sendMessage(msg);

                } else if (obj instanceof IBMLError) {  // error
                    IBMLError errorObj = (IBMLError) obj;
                    String msg = "#" + i + " (IBMLError) : " + errorObj.getCode() + ", " + errorObj.getMessage();
                    Log.d(TAG, msg);

                } else {
                	String msg = "#" + i + " : " + response.get(i);
                	Log.d(TAG, msg);

                }

            }

        }

        public void handleError(Exception exception, String url, String method) {
        	Log.d(TAG, "error in callback : " + exception.toString());
        }

    }



    /**
     * Print log message
     *
     * @param msg
     */
    private void println(String msg) {
    	Log.d(TAG, msg);
        txtMsg.append("\n" + msg);
    }

    /**
     * Send a string to the main thread
     *
     * @param msg
     */
    private void sendMessage(String msg) {
    	Bundle data = new Bundle();
    	data.putString("msg", msg);
    	Message curMessage = handler.obtainMessage();
    	curMessage.setData(data);

    	handler.sendMessage(curMessage);
    }

}
