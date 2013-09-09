package com.androidside.intentdemod1;

import java.io.File;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.view.View;

public class IntentDemoD1 extends Activity implements View.OnClickListener{
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        findViewById(R.id.call).setOnClickListener(this);
        
        findViewById(R.id.web_search).setOnClickListener(this);
        
        findViewById(R.id.map1).setOnClickListener(this);
        findViewById(R.id.map2).setOnClickListener(this);
        findViewById(R.id.map3).setOnClickListener(this);
        
        findViewById(R.id.content).setOnClickListener(this);
        findViewById(R.id.audio1).setOnClickListener(this);
        findViewById(R.id.audio2).setOnClickListener(this);
        
        findViewById(R.id.video).setOnClickListener(this);
        
        findViewById(R.id.browser).setOnClickListener(this);
        
        findViewById(R.id.email1).setOnClickListener(this);
        findViewById(R.id.email2).setOnClickListener(this);
        findViewById(R.id.email3).setOnClickListener(this);
        
        findViewById(R.id.sms).setOnClickListener(this);
        
        findViewById(R.id.image1).setOnClickListener(this);
        findViewById(R.id.image2).setOnClickListener(this);        
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
        case R.id.call: 
            Intent call= new Intent();
            call.setAction(android.content.Intent.ACTION_CALL);
            call.setData(Uri.parse("tel:0112345678"));
            startActivity(call);
            break;
            
        case R.id.web_search:
            Intent web_search = new Intent(Intent.ACTION_WEB_SEARCH);
            web_search.putExtra(SearchManager.QUERY, "안드로이드사이드");
            startActivity(web_search);
            break;
            
        case R.id.map1:
            Intent map1 = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=서울"));
            startActivity(map1);
            break;

        case R.id.map2:
            Intent map2 = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.484241,126.929651"));
            startActivity(map2);
            break;
            
        case R.id.map3:
            Intent map3 =  new Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.484241,126.929651?z=15"));
            startActivity(map3);
            break;
            
        case R.id.content:
            Intent content = new Intent();
            content.setAction(android.content.Intent.ACTION_VIEW);
            content.setData(ContactsContract.Contacts.CONTENT_URI);
            startActivity(content);
            break;

        case R.id.audio1:
            Intent audio1 = new Intent(Intent.ACTION_GET_CONTENT);
            audio1.setType("audio/*");
            startActivity(Intent.createChooser(audio1, "오디오 어플 선택"));
            break;            
            
        case R.id.audio2:            
            Intent audio2 = new Intent(); 
            audio2.setAction(android.content.Intent.ACTION_VIEW); 
            File file = new File("/sdcard/test.mp3"); 
            audio2.setDataAndType(Uri.fromFile(file), "audio/*"); 
            startActivity(audio2);
            break;  
           
        case R.id.video:
            Intent video = new Intent(); 
            video.setAction(android.content.Intent.ACTION_VIEW); 
            File videofile = new File("/sdcard/test.mp4"); 
            video.setDataAndType(Uri.fromFile(videofile), "video/*"); 
            startActivity(video); 
            break; 
            
        case R.id.browser:
            Uri browserUri = Uri.parse("http://www.androidside.com");
            Intent browser = new Intent(Intent.ACTION_VIEW, browserUri);
            startActivity(browser); 
            break;

        case R.id.email1:            
            Uri email1Uri = Uri.parse("mailto:androidtest@gmail.com");
            Intent email1 = new Intent(Intent.ACTION_SENDTO, email1Uri);
            startActivity(email1);
            break;
            
        case R.id.email2:
            Intent email2 = new Intent(Intent.ACTION_SEND);
            String[] tos = {"androidtest@gmail.com"};
            String[] ccs = {"test@gmail.com"};
            email2.putExtra(Intent.EXTRA_EMAIL, tos);
            email2.putExtra(Intent.EXTRA_CC, ccs);
            email2.putExtra(Intent.EXTRA_SUBJECT, "제목");
            email2.putExtra(Intent.EXTRA_TEXT, "안녕하세요!");
            email2.setType("plain/text");
            startActivity(Intent.createChooser(email2, "이메일 어플 선택"));
            break;
         
        case R.id.email3:   
            File email3File = new File(Environment.getExternalStorageDirectory(), "icon.png");
                        
            Intent email3 = new Intent(Intent.ACTION_SEND);             
            email3.setType("plain/text");              
            email3.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"test@gmail.com"}); 
 
            email3.putExtra(Intent.EXTRA_SUBJECT, "제목"); 
            email3.putExtra(Intent.EXTRA_TEXT, "안녕하세요!");
            email3.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(email3File));
            startActivity(Intent.createChooser(email3, "이메일 선택"));
            break;
            
        case R.id.sms:
            Uri smsUri = Uri.parse("smsto:0101235678");
            Intent sms = new Intent(Intent.ACTION_VIEW, smsUri);  
            sms.putExtra("sms_body", "안녕하세요!! 안드로이드사이드입니다.");
            startActivity(sms);
            break;        
            
        case R.id.image1:
            Uri image1Uri = Uri.parse("file://"+this.getFilesDir()+"/icon.png");
            Intent image1 = new Intent(Intent.ACTION_VIEW, image1Uri);
            image1.setDataAndType(image1Uri, "image/*");
            startActivity(image1);
            break;
            
        case R.id.image2:            
            Uri image2Uri = Uri.fromFile(new File("/sdcard/icon.png"));
            Intent image2 = new Intent(Intent.ACTION_VIEW, image2Uri);
            image2.setDataAndType(image2Uri, "image/*");
            startActivity(image2);
            break;
        }
        
    }
    
    
}