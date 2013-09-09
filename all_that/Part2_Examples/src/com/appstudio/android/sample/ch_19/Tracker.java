package com.appstudio.android.sample.ch_19;

import java.util.ArrayList;
import java.util.List;

public class Tracker {

	private List<String> mMethodList;
	private static Tracker ourInstance = new Tracker();
	public static int inx = 0;

	public static Tracker getInstance() {
		return ourInstance;
	}

	private Tracker() {
		mMethodList = new ArrayList<String>();
	}

	public List<String> getMethodList() {
		return mMethodList;
	}

	public void clear() {
		mMethodList.clear();
	}

	public void setStatus(String activityName, String status) {
		mMethodList.add(activityName + "(" + (inx++) + "): " + status + "()");
	}
}
