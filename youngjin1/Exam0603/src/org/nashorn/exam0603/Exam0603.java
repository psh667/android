package org.nashorn.exam0603;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Exam0603 extends Activity {
	
	private ArrayList<String> directoryList;
	private ArrayList<String> fileNameList;
	private File currentDirectory = new File("/");
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        fileNameList = new ArrayList<String>();
        directoryList = new ArrayList<String>();
        
        browseTo(currentDirectory);
    }
    
    private void fill(File[] files) {
		
		fileNameList.clear();
		directoryList.clear();
		
		if (currentDirectory.getParent() != null)
		{
			fileNameList.add("[..]");
			directoryList.add("..");
		}
		
		for (File currentFile : files){
			
			if (currentFile.isDirectory()) 
			{
				fileNameList.add("["+currentFile.getName()+"]");
				directoryList.add(currentFile.getAbsolutePath());
			}
			else
			{
				fileNameList.add(currentFile.getName());
				directoryList.add(currentFile.getAbsolutePath());
			}
		}
		
		ListView listView = (ListView)findViewById(R.id.list);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
			android.R.layout.simple_list_item_1, fileNameList);

  		listView.setAdapter(arrayAdapter);	
 		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String selectedFileString = directoryList.get(arg2);
				if(selectedFileString.equals(".."))
				{
					upOneLevel();
				} 
				else 
				{
					File clickedFile = null;
					clickedFile = new File(selectedFileString);
					if(clickedFile != null)
					{
						browseTo(clickedFile);
					}
				}
			}
		});
	}
    
    private void browseTo(final File aDirectory)
    {
		if (aDirectory.isDirectory())
		{
			try
			{
				if (aDirectory.listFiles() != null)
				{
					this.currentDirectory = aDirectory;
					fill(aDirectory.listFiles());
				}
			}
			catch(Exception e)
			{
				Toast.makeText(getBaseContext(),e.toString(), Toast.LENGTH_SHORT).show();
			}
		}
	}
    
    private void upOneLevel()
    {
		if(currentDirectory.getParent() != null)
		{
			this.browseTo(currentDirectory.getParentFile());
		}
	}
}