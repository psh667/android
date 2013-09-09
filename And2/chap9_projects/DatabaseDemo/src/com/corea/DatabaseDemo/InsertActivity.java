package com.corea.DatabaseDemo;

import com.corea.DatabaseDemo.R;

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

public class InsertActivity extends Activity {

    //** Called when the activity is first created. *//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String alltables = "";                                                        // 테이블의 정보를 갖는 화면에 표시될 String
        DBAdapter db = new DBAdapter(this);                                            // DB어댑터의 객체
        setContentView(R.layout.insert);                                            // 현재 페이지의 Insert레이아웃 부풀림
        Button updateButton = (Button) findViewById(R.id.updateButton);                    // 삽입버튼 객체 생성과 레이아웃의 아이디 가져옴
        AlertDialog.Builder ad = new AlertDialog.Builder(InsertActivity.this);      // 테이블을 표시할 다이어로그 객체 생성
        TextView view = (TextView) findViewById(R.id.insertText);

        //-------------------------------------------DB 열기-----------------------------------------------//
        db.open();                                                                      // db 오픈

        //-------------------------------------------삽입--------------------------------------------------//
        long id;
        id = db.insertTitle("최 민호", "컴퓨터", "3");
//        id = db.execSQL("INSERT INTO students(name, dept, grade)" +
//        		"VALUES('모 태범', '컴퓨터', '3')");
        id = db.insertTitle("김 승환", "물리", "4");
        id = db.insertTitle("최 영철", "체육", "2");
        id = db.insertTitle("손 선동", "영문", "1");
        id = db.insertTitle("김 진태", "전자", "3");

        //-----------------------------------------삽입 다이어로그 조작(다이어로그 버튼 추가)-----------------------//
        ad.setMessage("항목 5개 삽입 성공");
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.dismiss();
            }
        });
        ad.show();                                                                // 삽입이 완료되었다는 다이어로그

        //------------------------------------------화면보이기-----------------------------------------------//
        Cursor c = db.getAllTitles();
        if (c.moveToFirst()) {
            do {
                alltables = alltables.concat("번호: " + c.getString(0) + "\n" +
                        "이름: " + c.getString(1) + "\n" +
                        "과: " + c.getString(2) + "\n" +
                        "학년: " + c.getString(3) + "\n" +
                        "-------------------------" + "\n");
            } while (c.moveToNext());
        }
        view.setText(alltables);

        //------------------------------------------DB닫기,삭제--------------------------------------------//
        db.close();
        //deleteDatabase(DBAdapter.getDatabaseName());							// 데이터베이스 파일 삭제

        //------------------------------------------버튼 클릭이벤트------------------------------------------//	
        updateButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                startActivity(new Intent(InsertActivity.this, UpdateActivity.class));
            }
        });
        //--------------------------------------------------------------------------------------------------
    }
}
