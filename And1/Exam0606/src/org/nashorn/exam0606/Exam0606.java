package org.nashorn.exam0606;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Exam0606 extends Activity {
	private ArrayList<String> list;
	
	private ListView listView = null;
	
	private ArrayAdapter<String> arrayAdapter;
	
	private String[] items;
	private Bitmap[] bitmapList;
	
	private IconicAdapter iAdapter = null;
	
	private Handler mHandler = new Handler(); 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        listView = (ListView)findViewById(R.id.list);
		
		list = new ArrayList<String>();
		
		new makeList().execute("");
    }
    
    private class makeList extends AsyncTask<String, Void, Void> {   
        private ProgressDialog Dialog = new ProgressDialog(Exam0606.this);   
           
        protected void onPreExecute() {   
            Dialog.setMessage("파일 목록을 만드는 중입니다. 잠시만 기다리세요.");   
            Dialog.show();   
        }   
  
        protected Void doInBackground(String... urls) {   
        	searchFile(new File("/sdcard"));
            return null;   
        }   
           
        protected void onPostExecute(Void unused) {   
            Dialog.dismiss();   
            
            items = new String[list.size()];
    		bitmapList = new Bitmap[list.size()];
    		for (int i = 0; i < list.size(); i++)
    		{
    			items[i] = list.get(i);
    			bitmapList[i] = null;
    		}

    		iAdapter = new IconicAdapter(Exam0606.this);
    		listView.setAdapter(iAdapter);
    		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    			@Override
    			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
    					long arg3) {
    			}
    		});    
      		
      		new Thread(new Runnable() { 
      			public void run() { 
      				for (int i = 0; i < list.size(); i++)
      	    		{
      					try
      					{
      						BitmapFactory.Options options = new BitmapFactory.Options();
    	            		options.inSampleSize = 8;
    	            		Bitmap bm = BitmapFactory.decodeFile(list.get(i), options);
    	            		
    	            		Matrix rotateMatrix = new Matrix();
    	        			rotateMatrix.preRotate(90);
    	        			
    	        			Bitmap rotateImage = Bitmap.createBitmap(bm,
    	        					0, 0, bm.getWidth(), bm.getHeight(),
    	        					rotateMatrix, false);
    	            		
    	            		
    						bitmapList[i] = Bitmap.createScaledBitmap(rotateImage, 32, 32, true);
          				
          					mHandler.post(new Runnable() { 
          						public void run() { 
          							listView.invalidateViews();
          						} 
          					}); 	
      					}
      					catch(Exception e)
      					{
      						
      					}
      				} 
      			} 
      		}).start(); 
        }   
           
    }
	
	private void fill(File[] files) {
		for (File currentFile : files){
			if (currentFile.isDirectory()) {
				searchFile(new File(currentFile.getAbsolutePath()));
			}else{
				String fileName = currentFile.getAbsolutePath();
				
				if (fileName.matches(".*.jpg") || fileName.matches(".*.JPG"))
				{
					list.add(fileName);
				}
			}
		}
	}
	
	public void searchFile(final File aDirectory)
	{
		fill(aDirectory.listFiles());
	}
	
	class IconicAdapter extends ArrayAdapter {
		Activity context;
		
		IconicAdapter(Activity context)
		{
			super(context, R.layout.list_row, items);
			this.context = context;
		}
		
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.list_row, null);

			TextView label = (TextView)row.findViewById(R.id.label);
			label.setText(items[position]);
			
			ImageView icon = (ImageView)row.findViewById(R.id.icon);
			if (bitmapList[position] != null)
			{
				icon.setImageBitmap(bitmapList[position]);
			}
			
			return row;
		}
	}
}