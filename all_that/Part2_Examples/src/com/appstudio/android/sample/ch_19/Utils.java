package com.appstudio.android.sample.ch_19;

import java.util.List;

import android.os.Handler;
import android.widget.TextView;

public class Utils {
	private static Tracker mTracker = Tracker.getInstance();
	
	 public static void printStatus(final TextView viewMethods) {
	      Handler handler = new Handler();
	      handler.postDelayed(new Runnable() {
	        public void run() {

	        	StringBuilder sbMethods = new StringBuilder();
	        	List<String> listMethods = mTracker.getMethodList();
	        	for (String method : listMethods) {
	        		sbMethods.insert(0, method + "\r\n");
	        	}
	        	if(viewMethods != null) {
	        		viewMethods.setText(sbMethods.toString());
	        	}
	        }
	      }, 1000);
	    }
	
}
