package pro.android;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;
//import android.content.pm.ActivityInfo;

public class Text1Activity extends Activity 
{
	@Override 
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.texts1);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        TextView  nameValue = (TextView)findViewById(R.id.nameValueText);
        nameValue.setText("John Doe");
        TextView  addrValue = (TextView)findViewById(R.id.addrValueText);
        addrValue.setText("911 Hollywood  Blvd.");

        TextView  tv =(TextView)this.findViewById(R.id.cctvexValueText);
        tv.setText("저의 웹사이트 http://www.sayedhashimi.com을 방문하시거나 sayed@sayedhashimi.com으로 이메일 주시기 바랍니다");
        Linkify.addLinks(tv,  Linkify.ALL);

    }
}
