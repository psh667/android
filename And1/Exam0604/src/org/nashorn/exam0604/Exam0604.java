package org.nashorn.exam0604;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Exam0604 extends Activity {
	
	private String[] items = {"리플레이", "딸기노트", "야망의신화"};
	private int[] images = {R.drawable.item01, R.drawable.item02, R.drawable.item03};
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ListView list = (ListView)findViewById(R.id.list);
        
        IconicAdapter iAdapter = new IconicAdapter(this);
    	list.setAdapter(iAdapter); 
    }
    
    class IconicAdapter extends ArrayAdapter {
		Activity context;
		
		IconicAdapter(Activity context)
		{
			super(context, R.layout.list_item, items);
			
			this.context = context;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.list_item, null);
			
			ImageView itemImage = (ImageView)row.findViewById(R.id.list_img);
			itemImage.setImageResource(images[position]);
			TextView nameText = (TextView)row.findViewById(R.id.list_txt_name);
			nameText.setText(items[position]);
			
			TextView companyText = (TextView)row.findViewById(R.id.list_txt_company);
			TextView flatformText = (TextView)row.findViewById(R.id.list_txt_flatform);
			
			return row;
		}
	}
}