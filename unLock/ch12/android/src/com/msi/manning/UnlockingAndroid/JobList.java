/*
 * JobList.java Unlocking Android http://manning.com/ableson Author: W. F. Ableson
 * fableson@msiservices.com
 */

package com.msi.manning.UnlockingAndroid;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.util.Log;

public class JobList {

    private Context _context = null;
    private List<JobEntry> _joblist;

    JobList(Context c) {
        this._context = c;
        this._joblist = new Vector<JobEntry>(0);
    }

    int addJob(JobEntry job) {
        this._joblist.add(job);
        return this._joblist.size();
    }

    JobEntry getJob(int location) {
        return this._joblist.get(location);
    }

    List<JobEntry> getAllJobs() {
        return this._joblist;
    }

    int getJobCount() {
        return this._joblist.size();
    }

    void replace(JobEntry newjob) {
        try {
            JobList newlist = new JobList(this._context);
            for (int i = 0; i < getJobCount(); i++) {
                JobEntry je = getJob(i);
                if (je.get_jobid().equals(newjob.get_jobid())) {
                    Log.d("CH12", "Replacing Job");
                    newlist.addJob(newjob);
                } else {
                    newlist.addJob(je);
                }
            }
            this._joblist = newlist._joblist;
            persist();
        } catch (Exception e) {

        }
    }

    void persist() {
        try {
            FileOutputStream fos = this._context.openFileOutput("chapter12.xml", Context.MODE_PRIVATE);
            fos.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n".getBytes());
            fos.write("<joblist>\n".getBytes());
            for (int i = 0; i < getJobCount(); i++) {
                JobEntry je = getJob(i);
                fos.write(je.toXMLString().getBytes());
            }
            fos.write("</joblist>\n".getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.d("CH12", "Failed to write out file?" + e.getMessage());
        }
    }

    static JobList parse(Context context) {
        try {
            FileInputStream fis = context.openFileInput("chapter12.xml");

            if (fis == null) {
                return null;
            }
            // we need an input source for the sax parser
            InputSource is = new InputSource(fis);

            // create the factory
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // create a parser
            SAXParser parser = factory.newSAXParser();

            // create the reader (scanner)
            XMLReader xmlreader = parser.getXMLReader();

            // instantiate our handler
            JobListHandler jlHandler = new JobListHandler(context, null /*
                                                                         * no progress updates when
                                                                         * reading file
                                                                         */);

            // assign our handler
            xmlreader.setContentHandler(jlHandler);

            // perform the synchronous parse
            xmlreader.parse(is);

            // clean up
            fis.close();

            // return our new joblist
            return jlHandler.getList();
        } catch (Exception e) {
            Log.d("CH12", "Error parsing job list xml file : " + e.getMessage());
            return null;
        }
    }

}
