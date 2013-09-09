package com.msi.manning.windwaves.data;

import android.util.Log;

import com.msi.manning.windwaves.Constants;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * SAX Handler impl for National Data Buoy Center RSS feeds.
 * 
 * This class has some ugly because of the way the feed is variable, and the way the feed embeds
 * data in HTML formatted CDATA (rather than using defined fields).
 * 
 * @author charliecollins
 * 
 */
public class NDBCHandler extends DefaultHandler {

    /*
     * <item> <pubDate>Sun, 16 Nov 2008 16:30:01 UT</pubDate> <title>Station 46091</title>
     * <description><![CDATA[ <strong>November 16, 2008 7:50 am PST</strong><br />
     * <strong>Location:</strong> 36.753N 122.423W or 25 nautical miles SW of search location of 37N
     * 122W.<br /> <strong>Wind Direction:</strong> NE (40&#176;)<br /> <strong>Wind Speed:</strong>
     * 8 knots<br /> <strong>Wind Gust:</strong> 10 knots<br /> <strong>Significant Wave
     * Height:</strong> 5 ft<br /> <strong>Dominant Wave Period:</strong> 13 sec<br />
     * <strong>Atmospheric Pressure:</strong> 30.07 in (1018.3 mb)<br /> <strong>Pressure
     * Tendency:</strong> +0.03 in (+1.0 mb)<br /> <strong>Air Temperature:</strong> 60&#176;F
     * (15.5&#176;C)<br /> <strong>Water Temperature:</strong> 57&#176;F (13.7&#176;C)<br />
     * ]]></description>
     * 
     * <link>http://www.ndbc.noaa.gov/station_page.php?station=46091</link>
     * <guid>http://www.ndbc.noaa.gov/station_page.php?station=46091</guid> <georss:point>36.835
     * -121.899</georss:point> </item>
     */

    private static final String CLASSTAG = NDBCHandler.class.getSimpleName();

    private static final String ITEM = "item";
    private static final String PUBDATE = "pubDate";
    private static final String TITLE = "title";
    private static final String DESC = "description";
    private static final String LINK = "link";
    private static final String GEORSS = "point";

    private static final SimpleDateFormat DATE_FORMAT_A = new SimpleDateFormat("MMMM dd',' yyyy hh:mm a z");
    private static final SimpleDateFormat DATE_FORMAT_B = new SimpleDateFormat("EEE',' dd MMM yyyy hh:mm:ss z");

    private final ArrayList<BuoyData> buoys;
    private BuoyData buoy;

    private boolean inItem;
    private boolean inTitle;
    private boolean inPubDate;
    private boolean inDesc;
    private boolean inLink;
    private boolean inGeoRss;

    public NDBCHandler() {
        this.buoys = new ArrayList<BuoyData>();
        Log.d(Constants.LOGTAG, NDBCHandler.CLASSTAG + " NDBCHandler instantiated");
    }

    @Override
    public void characters(final char ch[], final int start, final int length) {
        String chString = "";
        if (ch != null) {
            chString = new String(ch, start, length);
        }

        if (this.inTitle) {
            this.buoy.title = chString;
        }

        if (this.inPubDate) {
            // hack to replace "UT" with GMT (UT won't parse)
            if (chString.contains("UT")) {
                chString = chString.replace("UT", "GMT");
            }
            this.buoy.dateString = chString;

            // try to handle the various date formats
            Date pubDate = null;
            try {
                pubDate = NDBCHandler.DATE_FORMAT_A.parse(chString);
            } catch (ParseException e) {
                // swallow
            }
            if (pubDate == null) {
                try {
                    pubDate = NDBCHandler.DATE_FORMAT_B.parse(chString);
                } catch (ParseException e) {
                    // swallow
                }
            }
            this.buoy.date = pubDate;
        }

        if (this.inDesc) {
            this.buoy.description = chString;
            // parse the description into separate elements (annoying, but the feed puts all this
            // data in one CDATA block)
            String[] descArray = chString.split("<br />");
            for (String s : descArray) {
                if (s.indexOf("Location:</strong>") != -1) {
                    this.buoy.location = s.substring(s.indexOf("Location:</strong>") + 18, s.length()).trim();
                } else if (s.indexOf("Wind Direction:</strong>") != -1) {
                    this.buoy.windDirection = s.substring(s.indexOf("Wind Direction:</strong>") + 24, s.length())
                        .trim().replaceAll("&#176;", "");
                } else if (s.indexOf("Wind Speed:</strong>") != -1) {
                    this.buoy.windSpeed = s.substring(s.indexOf("Wind Speed:</strong>") + 20, s.length()).trim();
                } else if (s.indexOf("Wind Gust:</strong>") != -1) {
                    this.buoy.windGust = s.substring(s.indexOf("Wind Gust:</strong>") + 19, s.length()).trim();
                } else if (s.indexOf("Significant Wave Height:</strong>") != -1) {
                    this.buoy.waveHeight = s.substring(s.indexOf("Significant Wave Height:</strong>") + 33, s.length())
                        .trim();
                } else if (s.indexOf("Dominant Wave Period:</strong>") != -1) {
                    this.buoy.wavePeriod = s.substring(s.indexOf("Dominant Wave Period:</strong>") + 30, s.length())
                        .trim();
                } else if (s.indexOf("Atmospheric Pressure:</strong>") != -1) {
                    this.buoy.atmoPressure = s.substring(s.indexOf("Atmospheric Pressure:</strong>") + 30, s.length())
                        .trim();
                } else if (s.indexOf("Pressure Tendency:</strong>") != -1) {
                    this.buoy.atmoPressureTendency = s.substring(s.indexOf("Pressure Tendency:</strong>") + 27,
                        s.length()).trim();
                } else if (s.indexOf("Air Temperature:</strong>") != -1) {
                    this.buoy.airTemp = s.substring(s.indexOf("Air Temperature:</strong>") + 25, s.length()).trim()
                        .replaceAll("&#176;", "");
                } else if (s.indexOf("Water Temperature:</strong>") != -1) {
                    this.buoy.waterTemp = s.substring(s.indexOf("Water Temperature:</strong>") + 27, s.length()).trim()
                        .replaceAll("&#176;", "");
                }
            }
        }

        if (this.inLink) {
            this.buoy.link = chString;
        }

        if (this.inGeoRss) {
            this.buoy.geoRssPoint = chString;
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(final String namespaceURI, final String localName, final String qName) throws SAXException {
        if (localName.equals(NDBCHandler.ITEM)) {
            this.inItem = false;
            this.buoys.add(this.buoy);
        }

        if (this.inItem) {
            if (localName.equals(NDBCHandler.TITLE)) {
                this.inTitle = false;
            } else if (localName.equals(NDBCHandler.PUBDATE)) {
                this.inPubDate = false;
            } else if (localName.equals(NDBCHandler.DESC)) {
                this.inDesc = false;
            } else if (localName.equals(NDBCHandler.LINK)) {
                this.inLink = false;
            } else if (localName.equals(NDBCHandler.GEORSS)) {
                this.inGeoRss = false;
            }
        }
    }

    public ArrayList<BuoyData> getBuoyDataList() {
        return this.buoys;
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(final String namespaceURI, final String localName, final String qName,
        final Attributes atts) throws SAXException {
        if (localName.equals(NDBCHandler.ITEM)) {
            this.inItem = true;
            this.buoy = new BuoyData();
        }

        if (this.inItem) {
            if (localName.equals(NDBCHandler.TITLE)) {
                this.inTitle = true;
            } else if (localName.equals(NDBCHandler.PUBDATE)) {
                this.inPubDate = true;
            } else if (localName.equals(NDBCHandler.DESC)) {
                this.inDesc = true;
            } else if (localName.equals(NDBCHandler.LINK)) {
                this.inLink = true;
            } else if (localName.equals(NDBCHandler.GEORSS)) {
                this.inGeoRss = true;
            }
        }
    }
}
