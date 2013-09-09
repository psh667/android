package org.androidtown.networking.xmlrpc;

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
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * SQL문을 실행하여 원격데이터베이스에서 처리하고 한글 처리하는 방법에 대해서도 알 수 있습니다.
 *
 * @author Mike
 */
public class SampleIBMLActivity6 extends Activity {

    /**
     * URL string
     */
    private String url = "http://147.46.109.56:10423/";

    /**
     * handler name
     */
    private String HANDLER_NAME = "mexecute";

    /**
     * TAG for logging
     */
    public static final String TAG = "SimpleIBML";

    /**
     * Message Text
     */
    private TextView txtMsg;

    ResponseHandler handler;

    //String SQL_STATEMENT = "select ID, NAME, USE_YN from SERVERSET.USER";
    String SQL_STATEMENT = "select ID, NAME, AGE, ADDRESS, MOBILE from TEST.CUSTOMER";


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
            MDBCCallback CallbackObj = new MDBCCallback();

	        // initialize client
	        IBMLClient client = new IBMLClient(url);
	        println("Client initialized with URL [" + url + "].\n");

	        // add parameters
	        Vector params = new Vector();

	        params.add(SQL_STATEMENT);

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
    public class MDBCCallback extends Handler implements AsyncCallback {


        public void handleResult(Object result, String url, String method) {

        	String debugMsg = "handleResult() for " + method + " called.";
        	sendMessage(debugMsg);

            Vector response = (Vector) result;


            if (response.get(0) instanceof IBMLError) {
                IBMLError errorObj = (IBMLError) response.get(0);

                sendMessage("IBMLError : " + errorObj.getCode() + ", " + errorObj.getMessage());
            }


            // only for LOGIN (MSMIS)
            Vector responseObj = (Vector) response.get(0);
            String resCode = (String) responseObj.get(0);
            String resMsg = (String) responseObj.get(1);
            sendMessage("RESPONSE code : " + resCode + ", message : " + resMsg);

            try {
            	Object obj = responseObj.get(2);

                if (obj instanceof String) {  // normal
                	String msg = "# (String) : " + obj;
                	Log.d(TAG, msg);

                	sendMessage(msg);

                } else if (obj instanceof byte[]) {  // MTable
                	try {

                        sendMessage((byte[]) obj);

                	} catch(Exception ex) {
                		ex.printStackTrace();
                	}

                } else if (obj instanceof IBMLError) {  // error
                    IBMLError errorObj = (IBMLError) obj;
                    String msg = "# (IBMLError) : " + errorObj.getCode() + ", " + errorObj.getMessage();
                    Log.d(TAG, msg);

                } else {
                	String msg = "# : " + obj;
                	Log.d(TAG, msg);

                }

            } catch(Exception ex) {
            	ex.printStackTrace();
            }


        }

        public void handleError(Exception exception, String url, String method) {

        	Log.d(TAG, "error in callback : " + exception.toString());

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

        private void sendMessage(byte[] obj) {
        	Bundle data = new Bundle();
        	data.putString("msg", "Table Data");
        	data.putByteArray("table", obj);
        	Message curMessage = obtainMessage();
        	curMessage.setData(data);

        	sendMessage(curMessage);
        }



    	public void handleMessage(Message message) {
    		Bundle data = message.getData();
    		String msg = data.getString("msg");
    		println(msg);

    		byte[] tableData = data.getByteArray("table");
    		if(tableData != null)
    		{
    			MTable table = null;
				table = new MTable("Result");
				try {
					table.makeMTable(tableData);

				} catch(Exception ex) {
					ex.printStackTrace();
				}

				String tableStr = printMTable(table);
				println(tableStr);
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
        txtMsg.append("\n" + msg);
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

