package org.nashorn.exam0805;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class Exam0805 extends Activity {
	private ChartView mChartView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LinearLayout dynamicLayout = (LinearLayout)findViewById(R.id.layout);
        mChartView = new ChartView(this);
        dynamicLayout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        
        mChartView.makeChart();
    }
}