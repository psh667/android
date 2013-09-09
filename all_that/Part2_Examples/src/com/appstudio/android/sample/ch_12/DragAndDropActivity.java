package com.appstudio.android.sample.ch_12;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.graphics.drawable.ColorDrawable;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;

public class DragAndDropActivity extends Activity {
	private static final String IMAGEVIEW_TAG = "icon bitmap";
	ImageView imageView;
	Bitmap mIconBitmap;
	OnDragListener mDragListen = new myDragEventListener();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draganddropmain); 

        imageView = (ImageView)findViewById(R.id.imageView);
        mIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        imageView.setImageBitmap(mIconBitmap);
        imageView.setTag(IMAGEVIEW_TAG);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
            	Toast.makeText(DragAndDropActivity.this, "ImageView is LongClicked", Toast.LENGTH_SHORT).show();
	            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
	            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
	            ClipData dragData = new ClipData((CharSequence) v.getTag(), mimeTypes , item);
	            DragShadowBuilder myShadow = new MyDragShadowBuilder(imageView);
                return v.startDrag(dragData, myShadow, null, 0 );
            }
        });
        ImageView dropView  = (ImageView)findViewById(R.id.dropView);
        dropView.setOnDragListener(mDragListen);
    }
    
    private static class MyDragShadowBuilder extends View.DragShadowBuilder {
    	private static Drawable shadow;
        public MyDragShadowBuilder(View v) {
            super(v);
            shadow = new ColorDrawable (Color.LTGRAY);
        }
        @Override
        public void onProvideShadowMetrics (Point size, Point touch){
            int width;
			int height;
            width  = getView().getWidth() / 2;
            height = getView().getHeight() / 2;
            shadow.setBounds(0, 0, width, height);
            size.set(width, height);
            touch.set(width / 2, height / 2);
        }
        @Override
        public void onDrawShadow(Canvas canvas) {
            shadow.draw(canvas);
        }
    }
    
    protected class myDragEventListener implements OnDragListener {
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch(action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        ((ImageView) v).setColorFilter(Color.BLUE);
                        Toast.makeText(DragAndDropActivity.this, "ACTION_DRAG_STARTED", Toast.LENGTH_SHORT).show();
                        v.invalidate();
                        return true;
                    } else {
                        return false;
                    }
                case DragEvent.ACTION_DRAG_ENTERED:
                    ((ImageView) v).setColorFilter(Color.GREEN);
                    Toast.makeText(DragAndDropActivity.this, "ACTION_DRAG_ENTERED", Toast.LENGTH_SHORT).show();
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                	return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    ((ImageView) v).setColorFilter(Color.RED);
                    Toast.makeText(DragAndDropActivity.this, "ACTION_DRAG_EXITED", Toast.LENGTH_SHORT).show();
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    CharSequence dragData = item.getText();
                    Toast.makeText(DragAndDropActivity.this, "Dragged data is " + dragData, Toast.LENGTH_SHORT).show();
                    ((ImageView) v).clearColorFilter();
                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    ((ImageView) v).clearColorFilter();
                    v.invalidate();
                    if (event.getResult()) {
                    	Toast.makeText(DragAndDropActivity.this, "The drop was handled.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DragAndDropActivity.this, "The drop didn't work.", Toast.LENGTH_SHORT).show();
                    };
                    return true;
				default:
					Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                    break;
            };
			return false;
        };
    };
}