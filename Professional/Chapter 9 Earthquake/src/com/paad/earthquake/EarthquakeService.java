package com.paad.earthquake;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.IBinder;
import android.preference.PreferenceManager;

public class EarthquakeService extends Service {
	
	public static final String NEW_EARTHQUAKE_FOUND = "New_Earthquake_Found";
	
	private Timer updateTimer;
	
	private int minimumMagnitude = 0;
	private boolean autoUpdate = false;
	private int updateFreq = 0;
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    // 공유 환경설정을 얻어온다.
	    Context context = getApplicationContext();
	    SharedPreferences prefs =
	        PreferenceManager.getDefaultSharedPreferences(context);

	    autoUpdate = 
	    	prefs.getBoolean(Preferences.PREF_AUTO_UPDATE, false);
	    minimumMagnitude = 
	    	Integer.parseInt(prefs.getString(Preferences.PREF_MIN_MAG, "0"));
	    updateFreq = 
	    	Integer.parseInt(prefs.getString(Preferences.PREF_UPDATE_FREQ, "0"));

	    updateTimer.cancel();
	    
	    if(autoUpdate) {
	        updateTimer = new Timer("earthquakeUpdates");
	        
	        updateTimer.scheduleAtFixedRate(new TimerTask() {
	    	    public void run() {
	    	        refreshEarthquakes();
	    	    }
	    	}, 0, updateFreq*60*1000);
	    }

	    refreshEarthquakes();

	    return Service.START_STICKY;
	};
	
	@Override
	public void onCreate() {
	    updateTimer = new Timer("earthquakeUpdates");
	}

	private TimerTask doRefresh = new TimerTask() {
	    public void run() {
	        refreshEarthquakes();
	    }
	};

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
	private void refreshEarthquakes() {
	    // XML을 가져온다.
	    URL url;
	    try {
	        String quakeFeed = getString(R.string.quake_feed);
	        url = new URL(quakeFeed);

	        URLConnection connection;
	        connection = url.openConnection();

	        HttpURLConnection httpConnection = (HttpURLConnection)connection;
	        int responseCode = httpConnection.getResponseCode();

	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            InputStream in = httpConnection.getInputStream(); 
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();

	            // 지진 정보 피드를 파싱한다.
	            Document dom = db.parse(in);
	            Element docEle = dom.getDocumentElement();

	            // 지진 정보로 구성된 리스트를 얻어온다.
	            NodeList nl = docEle.getElementsByTagName("entry");
	            if (nl != null && nl.getLength() > 0) {
	                for (int i = 0 ; i < nl.getLength(); i++) {
	                    Element entry = (Element)nl.item(i);
	                    Element title = (Element)entry.getElementsByTagName("title").item(0);
	                    Element g = (Element)entry.getElementsByTagName("georss:point").item(0);
	                    Element when = (Element)entry.getElementsByTagName("updated").item(0);
	                    Element link = (Element)entry.getElementsByTagName("link").item(0);

	                    String details = title.getFirstChild().getNodeValue();
	                    String linkString = link.getAttribute("href");

	                    String point = g.getFirstChild().getNodeValue();
	                    String dt = when.getFirstChild().getNodeValue();
	                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
	                    Date qdate = new GregorianCalendar(0,0,0).getTime();
	                    try {
	                        qdate = sdf.parse(dt);
	                    } catch (ParseException e) {
	                        e.printStackTrace();
	                    }

	                    String[] location = point.split(" ");
	                    Location l = new Location("dummyGPS");
	                    l.setLatitude(Double.parseDouble(location[0]));
	                    l.setLongitude(Double.parseDouble(location[1]));

	                    String magnitudeString = details.split(" ")[1];
	                    int end = magnitudeString.length()-1;
	                    double magnitude = Double.parseDouble(magnitudeString.substring(0, end));

	                    details = details.split(",")[1].trim();

	                    Quake quake = new Quake(qdate, details, l, magnitude, linkString);

	                    // 새로운 지진 정보를 처리한다.
	                    addNewQuake(quake);
	                }
	            }
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ParserConfigurationException e) {
	        e.printStackTrace();
	    } catch (SAXException e) {
	        e.printStackTrace();
	    } finally {
	    }
	}

	private void addNewQuake(Quake _quake) {
	    ContentResolver cr = getContentResolver();

	    // 이 지진 정보가 콘텐트 프로바이더에 이미 존재하고 있지는 않은지
	    // 확인하기 위한 where 절을 구성한다.
	    String w = EarthquakeProvider.KEY_DATE + " = " + _quake.getDate().getTime();

	    // 새로운 지진 정보라면 콘텐트 프로바이더에 넣는다.
	    Cursor c = cr.query(EarthquakeProvider.CONTENT_URI, null, w, null, null);
	    if (c.getCount()==0){
	        ContentValues values = new ContentValues();

	        values.put(EarthquakeProvider.KEY_DATE, _quake.getDate().getTime());
	        values.put(EarthquakeProvider.KEY_DETAILS, _quake.getDetails());
	        double lat = _quake.getLocation().getLatitude();
	        double lng = _quake.getLocation().getLongitude();
	        values.put(EarthquakeProvider.KEY_LOCATION_LAT, lat);
	        values.put(EarthquakeProvider.KEY_LOCATION_LNG, lng);
	        values.put(EarthquakeProvider.KEY_LINK, _quake.getLink());
	        values.put(EarthquakeProvider.KEY_MAGNITUDE, _quake.getMagnitude());
	        cr.insert(EarthquakeProvider.CONTENT_URI, values);
	        announceNewQuake(_quake);
	    }
	    c.close();
	}
	
	private void announceNewQuake(Quake quake) {
	    Intent intent = new Intent(NEW_EARTHQUAKE_FOUND);
	    intent.putExtra("date", quake.getDate().getTime());
	    intent.putExtra("details", quake.getDetails());
	    intent.putExtra("longitude", quake.getLocation().getLongitude());
	    intent.putExtra("latitude", quake.getLocation().getLatitude());
	    intent.putExtra("magnitude", quake.getMagnitude());

	    sendBroadcast(intent);
	}
}
