package com.androidside.demoteimagedemoa1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class RemoteImageDemoA1 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //원격에서 가지고 온 이미지를 보여줄 레이아웃의 ImageView를 가지고 온다.
        ImageView imageView = (ImageView) findViewById(R.id.image1);
        
        //원격에서 가지고 올 이미지를 URL 형식으로 지정한다.
        URL url = null;
        try {
            url = new URL("http://www.androidside.com/banner/banner.jpg");
        } catch (MalformedURLException e) {            
            e.printStackTrace();
        }
                
        //getRemoteImage() 메소드를 호출해서 Bitmap 이미지를 가지고 와서 ImageView에 설정한다.
        imageView.setImageBitmap(getRemoteImage(url));

    }

    //원격에 있는 이미지를 Bitmap 형식으로 반환한다.
    private static Bitmap getRemoteImage(final URL url) {
        Bitmap bitmap = null;
        
        try {
            //주어진 url에 연결한다.
            URLConnection conn = url.openConnection();
            conn.connect();
            
            //이미지를 가지고 와서 decodeStream() 메소드로 Bitmap 이미지를 만든다.
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            bitmap = BitmapFactory.decodeStream(bis);
            bis.close();            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return bitmap;
    }
}
