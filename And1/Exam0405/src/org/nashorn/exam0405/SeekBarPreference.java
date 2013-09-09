package org.nashorn.exam0405;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class SeekBarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener
{
  private SeekBar seekBar;

  private Context context;
  
  private int defaultNumber = 0;
  private int maxNumber = 0;
  private int value = 0;

  public SeekBarPreference(Context context, AttributeSet attrs) { 
	  super(context,attrs); 
	  this.context = context;
	  defaultNumber = 50;
	  maxNumber = 100;
  }
  @Override 
  protected View onCreateDialogView() 
  {
    LinearLayout layout = new LinearLayout(context);
    layout.setOrientation(LinearLayout.VERTICAL);
    layout.setPadding(6,6,6,6);

    seekBar = new SeekBar(context);
    seekBar.setOnSeekBarChangeListener(this);
    layout.addView(seekBar, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

    if (shouldPersist())
    {
    	value = getPersistedInt(defaultNumber);
    }
    
    seekBar.setMax(maxNumber);
    seekBar.setProgress(value);
    return layout;
  }
  
  @Override 
  protected void onBindDialogView(View v) 
  {
	  super.onBindDialogView(v);
	  seekBar.setMax(maxNumber);
	  seekBar.setProgress(value);  
  }
  
  @Override
  protected void onSetInitialValue(boolean restore, Object defaultValue)  
  {
	  super.onSetInitialValue(restore, defaultValue);
	  if (restore)
	  {
		  value = shouldPersist() ? getPersistedInt(defaultNumber) : 0;
	  }
	  else
	  {
		  value = (Integer)defaultValue;
	  }
  }

  public void onProgressChanged(SeekBar seek, int value, boolean fromTouch)
  {
	  if (shouldPersist())
	  {
		  persistInt(value);
	  }
	  callChangeListener(new Integer(value));
  }
  
  public void onStartTrackingTouch(SeekBar seek) 
  {
  }
  
  public void onStopTrackingTouch(SeekBar seek) 
  {
  }

  public void setMax(int max) 
  { 
	  maxNumber = max; 
  }
  
  public int getMax() 
  { 
	  return maxNumber; 
  }

  public void setProgress(int progress) 
  { 
	  value = progress;
	  if (seekBar != null)
	  {
		  seekBar.setProgress(progress);
	  }
  }
  public int getProgress() 
  { 
	  return value; 
  }
}