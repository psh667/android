package org.nashorn.exam0805;

import android.util.AttributeSet;
import android.view.*;
import android.content.Context;
import android.graphics.*;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.chart.PieChart;

public class ChartView extends View
{
	private PieChart mPieChart = null;
	
	private Exam0805 parent;
	
	public ChartView(Context context)
	{
		super(context);
		setFocusable(true);
		parent = (Exam0805)context;
	}
	public ChartView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		setFocusable(true);
		parent = (Exam0805)context;
	}
	public ChartView(Context context, AttributeSet attrs, int defaultStyle)
	{
		super(context, attrs, defaultStyle);
		setFocusable(true);
		parent = (Exam0805)context;
	}
	
	public void makeChart()
	{
		double[] values = new double[] {10, 20, 30, 40};
	    int[] colors = new int[] {Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GREEN };
	    String[] texts = new String[] {"SAMPLE1", "SAMPEL2", "SAMPLE3", "SAMPLE4" };

	    DefaultRenderer renderer = new DefaultRenderer();
	    for (int color : colors) {
	      SimpleSeriesRenderer ssr = new SimpleSeriesRenderer();
	      ssr.setColor(color);
	      renderer.addSeriesRenderer(ssr);
	    }
	    
	    CategorySeries series = new CategorySeries("Project budget");
	    int count = 0;
	    for (double value : values) {
	    	series.add(texts[count++], value);
	    }

		mPieChart = new PieChart(series, renderer);
		
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		
		if (mPieChart != null)
			mPieChart.draw(canvas, 0, 0, width, height);
	}
}