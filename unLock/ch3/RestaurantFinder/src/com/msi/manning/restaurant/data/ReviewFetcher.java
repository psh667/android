package com.msi.manning.restaurant.data;

import android.util.Log;

import com.msi.manning.restaurant.Constants;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Use Google Base with specified criteria to obtain Review data.
 * 
 * @author charliecollins
 */
public class ReviewFetcher {

    private static final String CLASSTAG = ReviewFetcher.class.getSimpleName();
    private static final String QBASE = "http://www.google.com/base/feeds/snippets/-/reviews?bq=[review%20type:restaurant]";
    private static final String QD_PREFIX = "[description:";
    private static final String QD_SUFFIX = "]";
    private static final String QL_PREFIX = "[location:";
    private static final String QL_SUFFIX = "]";
    private static final String QMAX_RESULTS = "&max-results=";
    private static final String QR_PREFIX = "[rating:";
    private static final String QR_SUFFIX = "]";
    private static final String QSTART_INDEX = "&start-index=";
    private final int numResults;
    private String query;
    private final int start;

    /**
     * Construct ReviewFetcher with location, description, rating, and paging params.
     * 
     * @param location
     * @param description
     * @param rating
     * @param start
     * @param numResults
     */
    public ReviewFetcher(String loc, String description, String rat, int start, int numResults) {

        Log.v(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG + " location = " + loc + " rating = " + rat + " start = "
            + start + " numResults = " + numResults);

        this.start = start;
        this.numResults = numResults;
        String location = loc;
        String rating = rat;

        // urlencode params
        try {
            if (location != null) {
                location = URLEncoder.encode(location, "UTF-8");
            }
            if (rating != null) {
                rating = URLEncoder.encode(rating, "UTF-8");
            }
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        // build query
        this.query = ReviewFetcher.QBASE;
        if ((rating != null) && !rating.equals("ALL")) {
            this.query += (ReviewFetcher.QR_PREFIX + rating + ReviewFetcher.QR_SUFFIX);
        }
        if ((location != null) && !location.equals("")) {
            this.query += (ReviewFetcher.QL_PREFIX + location + ReviewFetcher.QL_SUFFIX);
        }
        if ((description != null) && !description.equals("ANY")) {
            this.query += (ReviewFetcher.QD_PREFIX + description + ReviewFetcher.QD_SUFFIX);
        }
        this.query += (ReviewFetcher.QSTART_INDEX + this.start + ReviewFetcher.QMAX_RESULTS + this.numResults);

        Log.v(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG + " query - " + this.query);
    }

    /**
     * Call Google Base and parse via SAX.
     * 
     * @return
     */
    public ArrayList<Review> getReviews() {
        long startTime = System.currentTimeMillis();
        ArrayList<Review> results = null;

        try {
            URL url = new URL(this.query);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();

            ReviewHandler handler = new ReviewHandler();
            xr.setContentHandler(handler);

            xr.parse(new InputSource(url.openStream()));
            // after parsed, get record
            results = handler.getReviews();
        } catch (Exception e) {
            Log.e(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG, e);
        }
        long duration = System.currentTimeMillis() - startTime;
        Log.v(Constants.LOGTAG, " " + ReviewFetcher.CLASSTAG + " call and parse duration - " + duration);
        return results;
    }
}
