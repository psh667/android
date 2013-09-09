package org.nashorn.exam0701;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.app.*;
import android.content.DialogInterface;
import java.util.*;

public class Exam0701 extends Activity {
	
	Exam0701 curActivity = this;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button mPsTestButton = (Button)findViewById(R.id.ps_test);
        mPsTestButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try
				{
				       Process ps = Runtime.getRuntime().exec("ps");
				       byte[] msg = new byte[128];
				       int len;
				       String Value = "";
				       while((len = ps.getInputStream().read(msg)) > 0)
				      {
				            Value = Value + new String(msg, 0, len);
				       }
				       
				       Toast.makeText(getBaseContext(), Value, Toast.LENGTH_LONG).show();
				}
				catch(Exception e)
				{
					Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
				}

			}
		});
        
        Button mActivityManagerTestButton = (Button)findViewById(R.id.activity_manager_test);
        mActivityManagerTestButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final ActivityManager activityManager = (ActivityManager)curActivity.getSystemService(ACTIVITY_SERVICE );
				final List<ActivityManager.RunningAppProcessInfo> appList = activityManager.getRunningAppProcesses();
				final ArrayAdapter<ActivityManager.RunningAppProcessInfo> adaptedAppList = 
					new ArrayAdapter<ActivityManager.RunningAppProcessInfo>(curActivity,
							android.R.layout.simple_list_item_single_choice, appList);
				  
				ListView listView = (ListView)findViewById(R.id.process_list);
				  
				listView.setAdapter(adaptedAppList);
				listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						final ActivityManager.RunningAppProcessInfo runApp = appList.get(arg2);
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(curActivity);
						alertDialog.setTitle("프로세스 종료");
						alertDialog.setMessage("선택한 프로세스("+runApp.processName+")을 종료하시겠습니까?");
						alertDialog.setPositiveButton("예", 
			   				new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
									activityManager.restartPackage(runApp.processName);
								}
							});
						alertDialog.setNegativeButton("아니오", 
			   				new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface arg0, int arg1) {
									// TODO Auto-generated method stub
								}
							});
						alertDialog.show();
					}
				});

			}
		});
    }
}