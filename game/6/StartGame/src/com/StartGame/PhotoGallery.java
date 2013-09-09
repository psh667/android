
//-------------------------------------
// PhotoGallery - Option Menu
//-------------------------------------
package com.StartGame;

import java.io.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.MediaStore.Images;
import android.view.*;
import android.widget.*;

public class PhotoGallery extends Activity {
	
	final int REQ_GALLERY = 1; 
	Uri imgUri;
	ImageView imgView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.photogallery);
        
        imgView = (ImageView) findViewById(R.id.ImageView01);
        imgView.setOnClickListener(OnImageClick);
        
    	Intent intent = new Intent(); 
    	intent.setAction(Intent.ACTION_GET_CONTENT); 
    	intent.setType("image/*"); 
    	startActivityForResult(intent, REQ_GALLERY);
    }
    
    //-----------------------------------
    // Button Click
    //-----------------------------------
    Button.OnClickListener OnImageClick = new Button.OnClickListener() {
		public void onClick(View v) {
			finish();
		}
    };
	
	//---------------------------------
	// PhotpGallery 이미지 읽기
	//---------------------------------
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode != REQ_GALLERY || data == null) return;

    	imgUri = data.getData(); // "/external/images/media/2"
    	try { 
    		// 갤러리 이미지 읽기
    		Bitmap bitmap = Images.Media.getBitmap(getContentResolver(), imgUri);

    		// ImageView에 표시
    		imgView.setImageBitmap(bitmap); 

    		// 이미지 Id 구하기
    		int imgId = (int) ContentUris.parseId(imgUri);
    		
    		// 저장 매체 종류 (내장 메모리 / SD Card)
    		int stgType = 1;
    		if (imgUri.getPath().contains("external")) stgType = 2;

    		// 전역 변수에 쓰기
    		((GlobalVars)getApplicationContext()).setImageId(imgId);
    		((GlobalVars)getApplicationContext()).setStorageType(stgType);

    	} catch (FileNotFoundException e) {
    		//
    	} catch (IOException e) {
    		//
    	} 
   	} 
}
