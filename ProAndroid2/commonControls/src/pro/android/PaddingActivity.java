package pro.android;

import android.app.Activity;
//import android.content.ComponentName;
//import android.content.Intent;
import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.EditText;

public class PaddingActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.padding);

        EditText et1 =(EditText)this.findViewById(R.id.et_padding1);
        et1.setText("one");
        EditText et2 =(EditText)this.findViewById(R.id.et_padding2);
        et2.setText("one");
    }
}
