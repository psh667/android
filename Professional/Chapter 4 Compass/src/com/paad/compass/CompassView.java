package com.paad.compass;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.util.AttributeSet;
import android.content.res.Resources;

public class CompassView extends View {
	
	private Paint markerPaint;
	private Paint textPaint;
	private Paint circlePaint;
	private String northString;
	private String eastString;
	private String southString;
	private String westString;
	private int textHeight;
	private float bearing;
	
    public CompassView(Context context) {
        super(context);
        initCompassView();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    public CompassView(Context context, AttributeSet ats, int defaultStyle) {
        super(context, ats, defaultStyle);
        initCompassView();
    }

	protected void initCompassView() {
	    setFocusable(true);

	    Resources r = this.getResources();

	    circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    circlePaint.setColor(r.getColor(R.color.background_color));
	    circlePaint.setStrokeWidth(1);
	    circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

	    northString = r.getString(R.string.cardinal_north);
	    eastString = r.getString(R.string.cardinal_east);
	    southString = r.getString(R.string.cardinal_south);
	    westString = r.getString(R.string.cardinal_west);

	    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    textPaint.setColor(r.getColor(R.color.text_color));

	    textHeight = (int)textPaint.measureText("yY");

	    markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    markerPaint.setColor(r.getColor(R.color.marker_color));
	}
    
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    // 나침반은 가능한 한 많은 공간을 차지하면서 완벽한 원모양을 유지해야 한다.
	    // 높이나 폭 중 가장 짧은 경계선을 찾아, 이를 측정 치수로 설정한다.
	    int measuredWidth = measure(widthMeasureSpec);
	    int measuredHeight = measure(heightMeasureSpec);

	    int d = Math.min(measuredWidth, measuredHeight);

	    setMeasuredDimension(d, d);
	}

	private int measure(int measureSpec) {
	    int result = 0;

	    // 측정 사양(measurement specification)을 디코드 한다.
	    int specMode = MeasureSpec.getMode(measureSpec);
	    int specSize = MeasureSpec.getSize(measureSpec);

	    if (specMode == MeasureSpec.UNSPECIFIED) {
	        // 경계가 지정되지 않은 경우에는 기본 크기인 200을 리턴한다.
	        result = 200;
	    } else {
	        // 나침반은 가능한 한 많은 공간을 차지해야 하므로
	        // 항상 사용 가능한 영역 전부를 리턴한다.
	        result = specSize;
	    }
	    return result;
	}
	
	public void setBearing(float _bearing) {
	    bearing = _bearing;
	}

	public float getBearing() {
	    return bearing;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	    int px = getMeasuredWidth() / 2;
	    int py = getMeasuredHeight() /2 ;

	    int radius = Math.min(px, py);

	    // 배경을 그린다.
	    canvas.drawCircle(px, py, radius, circlePaint);

	    // '맨 꼭대기'가 현재 방위를 향하도록
	    // 캔버스를 회전시킨다.
	    canvas.save();
	    canvas.rotate(-bearing, px, py);

	    int textWidth = (int)textPaint.measureText("W");
	    int cardinalX = px-textWidth/2;
	    int cardinalY = py-radius+textHeight;

	    // 15도마다 눈금을 그리고 45도마다 텍스트를 그린다.
	    for (int i = 0; i < 24; i++) {
	        // 눈금을 그린다.
	        canvas.drawLine(px, py-radius, px, py-radius+10, markerPaint);

	        canvas.save();
	        canvas.translate(0, textHeight);

	        // 동, 서, 남, 북 네 방향을 그린다.
	        if (i % 6 == 0) {
	            String dirString = "";
	            switch (i) {
	                case(0)  : {
                                   dirString = northString;
	                               int arrowY = 2*textHeight;
	                               canvas.drawLine(px, arrowY, px-5, 3*textHeight, markerPaint);
	                               canvas.drawLine(px, arrowY, px+5, 3*textHeight, markerPaint);
	                               break;
	                           }
	                case(6)  : dirString = eastString; break;
	                case(12) : dirString = southString; break;
	                case(18) : dirString = westString; break;
	            }
	            canvas.drawText(dirString, cardinalX, cardinalY, textPaint);
	        }

	        else if (i % 3 == 0) {
	            // 45도마다 각도를 그린다.
	            String angle = String.valueOf(i*15);
	            float angleTextWidth = textPaint.measureText(angle);

	            int angleTextX = (int)(px-angleTextWidth/2);
	            int angleTextY = py-radius+textHeight;
	            canvas.drawText(angle, angleTextX, angleTextY, textPaint);
	        }
	        canvas.restore();

	        canvas.rotate(15, px, py);
	    }
	    canvas.restore();
	}
}