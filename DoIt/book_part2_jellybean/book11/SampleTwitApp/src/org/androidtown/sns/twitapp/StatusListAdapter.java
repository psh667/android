package org.androidtown.sns.twitapp;

import java.net.URL;
import java.util.Date;
import java.util.List;

import twitter4j.Status;
import twitter4j.User;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StatusListAdapter extends BaseAdapter {

	public static String TAG = "StatusListAdapter";

	private Context mContext;

	private List<Status> mStatuses = null;

	Handler mHandler;
	
	public StatusListAdapter(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
	}

	public void setListItems(List<Status> list) {
		mStatuses = list;
	}

	public int getCount() {
		if (mStatuses == null) {
			return 0;
		} else {
			return mStatuses.size();
		}
	}

	public Object getItem(int position) {
		if (mStatuses == null) {
			return null;
		} else {
			return mStatuses.get(position);
		}
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
		StatusItemView itemView;
		if (convertView == null) {
			itemView = new StatusItemView(mContext);
		} else {
			itemView = (StatusItemView) convertView;
		}

		try {
			Status curStatus = mStatuses.get(position);

			User user = curStatus.getUser();
			String userName = user.getName();
			String userScreenName = user.getScreenName();
			URL url = new URL(user.getProfileImageURL());

			Date date = curStatus.getCreatedAt();
			String data = curStatus.getText();

			itemView.setText(0, userScreenName);

			String dateStr = BasicInfo.DateFormat.format(date);
			itemView.setText(1, dateStr);

			itemView.setText(2, data);

			if (url != null) {
				Log.d(TAG, "Bitmap URL : " + url);
				
				GetBitmapThread thread = new GetBitmapThread(itemView, url);
				thread.start();
				
			}

		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return itemView;
	}

	class GetBitmapThread extends Thread {
		StatusItemView itemView;
		URL url;
		
		public GetBitmapThread(StatusItemView view, URL inUrl) {
			itemView = view;
			url = inUrl;
		}
		
		public void run() {
			try {
			final Bitmap curBitmap = BitmapFactory.decodeStream(url.openStream());
				
				mHandler.post(new Runnable() {
					public void run() {
						itemView.setIcon(curBitmap);
					}
				});
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
