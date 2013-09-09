package com.msi.manning.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.msi.manning.network.data.HTTPRequestHelper;

import org.apache.http.client.ResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Android Apache HTTP example demonstrating using the ClientLogin feature of the Google Data APIs
 * (obtain a token, and send it as a header with subsequent requests).
 * 
 * @author charliecollins
 * 
 */
public class GoogleClientLogin extends Activity {

    private static final String CLASSTAG = GoogleClientLogin.class.getSimpleName();
    private static final String URL_GET_GTOKEN = "https://www.google.com/accounts/ClientLogin";
    private static final String URL_GET_CONTACTS_PREFIX = "http://www.google.com/m8/feeds/contacts/"; // liz%40gmail.com
    private static final String URL_GET_CONTACTS_SUFFIX = "/full";
    private static final String GTOKEN_AUTH_HEADER_NAME = "Authorization";
    private static final String GTOKEN_AUTH_HEADER_VALUE_PREFIX = "GoogleLogin auth=";
    private static final String PARAM_ACCOUNT_TYPE = "accountType";
    private static final String PARAM_ACCOUNT_TYPE_VALUE = "HOSTED_OR_GOOGLE";
    private static final String PARAM_EMAIL = "Email";
    private static final String PARAM_PASSWD = "Passwd";
    private static final String PARAM_SERVICE = "service";
    private static final String PARAM_SERVICE_VALUE = "cp"; // google contacts
    private static final String PARAM_SOURCE = "source";
    private static final String PARAM_SOURCE_VALUE = "manning-unlockingAndrid-1.0";

    private String tokenValue;

    private EditText emailAddress;
    private EditText password;
    private Button getContacts;
    private Button getToken;
    private Button clearToken;
    private TextView tokenText;
    private TextView output;

    private ProgressDialog progressDialog;

    private final Handler tokenHandler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            progressDialog.dismiss();
            String bundleResult = msg.getData().getString("RESPONSE");
            Log.d(Constants.LOGTAG, " " + GoogleClientLogin.CLASSTAG + " response body before auth trim - "
                + bundleResult);

            String authToken = bundleResult;
            authToken = authToken.substring(authToken.indexOf("Auth=") + 5, authToken.length()).trim();

            tokenValue = authToken;
            tokenText.setText(authToken);
        }
    };

    private final Handler contactsHandler = new Handler() {

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
        this.setContentView(R.layout.google_client_login);

        this.emailAddress = (EditText) findViewById(R.id.gclient_email);
        this.password = (EditText) findViewById(R.id.gclient_password);

        this.getToken = (Button) findViewById(R.id.gclientgettoken_button);
        this.clearToken = (Button) findViewById(R.id.gclientcleartoken_button);
        this.getContacts = (Button) findViewById(R.id.gclientgetcontacts_button);
        this.tokenText = (TextView) findViewById(R.id.gclient_token);
        this.output = (TextView) findViewById(R.id.gclient_output);

        this.getToken.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText("");

                if ((tokenValue == null) || tokenValue.equals("")) {
                    Log.d(Constants.LOGTAG, " " + GoogleClientLogin.CLASSTAG + " token NOT set, getting it");
                    getToken(emailAddress.getText().toString(), password.getText().toString());
                } else {
                    Log.d(Constants.LOGTAG, " " + GoogleClientLogin.CLASSTAG
                        + " token already set, not re-getting it (clear it first)");
                }
            }
        });

        this.clearToken.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText("");
                tokenText.setText("");
                tokenValue = null;
            }
        });

        this.getContacts.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText("");
                getContacts(emailAddress.getText().toString(), tokenValue);
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

    private void getContacts(final String email, final String token) {
        final ResponseHandler<String> responseHandler = HTTPRequestHelper
            .getResponseHandlerInstance(this.contactsHandler);

        this.progressDialog = ProgressDialog.show(this, "working . . .", "getting Google Contacts");

        // do the HTTP dance in a separate thread (the responseHandler will fire when complete)
        new Thread() {

            @Override
            public void run() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(GoogleClientLogin.GTOKEN_AUTH_HEADER_NAME,
                    GoogleClientLogin.GTOKEN_AUTH_HEADER_VALUE_PREFIX + token);

                String encEmail = email;
                try {
                    encEmail = URLEncoder.encode(encEmail, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e(Constants.LOGTAG, " " + GoogleClientLogin.CLASSTAG, e);
                }
                String url = GoogleClientLogin.URL_GET_CONTACTS_PREFIX + encEmail
                    + GoogleClientLogin.URL_GET_CONTACTS_SUFFIX;
                Log.d(Constants.LOGTAG, " " + GoogleClientLogin.CLASSTAG + " getContacts URL - " + url);

                HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
                helper.performGet(url, null, null, headers);
            }
        }.start();
    }

    private void getToken(final String email, final String pass) {
        final ResponseHandler<String> responseHandler = HTTPRequestHelper.getResponseHandlerInstance(this.tokenHandler);

        this.progressDialog = ProgressDialog.show(this, "working . . .", "getting Google ClientLogin token");

        // do the HTTP dance in a separate thread (the responseHandler will fire when complete)
        new Thread() {

            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put(GoogleClientLogin.PARAM_ACCOUNT_TYPE, GoogleClientLogin.PARAM_ACCOUNT_TYPE_VALUE);
                params.put(GoogleClientLogin.PARAM_EMAIL, email);
                params.put(GoogleClientLogin.PARAM_PASSWD, pass);
                params.put(GoogleClientLogin.PARAM_SERVICE, GoogleClientLogin.PARAM_SERVICE_VALUE);
                params.put(GoogleClientLogin.PARAM_SOURCE, GoogleClientLogin.PARAM_SOURCE_VALUE);

                HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
                helper.performPost(HTTPRequestHelper.MIME_FORM_ENCODED, GoogleClientLogin.URL_GET_GTOKEN, null, null,
                    null, params);
            }
        }.start();
    }
}
