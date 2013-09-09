package com.corea.DatabaseDemo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UpdateActivity extends Activity {
	
    //** Called when the activity is first created. *//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String alltables = "";														// 테이블의 정보를 갖는 화면에 표시될 String
        setContentView(R.layout.update);											// 업데이트 레이아웃을 부풀린다.
        Button deleteButton = (Button)findViewById(R.id.deleteButton);				// 버튼 객체 생성과, 아이디값 받기
        DBAdapter db = new DBAdapter(this);											// 어뎁터 객체 생성
        AlertDialog.Builder ad = new AlertDialog.Builder(UpdateActivity.this);    	// 테이블을 표시할 다이어로그 객체 생성
        TextView view = (TextView)findViewById(R.id.updateText);					// TextView 값을 받아온다.
        
        //---------------------------------------다이어로그 버튼생성----------------------------------------//
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog, int arg1) {
        		dialog.dismiss();
        	}
        });
        
        //-------------------------------------------DB 열기---------------------------------------------//
        db.open();	 																 // db 오픈
 
        //-------------------------------------------업데이트---------------------------------------------//
        if (db.updateTitle(2, "박 주영", "기계", "1")) {
        	ad.setMessage("항목 2번 수정 성공");									// 1번목록 삭제 완료 표시
        	ad.show();
        }
        else {
           ad.setMessage("항목 2번 수정 실패");								// 1번목록 삭제 실패  표시
           ad.show();
        }
       //----------------------------------------화면보이기-----------------------------------------------//
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
               
        //-----------------------------------------DB 닫기, 삭제-------------------------------------------//
        db.close();
        //deleteDatabase(DBAdapter.getDatabaseName());	// 데이터베이스 파일 삭제

        //------------------------------------------버튼 클릭이벤트------------------------------------------//	
        deleteButton.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				startActivity(new Intent(UpdateActivity.this, DeleteActivity.class));
			}     	
        });
        //------------------------------------------------------------------------------------------------//	
    }
}
