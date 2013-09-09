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
import android.widget.Spinner;
import android.widget.TextView;

import com.msi.manning.network.util.StringUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Android basic HTTP example using Apache HttpClient.
 * 
 * 
 * @author charliecollins
 * 
 */
public class ApacheHTTPSimple extends Activity {

    private static final String CLASSTAG = ApacheHTTPSimple.class.getSimpleName();
    private static final String URL1 = "http://www.comedycentral.com/rss/jokes/index.jhtml";
    private static final String URL2 = "http://feeds.theonion.com/theonion/daily";

    private Spinner urlChooser;
    private Button button;
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
        this.setContentView(R.layout.apache_http_simple);

        this.urlChooser = (Spinner) findViewById(R.id.apache_url);
        ArrayAdapter<String> urls = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[] {
            ApacheHTTPSimple.URL1, ApacheHTTPSimple.URL2 });
        urls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.urlChooser.setAdapter(urls);

        this.button = (Button) findViewById(R.id.apachego_button);
        this.output = (TextView) findViewById(R.id.apache_output);

        this.button.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText("");
                performRequest();
            }
        });
    };

    @Override
    public void onPause() {
        super.onPause();
        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }

    /**
     * Perform asynchronous HTTP using Apache <code>HttpClient</code> and
     * <code>ResponseHandler</code>.
     * 
     * @param user
     * @param pass
     */
    private void performRequest() {

        // use a response handler so we aren't blocking on the HTTP request
        final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            public String handleResponse(HttpResponse response) {
                // when the response happens close the notification and update UI
                StatusLine status = response.getStatusLine();
                Log.d(Constants.LOGTAG, " " + ApacheHTTPSimple.CLASSTAG + " statusCode - " + status.getStatusCode());
                Log.d(Constants.LOGTAG, " " + ApacheHTTPSimple.CLASSTAG + " statusReasonPhrase - "
                    + status.getReasonPhrase());
                HttpEntity entity = response.getEntity();
                String result = null;
                try {
                    result = StringUtils.inputStreamToString(entity.getContent());
                    Message message = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("RESPONSE", result);
                    message.setData(bundle);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    Log.e(Constants.LOGTAG, " " + ApacheHTTPSimple.CLASSTAG, e);
                }
                return result;
            }
        };

        this.progressDialog = ProgressDialog.show(this, "working . . .", "performing HTTP request");

        // do the HTTP dance in a separate thread (the responseHandler will fire when complete)
        new Thread() {

            @Override
            public void run() {
                try {
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpGet httpMethod = new HttpGet(urlChooser.getSelectedItem().toString());
                    client.execute(httpMethod, responseHandler);
                } catch (ClientProtocolException e) {
                    Log.e(Constants.LOGTAG, " " + ApacheHTTPSimple.CLASSTAG, e);
                } catch (IOException e) {
                    Log.e(Constants.LOGTAG, " " + ApacheHTTPSimple.CLASSTAG, e);
                }
            }
        }.start();
    }

}
