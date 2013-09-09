package org.androidtown.lbs.mapoverlay;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class BankItemOverlay extends ItemizedOverlay<OverlayItem> {

	private List<OverlayItem> items = new ArrayList<OverlayItem>();
	Context context;
	
	public BankItemOverlay(Drawable drawable, Context context) {
		super(boundCenterBottom(drawable));
		this.context = context;
	}
	
	public void addOverlay(OverlayItem overlay) {
		items.add(overlay);
	    populate();
	}

	protected OverlayItem createItem(int i) {
	  return items.get(i);
	}

	public int size() {
		return items.size();
	}
	
	protected boolean onTap(int index){
		OverlayItem item = items.get(index);
		Log.d("BankItemOverlay" , "Tap on item #" + index + " : " + item.getTitle() + ", " + item.getSnippet());
		
		return true;
	}
	
}
