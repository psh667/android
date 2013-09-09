/*
 * refreshjobs.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */

package com.msi.manning.UnlockingAndroid;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RefreshJobs extends Activity {

    Prefs myprefs = null;
    Boolean bCancel = false;
    JobList mList = null;
    ProgressDialog myprogress;
    Handler progresshandler;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.refreshjobs);

        this.myprefs = new Prefs(getApplicationContext());

        this.myprogress = ProgressDialog.show(this, "Refreshing Job List", "Please Wait", true, false);

        // install handler for processing gui update messages
        this.progresshandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // process incoming messages here
                switch (msg.what) {
                    case 0:
                        // update progress bar
                        RefreshJobs.this.myprogress.setMessage("" + (String) msg.obj);
                        break;
                    case 1:
                        RefreshJobs.this.myprogress.cancel();
                        finish();
                        break;
                    case 2: // error occurred
                        RefreshJobs.this.myprogress.cancel();
                        finish();
                        break;
                }
                // super.handleMessage(msg);
            }
        };

        Thread workthread = new Thread(new DoReadJobs());

        workthread.start();

    }

    class DoReadJobs implements Runnable {

        public void run() {
            InputSource is = null;

            // set up our message - used to convey progress information
            Message msg = new Message();
            msg.what = 0;

            try {
                // Looper.prepare();

                msg.obj = ("Connecting ...");
                RefreshJobs.this.progresshandler.sendMessage(msg);
                URL url = new URL(RefreshJobs.this.myprefs.getServer() + "getjoblist.php?identifier="
                    + RefreshJobs.this.myprefs.getEmail());
                // get our data via the url class
                is = new InputSource(url.openStream());

                // create the factory
                SAXParserFactory factory = SAXParserFactory.newInstance();

                // create a parser
                SAXParser parser = factory.newSAXParser();

                // create the reader (scanner)
                XMLReader xmlreader = parser.getXMLReader();

                // instantiate our handler
                JobListHandler jlHandler = new JobListHandler(getApplication().getApplicationContext(),
                    RefreshJobs.this.progresshandler);

                // assign our handler
                xmlreader.setContentHandler(jlHandler);

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Parsing ...");
                RefreshJobs.this.progresshandler.sendMessage(msg);

                // perform the synchronous parse
                xmlreader.parse(is);

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Parsing Complete");
                RefreshJobs.this.progresshandler.sendMessage(msg);

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Saving Job List");
                RefreshJobs.this.progresshandler.sendMessage(msg);

                jlHandler.getList().persist();

                msg = new Message();
                msg.what = 0;
                msg.obj = ("Job List Saved.");
                RefreshJobs.this.progresshandler.sendMessage(msg);

                msg = new Message();
                msg.what = 1;
                RefreshJobs.this.progresshandler.sendMessage(msg);

            } catch (Exception e) {
                Log.d("CH12", "Exception: " + e.getMessage());
                msg = new Message();
                msg.what = 2; // error occured
                msg.obj = ("Caught an error retrieving Job data: " + e.getMessage());
                RefreshJobs.this.progresshandler.sendMessage(msg);

            }
        }
    }

}
