package kr.co.company.buttonevent4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void myClickListener(View target)
    {
		Toast.makeText(getApplicationContext(), "버튼이 눌려졌습니다", 	Toast.LENGTH_SHORT).show();
    }
}
