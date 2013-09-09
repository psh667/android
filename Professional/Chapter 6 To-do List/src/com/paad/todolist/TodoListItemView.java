package com.paad.todolist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class TodoListItemView extends TextView {

    private Paint marginPaint;
    private Paint linePaint;
    private int paperColor;
    private float margin;
	
    public TodoListItemView (Context context, AttributeSet ats, int ds) {
        super(context, ats, ds);
        init();
    }

    public TodoListItemView (Context context) {
        super(context);
        init();
    }

    public TodoListItemView (Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 리소스 테이블의 레퍼런스를 얻어온다.
        Resources myResources = getResources();

        // onDraw 메서드에서 사용할 페인트 브러시들을 만든다.
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(myResources.getColor(R.color.notepad_margin));
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(myResources.getColor(R.color.notepad_lines));

        // 종이의 배경 색상과 여백 폭을 얻어온다.
        paperColor = myResources.getColor(R.color.notepad_paper);
        margin = myResources.getDimension(R.dimen.notepad_margin);
    }
    
	@Override
	public void onDraw(Canvas canvas) {
	    // 종이의 배경 색상으로 칠한다.
	    canvas.drawColor(paperColor);

	    // 괘선을 그린다.
	    canvas.drawLine(0, 0, 0, getMeasuredHeight(), linePaint);
	    canvas.drawLine(0, getMeasuredHeight(),
	                         getMeasuredWidth(), getMeasuredHeight(),
	                         linePaint);
	    // 여백을 그린다.
	    canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);

	    // 텍스트를 여백 맞은편으로 이동시킨다.
	    canvas.save();
	    canvas.translate(margin, 0);

	    // TextView를 이용해 텍스트를 렌더링 한다.
	    super.onDraw(canvas);
	    canvas.restore();
	}
}