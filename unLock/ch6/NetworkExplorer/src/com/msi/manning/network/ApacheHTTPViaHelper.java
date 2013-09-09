package com.msi.manning.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.msi.manning.network.data.HTTPRequestHelper;

import org.apache.http.client.ResponseHandler;

/**
 * Android HTTP example demonstrating basic auth over Apache HttpClient 4 (using del.icio.us API) -
 * AND using custom HttpRequestHelper.
 * 
 * 
 * @author charliecollins
 * 
 */
public class ApacheHTTPViaHelper extends Activity {

    private static final String CLASSTAG = ApacheHTTPViaHelper.class.getSimpleName();
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
        // inflate the SAME view XML layout file as ApacheHTTPSimple Activity (re-use it)
        this.setContentView(R.layout.apache_http_simple);

        this.urlChooser = (Spinner) findViewById(R.id.apache_url);
        ArrayAdapter<String> urls = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[] {
            ApacheHTTPViaHelper.URL1, ApacheHTTPViaHelper.URL2 });
        urls.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.urlChooser.setAdapter(urls);

        this.button = (Button) findViewById(R.id.apachego_button);
        this.output = (TextView) findViewById(R.id.apache_output);

        this.button.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText("");

                performRequest(urlChooser.getSelectedItem().toString());
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
    private void performRequest(final String url) {

        final ResponseHandler<String> responseHandler = HTTPRequestHelper.getResponseHandlerInstance(this.handler);

        this.progressDialog = ProgressDialog.show(this, "working . . .", "performing HTTP request");

        // do the HTTP dance in a separate thread (the responseHandler will fire when complete)
        new Thread() {

            @Override
            public void run() {
                HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
                helper.performGet(url, null, null, null);
            }
        }.start();
    }
}
