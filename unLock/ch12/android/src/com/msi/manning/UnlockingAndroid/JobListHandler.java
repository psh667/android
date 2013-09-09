/*
 * JobListHandler.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */

package com.msi.manning.UnlockingAndroid;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class JobListHandler extends DefaultHandler {

    Handler phandler = null;
    JobList _list;
    JobEntry _job;
    String _lastElementName = "";
    StringBuilder sb = null;
    Context _context;

    JobListHandler(Context c, Handler progresshandler) {
        this._context = c;
        if (progresshandler != null) {
            this.phandler = progresshandler;
            Message msg = new Message();
            msg.what = 0;
            msg.obj = ("Processing List");
            this.phandler.sendMessage(msg);
        }
    }

    public JobList getList() {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = ("Fetching List");
        if (this.phandler != null) {
            this.phandler.sendMessage(msg);
        }
        return this._list;
    }

    @Override
    public void startDocument() throws SAXException {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = ("Starting Document");
        if (this.phandler != null) {
            this.phandler.sendMessage(msg);
        }

        // initialize our JobLIst object - this will hold our parsed contents
        this._list = new JobList(this._context);

        // initialize the JobEntry object
        this._job = new JobEntry();

    }

    @Override
    public void endDocument() throws SAXException {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = ("End of Document");
        if (this.phandler != null) {
            this.phandler.sendMessage(msg);
        }

    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        try {
            this.sb = new StringBuilder("");

            if (localName.equals("job")) {
                // create a new item

                Message msg = new Message();
                msg.what = 0;
                msg.obj = (localName);
                if (this.phandler != null) {
                    this.phandler.sendMessage(msg);
                }

                this._job = new JobEntry();

            }
        } catch (Exception ee) {
            Log.d("CH12:startElement", ee.getStackTrace().toString());
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {

        if (localName.equals("job")) {
            // add our job to the list!
            this._list.addJob(this._job);
            Message msg = new Message();
            msg.what = 0;
            msg.obj = ("Storing Job # " + this._job.get_jobid());
            if (this.phandler != null) {
                this.phandler.sendMessage(msg);
            }

            return;
        }

        if (localName.equals("id")) {
            this._job.set_jobid(this.sb.toString());
            return;
        }
        if (localName.equals("status")) {
            this._job.set_status(this.sb.toString());
            return;
        }
        if (localName.equals("customer")) {
            this._job.set_customer(this.sb.toString());
            return;
        }
        if (localName.equals("address")) {
            this._job.set_address(this.sb.toString());
            return;
        }
        if (localName.equals("city")) {
            this._job.set_city(this.sb.toString());
            return;
        }
        if (localName.equals("state")) {
            this._job.set_state(this.sb.toString());
            return;
        }
        if (localName.equals("zip")) {
            this._job.set_zip(this.sb.toString());
            return;
        }
        if (localName.equals("product")) {
            this._job.set_product(this.sb.toString());
            return;
        }
        if (localName.equals("producturl")) {
            this._job.set_producturl(this.sb.toString());
            return;
        }
        if (localName.equals("comments")) {
            this._job.set_comments(this.sb.toString());
            return;
        }

    }

    @Override
    public void characters(char ch[], int start, int length) {
        String theString = new String(ch, start, length);
        Log.d("CH12", "characters[" + theString + "]");
        this.sb.append(theString);
    }
}
