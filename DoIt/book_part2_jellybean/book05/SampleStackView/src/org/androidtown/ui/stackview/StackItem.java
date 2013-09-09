package org.androidtown.ui.stackview;

import android.graphics.drawable.Drawable;

public class StackItem {
 
	public String itemText;
	public Drawable itemPhoto;
		 
	public StackItem(String text, Drawable photo) {
		if (photo != null) {
			this.itemPhoto = photo;
		}
		
		this.itemText = text;
	}

}
