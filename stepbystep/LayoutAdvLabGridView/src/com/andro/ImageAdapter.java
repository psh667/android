package com.andro;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {    
	private Context mContext;   
	
	public ImageAdapter(Context c) {       
		mContext = c;    
	}    
	
	// 이미지셋에 있는아이템의 수를 반환함(그리드뷰는 아이템의 수에 해당하는 행렬을 준비함)  
	public int getCount() {        
		return mThumbIds.length;    
	}
	
	public Object getItem(int position) {        
		return null;    
	}
	
	public long getItemId(int position) {        
		return 0;    
	}    
	
	// 주어진 위치(position)에 출력할 이미지를 반환함
	public View getView(int position, View convertView, ViewGroup parent) {        
		ImageView imageView;        
		if (convertView == null) {              
			imageView = new ImageView(mContext);            
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));            
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);            
			imageView.setPadding(8, 8, 8, 8);        
	    } else {            
			imageView = (ImageView) convertView;        
		}        
		
		// 이미지뷰에 주어진 위치의 이미지를 설정함 
		imageView.setImageResource(mThumbIds[position]);        
		return imageView;    
	}    
	
	// 출력될 이미지 데이터셋(res/drawable 폴더)    
	private Integer[] mThumbIds = {            
			R.drawable.sample_0, 
			R.drawable.sample_1,            
			R.drawable.sample_2,            
			R.drawable.sample_3,            
			R.drawable.sample_4, 
			R.drawable.sample_5,            
			R.drawable.sample_6,            
			R.drawable.sample_7,            
			R.drawable.sample_0, 
			R.drawable.sample_1,            
			R.drawable.sample_2,            
			R.drawable.sample_3,            
			R.drawable.sample_4, 
			R.drawable.sample_5,            
			R.drawable.sample_6,            
			R.drawable.sample_7 };            
}