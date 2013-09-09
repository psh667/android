package org.androidtown.networking.xmlrpc;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.ubiworks.mobile.protocol.ibml.android.AsyncCallback;
import org.ubiworks.mobile.protocol.ibml.android.IBML;
import org.ubiworks.mobile.protocol.ibml.android.IBMLClient;
import org.ubiworks.mobile.protocol.ibml.android.IBMLError;
import org.ubiworks.mobile.protocol.mdbc.android.MRecord;
import org.ubiworks.mobile.protocol.mdbc.android.MRecordIterator;
import org.ubiworks.mobile.protocol.mdbc.android.MTable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 해시테이블이나 객체를 벡터 형태로 주고받는 방법에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class SampleIBMLActivity5 extends Activity {

    /**
     * URL string
     */
    private String url = "http://147.46.109.56:10423/";

    /**
     * handler name
     */
    private String HANDLER_NAME = "vector";
    //private String HANDLER_NAME = "hashtable";
    //private String HANDLER_NAME = "objectarray";

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
    		// Type Setting
    		IBML.TYPE_SUPPORT = IBML.TYPE_SUPPORT_ALL;

            // callback object
            VectorCallback CallbackObj = new VectorCallback();

	        // initialize client
	        IBMLClient client = new IBMLClient(url);
	        println("Client initialized with URL [" + url + "].\n");

	        // add parameters
	        Vector params = new Vector();

	        Integer CID = new Integer(10001);
	        Integer SID = new Integer(10001);

	        params.add(CID);
	        params.add(SID);

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
    public class VectorCallback implements AsyncCallback {

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

                } else if (obj instanceof Vector) {  // Vector
                    Vector outObj = (Vector) obj;

                    for (int k = 0; k < outObj.size(); k++) {
                        Object subObj = outObj.get(k);
                        String msg = "\t#" + k + " (" + subObj.getClass().getName() + ") : " + subObj.toString();
                        sendMessage(msg);
                    }

                } else if (obj instanceof Hashtable) {  // Hashtable
                    Hashtable outObj = (Hashtable) obj;

                    Enumeration enuma = outObj.keys();
                    int k = 0;
                    while(enuma.hasMoreElements()) {

                        Object subKey = enuma.nextElement();
                        Object subValue = outObj.get(subKey);
                        String msg = "\t#" + k + " " + subKey + " -> (" + subValue.getClass().getName() + ") : " + subValue.toString();
                        sendMessage(msg);

                        k++;
                    }

                } else if (obj instanceof Object[]) {  // Object Array
                    Object[] outObj = (Object[]) obj;

                    for (int k = 0; k < outObj.length; k++) {
                        if (outObj[k] == null) {
                            String msg = "\t#" + k + " -> NULL";
                            sendMessage(msg);
                        } else {
                            String msg = "\t#" + k + " (" + outObj[k].getClass().getName() + ") : " + outObj[k].toString();
                            sendMessage(msg);
                        }

                    }

                } else if (obj instanceof byte[]) {  // MTable
                	try {
	                    MTable curTable = new MTable();
	                    curTable.makeMTable((byte[])obj);

	                    String resultStr = printMTable(curTable);
	                    sendMessage(resultStr);
                	} catch(Exception ex) {
                		ex.printStackTrace();
                	}

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


    /**
     * Print out MTable instance
     */
    public String printMTable(MTable table) {
        StringBuffer StrBuf = new StringBuffer();
        StrBuf.append("\n\n##### MTable Info #####");
        StrBuf.append("\nNAME		: " + table.name);
        StrBuf.append("\nCOLUMN COUNT	: " + table.countColumn);
        StrBuf.append("\nRECORD COUNT	: " + table.count());
        StrBuf.append("\n\n##### Column Info #####");

        for(int i = 0; i < table.countColumn; i++) {
            StrBuf.append("\n#" + i + " (" + table.columns[i].name + ", " + table.columns[i].type + ", " + table.columns[i].length + ", " + table.columns[i].scale + ")");
        }

        StrBuf.append("\n\n##### Record Info #####");

        try {
            MRecordIterator iter = table.iterator();
            MRecord aRecord = null;
            Object aObj = null;
            int recidx = 0;

            while(iter.hasNext()) {
                aRecord = iter.next();
                StrBuf.append("\n#" + recidx + " ");
                for (int i = 0; i < table.countColumn; i++) {
                    aObj = aRecord.getCell(i);
                    if (checkNull(aObj)) {
                        StrBuf.append("NULL" + "\t");
                    } else {
                        StrBuf.append(aObj + "\t");
                    }
                }
                recidx++;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            Log.e(TAG, ex.toString());
        }

        String resultStr = StrBuf.toString();
        Log.d(TAG, resultStr);

        return resultStr;
    }


    public boolean checkNull(Object obj) {
        if (obj instanceof java.lang.Byte) {
            byte outval = ((Byte)obj).byteValue();
            if (outval == 0xFF || outval == -1) {
                return true;
            }
        }

        return false;
    }


}

