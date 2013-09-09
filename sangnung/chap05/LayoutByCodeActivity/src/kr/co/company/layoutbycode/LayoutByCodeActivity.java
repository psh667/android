package kr.co.company.layoutbycode;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class LayoutByCodeActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        LinearLayout manager = (LinearLayout)findViewById(R.id.LayoutManager);
        manager.setOrientation(LinearLayout.HORIZONTAL);

        Button button = (Button)findViewById(R.id.Button01);
        button.setText("첫번째 버튼");
    }
}