// LaunchedActivity.java
package com.corea.IntentExplicitDemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//StartActivityForResult 메소드에 의해 호출되는 Activity
public class AnotherActivity extends Activity {

    //실행 시에 인텐트에 전달된 데이터 키
    public static final String TEXT_INPUT = "TextInput";
    //종료 시에 인텐트에 전달된 데이터 키
    public static final String TEXT_RESULT = "TextResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anotherview);

        //인텐트로부터 Parameter를 읽어 드림
        String input = getIntent().getStringExtra(TEXT_INPUT);
        if (input != null) {
            EditText edit = (EditText) findViewById(R.id.result_text);
            edit.setText(input);
        }

        Button finish = (Button) findViewById(R.id.finish_button);
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //버튼을 누르면 종료하다
                terminate();
            }
        });
    }

    //텍스트 박스의 내용을 인텐트에 설정하고 Activity를 종료
    private void terminate() {
        EditText edit = (EditText) findViewById(R.id.result_text);
        String result = edit.getText().toString();
        if (result.length() != 0) {
            //텍스트박스에 문자가 입력되어 있으면 인텐트에 설정
            Intent i = new Intent();
            i.putExtra(TEXT_RESULT, result);
            setResult(RESULT_OK, i);
        } else {
            //텍스트박스에 문자가 입력되어 있지 않으면 결과는 취소(cancel)
            setResult(RESULT_CANCELED);
        }
        // Activity를 종료하다
        finish();
    }
}
