package org.androidtown.pdf;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 인텐트를 이용해 PDF 문서를 보기 위한 뷰어를 띄우는 방법에 대해 알 수 있습니다.
 *  
 *  이 프로젝트 안의 data 폴더에 있는 sample.pdf 파일을 단말의 SD 카드에 넣었다면,
 *  /sdcard/sample.pdf 라고 입력상자에 입력하면 됩니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	EditText edit01;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        edit01 = (EditText) findViewById(R.id.edit01);
        
        // 버튼을 눌렀을 때 PDF를 열기 위해 정의한 openPDF() 메소드를 호출합니다.
        Button openBtn = (Button) findViewById(R.id.openBtn);
        openBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// 입력한 파일명을 가져옵니다.
        		String filename = edit01.getText().toString();
        		if (filename.length() > 0) {
        			openPDF(filename.trim());
        		} else {
        			Toast.makeText(getApplicationContext(), "PDF 파일명을 입력하세요.", Toast.LENGTH_SHORT).show();
        		}
        	}
        });
        
    }
    
    /**
     * PDF 파일을 열기 위해 정의한 메소드
     * 
     * @param contentsPath
     */
    public void openPDF(String contentsPath) {
    	File file = new File(contentsPath);

        if (file.exists()) {
        	// 입력한 파일 정보로 Uri 객체 생성
            Uri path = Uri.fromFile(file);
            
            // 인텐트 객체를 만들고 setDataAndType() 메소드로 Uri 객체 설정
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
            	// 액티비티 띄우기
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
            	Toast.makeText(this, "PDF 파일을 보기 위한 뷰어 앱이 없습니다.", Toast.LENGTH_SHORT).show();
            }
        } else {
        	Toast.makeText(this, "PDF 파일이 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
