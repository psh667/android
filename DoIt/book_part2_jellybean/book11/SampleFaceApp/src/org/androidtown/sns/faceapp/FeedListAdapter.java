package org.androidtown.sns.faceapp;

import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FeedListAdapter extends BaseAdapter {

	public static String TAG = "FeedListAdapter";

	private Context mContext;

	private ArrayList<FeedItem> mItems = new ArrayList<FeedItem>();

	public FeedListAdapter(Context context) {
		mContext = context;
	}

	public void setListItems(ArrayList<FeedItem> list) {
		mItems = list;
	}

	public void clear() {
		if (mItems != null) {
			mItems.clear();
		}
	}

	public void addItem(FeedItem aItem) {
		mItems.add(aItem);
	}

	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return mItems.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
		return true;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		FeedItemView itemView;
		if (convertView == null) {
			itemView = new FeedItemView(mContext);
		} else {
			itemView = (FeedItemView) convertView;
		}

		try {
			FeedItem curItem = mItems.get(position);

			String name = curItem.getName();
			String date = curItem.getDate();
			String message = curItem.getMessage();
			String pictureUrl = curItem.getPicture();

			itemView.setText(0, name);
			itemView.setText(1, date);
			itemView.setText(2, message);

			if (pictureUrl != null) {
				Log.d(TAG, "Bitmap URL : " + pictureUrl);
				Bitmap curBitmap = BitmapFactory.decodeStream(new URL(pictureUrl).openStream());
				itemView.setIcon(curBitmap);
			} else {
				if (BasicInfo.BasicPicture == null) {
					String basicUrl = "http://profile.ak.fbcdn.net/static-ak/rsrc.php/v1/yo/r/UlIqmHJn-SK.gif";
					Log.d(TAG, "loading basic picture bitmap from : " + basicUrl);

					BasicInfo.BasicPicture = BitmapFactory.decodeStream(new URL(basicUrl).openStream());
				}

				itemView.setIcon(BasicInfo.BasicPicture);
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return itemView;
	}

}
