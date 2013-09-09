package com.corea.DatabaseDemo;

import com.corea.DatabaseDemo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class DeleteActivity extends Activity {
      	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String alltables = "";														// 테이블의 정보를 갖는 화면에 표시될 String
        DBAdapter db = new DBAdapter(this);											// 어뎁터 객체 생성
        AlertDialog.Builder ad = new AlertDialog.Builder(DeleteActivity.this);  	// 테이블을 표시할 다이어로그 객체 생성
        setContentView(R.layout.delete);											// delete레이아웃을 부풀려준다.
        TextView view = (TextView)findViewById(R.id.deleteText);					// TextView객체생성, 아이디값 받아온다.
      
        //---------------------------------------다이어로그 버튼생성----------------------------------------//
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int arg1) {
        		dialog.dismiss();
        	}
        });
        
        //-------------------------------------------DB 오픈--------------------------------------------//
        db.open();	  // db 오픈
        
        //-------------------------------------------삭제-----------------------------------------------//
        if (db.deleteTitle(1)) {						
            ad.setMessage("항목 1번 삭제 성공");			// 1번목록 삭제 완료 표시
            ad.show();	
        }
        else {
        	ad.setMessage("항목 1번 삭제 실패");			// 1번목록 삭제 실패  표시
        	ad.show();
        }
         
        //----------------------------------------화면보이기---------------------------------------------//
        Cursor c = db.getAllTitles();										
        if(c.moveToFirst()) {
        	do{	
        		alltables = alltables.concat("번호: " + c.getString(0) + "\n" +
                        "이름: " + c.getString(1) + "\n" +
                        "과: " + c.getString(2) + "\n" +
                        "학년: " + c.getString(3) + "\n" +
                        "-------------------------" + "\n");
        	}while(c.moveToNext());
        }
        view.setText(alltables);
        
        //------------------------------------------DB닫기,삭제--------------------------------------------//
        db.close();	
        deleteDatabase(DBAdapter.getDatabaseName());	// 데이터베이스 파일 삭제
        //------------------------------------------------------------------------------------------------//
    }
}

