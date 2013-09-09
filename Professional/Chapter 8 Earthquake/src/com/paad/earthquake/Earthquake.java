package com.paad.earthquake;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Earthquake extends Activity {

	private static final int MENU_UPDATE = Menu.FIRST;
	private static final int MENU_PREFERENCES = Menu.FIRST+1;
	private static final int MENU_EARTHQUAKE_MAP = Menu.FIRST+2;
	private static final int QUAKE_DIALOG = 1;
	private static final int SHOW_PREFERENCES = 1;
	
	int minimumMagnitude = 0;
	boolean autoUpdate = false;
	int updateFreq = 0;
	
    ListView earthquakeListView;
    ArrayAdapter<Quake> aa;

    ArrayList<Quake> earthquakes = new ArrayList<Quake>();
	Quake selectedQuake;

	@Override
	public void onCreate(Bundle icicle) {
	    super.onCreate(icicle);
	    setContentView(R.layout.main);

	    earthquakeListView = (ListView)this.findViewById(R.id.earthquakeListView);

	    earthquakeListView.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView _av, View _v, int _index, long arg3) {
	            selectedQuake = earthquakes.get(_index);
	            showDialog(QUAKE_DIALOG);
	        }
	    });

	    int layoutID = android.R.layout.simple_list_item_1;
	    aa = new ArrayAdapter<Quake>(this, layoutID , earthquakes);
	    earthquakeListView.setAdapter(aa);
	    
	    loadQuakesFromProvider();

	    updateFromPreferences();
	    refreshEarthquakes();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);

	    menu.add(0, MENU_UPDATE, Menu.NONE, R.string.menu_update);
	    menu.add(0, MENU_PREFERENCES, Menu.NONE,
	                R.string.menu_preferences);
	    Intent startMap = new Intent(this, EarthquakeMap.class);
	    menu.add(0, MENU_EARTHQUAKE_MAP,
	                Menu.NONE,
	                R.string.menu_earthquake_map).setIntent(startMap);
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);

	    switch (item.getItemId()) {
	        case (MENU_UPDATE): {
	            refreshEarthquakes();
	            return true;
	        }
	        case (MENU_PREFERENCES): {
	            Intent i = new Intent(this, Preferences.class);
	            startActivityForResult(i, SHOW_PREFERENCES);
	            return true;
	        }
	    }
	    return false;
	}
	
	@Override
	public Dialog onCreateDialog(int id) {
	    switch(id) {
	        case (QUAKE_DIALOG) :
	            LayoutInflater li = LayoutInflater.from(this);
	            View quakeDetailsView = li.inflate(R.layout.quake_details, null);

	            AlertDialog.Builder quakeDialog = new AlertDialog.Builder(this);
	            quakeDialog.setTitle("지진 발생 시간");
	            quakeDialog.setView(quakeDetailsView);
	            return quakeDialog.create();
	    }
	    return null;
	}

	@Override
	public void onPrepareDialog(int id, Dialog dialog) {
	    switch(id) {
	        case (QUAKE_DIALOG) :
	            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	            String dateString = sdf.format(selectedQuake.getDate());
	            String quakeText = "진도 " + selectedQuake.getMagnitude() +
	                               "\n" + selectedQuake.getDetails() + "\n" +
	                               selectedQuake.getLink();

	            AlertDialog quakeDialog = (AlertDialog)dialog;
	            quakeDialog.setTitle(dateString);
	            TextView tv = (TextView)quakeDialog.findViewById(R.id.quakeDetailsTextView);
	            tv.setText(quakeText);

	            break;
	    }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == SHOW_PREFERENCES) {
	        updateFromPreferences();
	        refreshEarthquakes();
	    }
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

	            // 이전에 있는 지진 정보들을 모두 삭제한다.
	            earthquakes.clear();
	            loadQuakesFromProvider();

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
	    // 콘텐트 프로바이더가 이 지진 정보를 이미 가지고 있는 것은 아닌지
	    // 그 여부를 확인하기 위한 where 절을 구성한다.
	    String w = EarthquakeProvider.KEY_DATE + " = " + _quake.getDate().getTime();

	    // 이 새로운 지진 정보라면 콘텐트 프로바이더에 넣는다.
	    if (cr.query(EarthquakeProvider.CONTENT_URI, null, w, null, null).getCount()==0){
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
	        earthquakes.add(_quake);

	        addQuakeToArray(_quake);
	    }
	}

	private void addQuakeToArray(Quake _quake) {
	    if (_quake.getMagnitude() > minimumMagnitude) {
	        // Add the new quake to our list of earthquakes.
	        earthquakes.add(_quake);

	        // Notify the array adapter of a change.
	        aa.notifyDataSetChanged();
	    }
	}

	private void loadQuakesFromProvider() {
	    // 지진 정보 배열에 있는 기존 내용을 비운다.
	    earthquakes.clear();

	    ContentResolver cr = getContentResolver();

	    // 저장된 지진 정보를 모두 가져온다.
	    Cursor c = cr.query(EarthquakeProvider.CONTENT_URI, null, null, null, null);

	    if (c.moveToFirst()) {
	        do {
	            // 지진의 세부정보를 얻어온다.
	            Long datems = c.getLong(EarthquakeProvider.DATE_COLUMN);
	            String details = c.getString(EarthquakeProvider.DETAILS_COLUMN);
	            Float lat = c.getFloat(EarthquakeProvider.LATITUDE_COLUMN);
	            Float lng = c.getFloat(EarthquakeProvider.LONGITUDE_COLUMN);
	            Double mag = c.getDouble(EarthquakeProvider.MAGNITUDE_COLUMN);
	            String link = c.getString(EarthquakeProvider.LINK_COLUMN);

	            Location location = new Location("dummy");
	            location.setLongitude(lng);
	            location.setLatitude(lat);

	            Date date = new Date(datems);

	            Quake q = new Quake(date, details, location, mag, link);
	            addQuakeToArray(q);
	        } while(c.moveToNext());
	    }
	}
	
	private void updateFromPreferences() {
	    Context context = getApplicationContext();
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

	    autoUpdate = prefs.getBoolean(Preferences.PREF_AUTO_UPDATE, false);
	    minimumMagnitude = Integer.parseInt(prefs.getString(Preferences.PREF_MIN_MAG, "0"));
	    updateFreq = Integer.parseInt(prefs.getString(Preferences.PREF_UPDATE_FREQ, "0"));
	}
}