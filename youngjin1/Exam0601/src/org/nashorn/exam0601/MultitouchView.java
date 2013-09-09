package org.nashorn.exam0601;

import android.view.*;
import android.content.*;
import android.graphics.*;

public class MultitouchView extends View
{
	private int posX = 0;
	private int posY = 0;
	private float scale = 1f;
	
	private Bitmap image; 
	
	public MultitouchView(Context context)
	{
		super(context);
		
		image = BitmapFactory.decodeResource(context.getResources(), R.drawable.multitouch);
	}
	
	public void setScale(float scale)
	{
		this.scale = scale;
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		if (image != null)
		{
			Rect srcRect = new Rect(0, 0, image.getWidth(), image.getHeight());
			int width = (int)(image.getWidth()*scale);
			int height = (int)(image.getHeight()*scale);
			posX = (image.getWidth() - width)/2;
			posY = (image.getHeight() - height)/2;
			Rect dstRect;
			dstRect = new Rect(posX, posY, posX+width, posY+height);
			canvas.drawBitmap(image, srcRect, dstRect, null);
		}
	}
}