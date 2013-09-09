package com.msi.manning.windwaves;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.msi.manning.windwaves.data.BuoyData;

import java.util.List;

/**
 * Map Overlay class that extends ItemizedOverlay - puts the same marker on the map for each
 * OverlayItem, and handles tap events, etc.
 * 
 * @author charliecollins
 * 
 */
public class BuoyItemizedOverlay extends ItemizedOverlay<BuoyOverlayItem> {

    private static final String CLASSTAG = BuoyItemizedOverlay.class.getSimpleName();

    private final List<BuoyOverlayItem> items;
    private final Context context;

    public BuoyItemizedOverlay(final List<BuoyOverlayItem> items, final Drawable defaultMarker, final Context context) {
        super(defaultMarker);
        this.items = items;
        this.context = context;

        // after the list is ready, you have to call populate (before draw is automatically invoked)
        populate();
    }

    @Override
    public BuoyOverlayItem createItem(final int i) {
        return this.items.get(i);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent e, MapView v) {
        // /Toast.makeText(this.context, "trackballEvent", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected boolean onTap(final int i) {
        Log.d(Constants.LOGTAG, BuoyItemizedOverlay.CLASSTAG + " item with index " + i + " tapped");
        final BuoyData bd = this.items.get(i).buoyData;
        Log.d(Constants.LOGTAG, BuoyItemizedOverlay.CLASSTAG + " selected buoyData - " + bd);

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View bView = inflater.inflate(R.layout.buoy_selected, null);
        TextView title = (TextView) bView.findViewById(R.id.buoy_title);
        title.setText(bd.title);
        TextView date = (TextView) bView.findViewById(R.id.buoy_date);
        date.setText(bd.dateString);
        TextView location = (TextView) bView.findViewById(R.id.buoy_location);
        location.setText(bd.location);

        String airTemp = "NA";
        if (bd.airTemp != null) {
            airTemp = bd.airTemp;
        }
        String waterTemp = "NA";
        if (bd.waterTemp != null) {
            waterTemp = bd.waterTemp;
        }
        String windSpeed = "NA";
        if (bd.windSpeed != null) {
            windSpeed = bd.windSpeed;
        }
        String waveHeight = "NA";
        if (bd.waveHeight != null) {
            waveHeight = bd.waveHeight;
        }

        TextView atView = (TextView) bView.findViewById(R.id.cond_airtemp);
        atView.setText("Air temp: " + airTemp);
        TextView wtView = (TextView) bView.findViewById(R.id.cond_watertemp);
        wtView.setText("Water temp: " + waterTemp);
        TextView wsView = (TextView) bView.findViewById(R.id.cond_windspeed);
        wsView.setText("Wind speed: " + windSpeed);
        TextView whView = (TextView) bView.findViewById(R.id.cond_waveheight);
        whView.setText("Wave height: " + waveHeight);

        new AlertDialog.Builder(this.context).setView(bView).setPositiveButton("More Detail",
            new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface di, int what) {
                    Intent intent = new Intent(context, BuoyDetailActivity.class);
                    // quick and dirty hack to set data on another activity
                    // (not really ideal, but don't need a Parcelable here either, and don't want to
                    // pass in Bundle separately, etc)
                    BuoyDetailActivity.buoyData = bd;
                    context.startActivity(intent);
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int what) {
                di.dismiss();
            }
        }).show();

        return true;
    }

    @Override
    public int size() {
        return this.items.size();
    }

    @Override
    public void draw(Canvas canvas, MapView mapView, boolean b) {
        super.draw(canvas, mapView, false);
        // example of manual drawing it, here we are letting ItemizedOverlay handle it
        // Bitmap buoy = BitmapFactory.decodeResource(mapView.getResources(), R.drawable.buoy_30);
        // bitmap, x, y, Paint
        // canvas.drawBitmap(buoy, 50, 50, null);        
    }
}
