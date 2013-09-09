package com.msi.manning.windwaves.data;

import android.util.Log;

import com.msi.manning.windwaves.Constants;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Invoke NDBC API and parse.
 * 
 * @see NDBCHandler
 * 
 * @author charliecollins
 * 
 */
public class NDBCFetcher {

    private static final String CLASSTAG = NDBCFetcher.class.getSimpleName();

    private static final String QBASE = "http://www.ndbc.noaa.gov/rss/ndbc_obs_search.php?";
    private static final String QLAT = "lat=";
    private static final String QLON = "&lon=";
    private static final String QRAD = "&radius=";

    private String query;
    private String lat;
    private String lon;
    private String rad;

    /**
     * Network data fetcher for NDBC RSS feeds, requires lat in such as 37W, and long in such as
     * 122E (not integers or doubles, Strings).
     * 
     * @param lat
     * @param lon
     * @param rad
     */
    public NDBCFetcher(String lat, String lon, String rad) {
        this.lat = lat;
        this.lon = lon;
        this.rad = rad;

        this.query = NDBCFetcher.QBASE + NDBCFetcher.QLAT + this.lat + NDBCFetcher.QLON + this.lon + NDBCFetcher.QRAD
            + this.rad;
        Log.i(Constants.LOGTAG, NDBCFetcher.CLASSTAG + " query - " + this.query);
    }

    public ArrayList<BuoyData> getData() {
        ArrayList<BuoyData> buoys = new ArrayList<BuoyData>();
        try {
            URL url = new URL(this.query);
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            NDBCHandler handler = new NDBCHandler();
            xr.setContentHandler(handler);
            xr.parse(new InputSource(url.openStream()));
            // after parsed, get data
            buoys = handler.getBuoyDataList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buoys;
    }
}
