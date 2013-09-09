package org.nashorn.exam0702;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import android.net.*;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class Exam0702 extends Activity {
	
	Exam0702 curActivity = this;
	
	private String selectPackageName = "";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        PackageManager packagemanager = getPackageManager(); 
        final List<PackageInfo> appList = packagemanager.getInstalledPackages(0);
        ArrayAdapter<PackageInfo> adaptedAppList = new ArrayAdapter<PackageInfo>(this, android.R.layout.simple_list_item_single_choice, appList);
        ListView listView = (ListView)findViewById(R.id.application_list);
        listView.setAdapter(adaptedAppList);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				final PackageInfo runApp = appList.get(arg2);
				
				selectPackageName = runApp.packageName;
			}
		});
    }
    
    public void fileCopy(String src, String dest)
    {
    	try
    	{
	    	InputStream myInput = new FileInputStream(src);
	    	String outFileName = dest;
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	    	byte[] buffer = new byte[1024];
	    	
	    	int total_length = 0;
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    		total_length+=length;
	    	}
	    	total_length+=length;

	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
    	}
    	catch(Exception e)
    	{
    		Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        
        return true;
    }
	
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	   		case R.id.backup:
	   		{
	   			if (selectPackageName.length() > 0)
   				{
					//선택한 패키지 파일 복사
	   				String srcFile = "/data/app/"+selectPackageName+".apk";
	   				String destFile = "/sdcard/"+selectPackageName+".apk";
	   				
		   			try
		   			{
		   				File file = new File(srcFile);
        			    if (file.isFile()) {
        			    	
        			    	fileCopy(srcFile, destFile);
  		   			       
  		   			       Toast.makeText(this, selectPackageName+"을 백업했습니다.", Toast.LENGTH_SHORT).show();
        			    }
        			    else
        			    	Toast.makeText(this, "해당 어플리케이션은 백업할 수 없습니다.", Toast.LENGTH_SHORT).show();
		   			}
		   			catch(Exception e)
		   			{
		   				Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
		   			}
   				}
   				else
   					Toast.makeText(this, "백업할 어플리케이션을 선택하세요.", Toast.LENGTH_SHORT).show();
	   		}
   			return true;
   			
	   		case R.id.uninstall:
   			{
   				if (selectPackageName.length() > 0)
   				{
	   				AlertDialog.Builder alertDialog = new AlertDialog.Builder(curActivity);
					alertDialog.setTitle("어플리케이션 제거");
					alertDialog.setMessage("선택한 어플리케이션("+selectPackageName+")을 제거하시겠습니까?");
					alertDialog.setPositiveButton("예", 
		   				new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								Uri uri = Uri.fromParts("package", selectPackageName, null);
						        Intent it = new Intent(Intent.ACTION_DELETE, uri);
						        startActivity(it); 
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
   			}
   			return true;
	   			

    	}
	    
    	return super.onOptionsItemSelected(item);
    }   	
}