package kr.co.infinity.CallTest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CallTest extends Activity {
    private EditText edit;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        edit = (EditText)findViewById(R.id.edit);

    }
    public void onClickCall(View v){
    	Uri n = Uri.parse("tel: "+ edit.getText());
		startActivity( new Intent(Intent.ACTION_CALL, n) );
    	
    }
    public void onClickDial(View v){
    	Uri n = Uri.parse("tel: "+ edit.getText());
		startActivity( new Intent(Intent.ACTION_DIAL, n) );
  	}
    
}