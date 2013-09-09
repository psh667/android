package com.appstudio.android.sample.ch_27_1;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class SensorActivity extends Activity 
implements SensorEventListener {
private SensorManager mSensorManager;  
private Sensor mLight;  
@Override  
public final void onCreate(Bundle savedInstanceState) {    
super.onCreate(savedInstanceState);    
//setContentView(R.layout.main);    
mSensorManager = (SensorManager) getSystemService(
Context.SENSOR_SERVICE);    
mLight = mSensorManager.getDefaultSensor(
Sensor.TYPE_LIGHT);  
}  
@Override  
public final void onAccuracyChanged(
Sensor sensor, int accuracy) {    
// 센서의 정확도 변경에 따른 처리가 필요하면 이곳에서 기술  
}  
@Override  
public final void onSensorChanged(SensorEvent event) {    
// 센서 값을 꺼내온 후 처리한다.
float lux = event.values[0];    
// 센서 값에 따른 처리  
}  
@Override  
protected void onResume() {    
super.onResume();    
mSensorManager.registerListener(
this, mLight, SensorManager.SENSOR_DELAY_NORMAL);  
}  
@Override  
protected void onPause() {    
super.onPause();    
mSensorManager.unregisterListener(this);  
}
}
