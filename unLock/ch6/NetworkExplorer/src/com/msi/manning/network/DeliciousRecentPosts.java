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
import com.msi.manning.network.data.xml.DeliciousHandler;
import com.msi.manning.network.data.xml.DeliciousPost;

import org.apache.http.client.ResponseHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Android HTTP example demonstrating basic auth over Apache HttpClient 4 (using del.icio.us API),
 * and XML parsing (HTTP and Plain XML - POX).
 * 
 * 
 * @author charliecollins
 * 
 */
public class DeliciousRecentPosts extends Activity {

    private static final String CLASSTAG = DeliciousRecentPosts.class.getSimpleName();
    private static final String URL_GET_POSTS_RECENT = "https://api.del.icio.us/v1/posts/recent?";

    private EditText user;
    private EditText pass;
    private TextView output;
    private Button button;

    private ProgressDialog progressDialog;

    // use a handler to update the UI (send the handler messages from other threads)
    private final Handler handler = new Handler() {

        @Override
        public void handleMessage(final Message msg) {
            progressDialog.dismiss();
            String bundleResult = msg.getData().getString("RESPONSE");
            output.setText(parseXMLResult(bundleResult));
        }
    };

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.delicious_posts);

        this.user = (EditText) findViewById(R.id.del_user);
        this.pass = (EditText) findViewById(R.id.del_pass);
        this.button = (Button) findViewById(R.id.delgo_button);
        this.output = (TextView) findViewById(R.id.del_output);

        this.button.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                output.setText("");

                performRequest(user.getText().toString(), pass.getText().toString());
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
    private void performRequest(final String user, final String pass) {

        this.progressDialog = ProgressDialog.show(this, "working . . .", "performing HTTP post to del.icio.us");

        final ResponseHandler<String> responseHandler = HTTPRequestHelper.getResponseHandlerInstance(this.handler);

        // do the HTTP dance in a separate thread (the responseHandler will fire when complete)
        new Thread() {

            @Override
            public void run() {
                HTTPRequestHelper helper = new HTTPRequestHelper(responseHandler);
                helper.performPost(DeliciousRecentPosts.URL_GET_POSTS_RECENT, user, pass, null, null);
            }
        }.start();

    }

    /**
     * Parse XML result into data objects.
     * 
     * @param xmlString
     * @return
     */
    private String parseXMLResult(String xmlString) {
        StringBuilder result = new StringBuilder();

        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            DeliciousHandler handler = new DeliciousHandler();
            xr.setContentHandler(handler);
            xr.parse(new InputSource(new StringReader(xmlString)));

            List<DeliciousPost> posts = handler.getPosts();
            for (DeliciousPost p : posts) {
                Log.d(Constants.LOGTAG, " " + DeliciousRecentPosts.CLASSTAG + " DeliciousPost - " + p.getHref());
                result.append("\n" + p.getHref());
            }
        } catch (Exception e) {
            Log.e(Constants.LOGTAG, " " + DeliciousRecentPosts.CLASSTAG + " ERROR - " + e);
        }
        return result.toString();
    }

}
