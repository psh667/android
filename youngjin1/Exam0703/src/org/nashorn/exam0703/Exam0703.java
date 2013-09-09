package org.nashorn.exam0703;

import android.app.Activity;
import android.os.Bundle;
import java.io.File;
import android.widget.*;
import android.os.Environment;
import android.os.StatFs;

public class Exam0703 extends Activity {
	private TextView totalInternalMemoryText;
	private TextView availableInternalMemoryText;
	private TextView totalExternalMemoryText;
	private TextView availableExternalMemoryText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        double totalInternalMemory = getTotalInternalMemorySize();
        totalInternalMemoryText = (TextView)findViewById(R.id.total_internal_memory);
        totalInternalMemoryText.setText(String.format("%.3fMB", totalInternalMemory/1024/1024));
        
        double availableInternalMemory = getAvailableInternalMemorySize();
        availableInternalMemoryText = (TextView)findViewById(R.id.available_internal_memory);
        availableInternalMemoryText.setText(String.format("%.3fMB", availableInternalMemory/1024/1024));
        
        double totalExternalMemory = getTotalExternalMemorySize();
        totalExternalMemoryText = (TextView)findViewById(R.id.total_external_memory);
        if (totalExternalMemory == -1)
        	totalExternalMemoryText.setText("0.0GB");
        else
        	totalExternalMemoryText.setText(String.format("%.3fGB", totalExternalMemory/1024/1024/1024));
        
        double availableExternalMemory = getAvailableExternalMemorySize();
        availableExternalMemoryText = (TextView)findViewById(R.id.available_external_memory);
        if (availableExternalMemory == -1)
        	availableExternalMemoryText.setText("0.0GB");
        else
        	availableExternalMemoryText.setText(String.format("%.3fGB", availableExternalMemory/1024/1024/1024));
        
    }
    
	public double getTotalInternalMemorySize() 
	{
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		
		return totalBlocks * blockSize;
	}
	
	public double getAvailableInternalMemorySize() 
	{
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		
		return availableBlocks * blockSize;
	}

	public boolean isExistExternalMemory() 
	{
	    return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
	
	public double getTotalExternalMemorySize() 
	{
		if(isExistExternalMemory()) 
		{
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} 
		else 
		{
			return -1;
		}
	}	
	
	public double getAvailableExternalMemorySize() 
	{
		if(isExistExternalMemory()) 
		{
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			
			return availableBlocks * blockSize;
		} 
		else 
		{
			return -1;
		}
	}
}

