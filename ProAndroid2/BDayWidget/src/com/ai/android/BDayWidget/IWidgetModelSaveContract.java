package com.ai.android.BDayWidget;

import java.util.Map;

public interface IWidgetModelSaveContract
{
	public void  setValueForPref(String key, String value);
	public String getPrefname();
	
	// 저장되길 원하는 키-값 쌍 반환
	public Map<String,String> getPrefsToSave();
	
	// 반환 전 호출됨
	public void  init();
}
