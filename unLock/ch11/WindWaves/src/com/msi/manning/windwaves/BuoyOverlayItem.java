package com.msi.manning.windwaves;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;
import com.msi.manning.windwaves.data.BuoyData;

/**
 * Simple class to represent each data point we want to display on a Map.
 * 
 * @author charliecollins
 * 
 */
public class BuoyOverlayItem extends OverlayItem {

    public final GeoPoint point;
    public final BuoyData buoyData;

    public BuoyOverlayItem(final GeoPoint point, final BuoyData buoyData) {
        super(point, buoyData.title, buoyData.dateString);
        this.point = point;
        this.buoyData = buoyData;
    }
}
