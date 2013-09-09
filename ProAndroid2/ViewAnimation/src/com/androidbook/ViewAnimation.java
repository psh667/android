package com.androidbook;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class ViewAnimation extends Animation 
{
  public void ViewAnimation2() { }

  @Override 
  public void initialize(int width, int height, int parentWidth, 
                        int parentHeight) 
  {
    super.initialize(width, height, parentWidth, parentHeight); 
    setDuration(2500); 
    setFillAfter(true); 
    setInterpolator(new LinearInterpolator()); 
  }

  Camera camera = new Camera(); 


  @Override 
//  protected void applyTransformation(float interpolatedTime, Transformation t) 
//  {
//	float centerX = interpolatedTime/2;
//	float centerY = interpolatedTime/2;
//    final Matrix matrix = t.getMatrix(); 
//    matrix.setScale(interpolatedTime, interpolatedTime); 
//    matrix.preTranslate(-centerX, -centerY); 
//    matrix.postTranslate(centerX, centerY);
//	}
  protected void applyTransformation(float interpolatedTime, Transformation t) 
  {
		float centerX = interpolatedTime/2;
		float centerY = interpolatedTime/2;
    final Matrix matrix = t.getMatrix(); 
    camera.save(); 
    camera.translate(0.0f, 0.0f, (1300 - 1300.0f * interpolatedTime)); 
    camera.rotateY(360 * interpolatedTime); 
    camera.getMatrix(matrix);

    matrix.preTranslate(-centerX, -centerY); 
    matrix.postTranslate(centerX, centerY); 
    camera.restore(); 
  }
}
