package com.msi.manning.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.msi.manning.network.data.HTTPRequestHelper;

import org.apache.http.client.ResponseHandler;

import java.util.HashMap;

/**
 * Simple form to exercise the HttpRequestHelper class (which wraps HttpClient).
 * 
 * GET: http://www.yahoo.com GET: http://www.google.com/search?&q=android POST:
 * http://www.snee.com/xml/crud/posttest.cgi (fname and lname params)
 * 
 * Or, host "echo.jsp" from the root of this project locally and use it for testing.
 * 
 * 
 * @author charliecollins
 * 
 */
public class HTTPHelperForm extends Activity {

    private static final String CLASSTAG = HTTPHelperForm.class.getSimpleName();

    private EditText url;
    private Spinner method;
    private EditText param1Name;
    private EditText param1Value;
    private EditText param2Name;
    private EditText param2Value;
    private EditText param3Name;
    private EditText param3Value;
    private EditText user;
    private EditText pass;
    private Button go;
    private TextView output;

    private ProgressDialog progressDialog;

    // use a handler to update the UI (send the handler messages from other threads)
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            progressDialog.dismiss();
            String bundleResult = msg.getData().getString("RESPONSE");
            output.setText(bundleResult);
        }
    };

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        // inflate the SAME view XML layout file as ApacheHTTPWithAuth Activity (re-use it)
        this.setContentView(R.layout.http_helper_form);

        this.url = (EditText) findViewById(R.id.htth_url);
        this.method = (Spinner) findViewById(R.id.htth_method);
        this.param1Name = (EditText) findViewById(R.id.htth_param1_name);
        this.param1Value = (EditText) findViewById(R.id.htth_param1_value);
        this.param2Name = (EditText) findViewById(R.id.htth_param2_name);
        this.param2Value = (EditText) findViewById(R.id.htth_param2_value);
        this.param3Name = (EditText) findViewById(R.id.htth_param3_name);
        this.param3Value = (EditText) findViewById(R.id.htth_param3_value);
        this.user = (EditText) findViewById(R.id.htth_user);
        this.pass = (EditText) findViewById(R.id.htth_pass);
        this.go = (Button) findViewById(R.id.htth_go_button);
        this.output = (TextView) findViewById(R.id.htth_output);

        ArrayAdapter<String> methods = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
            new String[] { "GET", "POST" });
        methods.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.method.setAdapter(methods);

        this.go.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText("");

                performRequest(url.getText().toString(), method.getSelectedItem().toString(), param1Name.getText()
                    .toString(), param1Value.getText().toString(), param2Name.getText().toString(), param2Value
                    .getText().toString(), param3Name.getText().toString(), param3Value.getText().toString(), user
                    .getText().toString(), pass.getText().toString());
            }
        });
    };

    @Override
    public void onPause() {
        super.onPause();
        if ((this.progressDialog != null) && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

    /**
     * Perform asynchronous HTTP using Apache <code>HttpClient</code> via
     * <code>HttpRequestHelper</code> and <code>ResponseHandler</code>.
     * 
     * @param url
     * @param method
     * @param param1Name
     * @param param1Value
     * @param param2Name
     * @param param2Value
     * @param param3Name
     * @param param3Value
     * @param user
     * @param pass
     */
    private void performRequest(final String url, final String method, final String param1Name,
        final String param1Value, final String param2Name, final String param2Value, final String param3Name,
        final String param3Value, final String user, final String pass) {

        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " request url - " + url);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " request method - " + method);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " param1Name - " + param1Name);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " param1Value - " + param1Value);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " param2Name - " + param2Name);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " param2Value - " + param2Value);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " param3Name - " + param3Name);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " param3Value - " + param3Value);
        Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " user - " + user);
        if (pass != null) {
            Log.d(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " pass length - " + pass.length());
        }

        final HashMap<String, String> params = new HashMap<String, String>();
        if ((param1Name != null) && (param1Value != null)) {
            params.put(param1Name, param1Value);
        }
        if ((param2Name != null) && (param2Value != null)) {
            params.put(param2Name, param2Value);
        }
        if ((param3Name != null) && (param3Value != null)) {
            params.put(param3Name, param3Value);
        }

        final ResponseHandler<String> responseHandler = HTTPRequestHelper.getResponseHandlerInstance(this.handler);

        this.progressDialog = ProgressDialog.show(this, "working . . .", "performing HTTP request");

        // do the HTTP dance in a separate thread (the responseHandler will fire when complete)
        new Thread() {

            @Override
            public void run() {
                HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
                if (method.equals("GET")) {
                    helper.performGet(url, user, pass, null);
                } else if (method.equals("POST")) {
                    helper.performPost(HTTPRequestHelper.MIME_FORM_ENCODED, url, user, pass, null, params);
                } else {
                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("RESPONSE", "ERROR - see logcat");
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    Log.w(Constants.LOGTAG, " " + HTTPHelperForm.CLASSTAG + " unknown method, nothing to do");
                }
            }
        }.start();
    }
}
