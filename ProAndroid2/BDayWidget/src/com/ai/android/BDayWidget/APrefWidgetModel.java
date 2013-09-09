package com.ai.android.BDayWidget;

import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

//파일명: /src//APrefWidgetModel.java 
public abstract class APrefWidgetModel implements IWidgetModelSaveContract
{
    private static String tag = "APrefWidgetModel";

    public int iid;
    public  APrefWidgetModel(int instanceId)  {
    iid = instanceId;
    }
    // 추상 메서드
    public abstract  String getPrefname();
    public abstract  void  init();
    public  Map<String,String> getPrefsToSave(){	return null;}

    public void  savePreferences(Context context){ 
        Map<String,String> keyValuePairs = getPrefsToSave(); 
        if (keyValuePairs == null){
            return;
        }
        // 어떤 값들을 저장하려 함
        SharedPreferences.Editor prefs  =
            context.getSharedPreferences(getPrefname(), 0).edit();

        for(String key:   keyValuePairs.keySet()){ 
            String  value = keyValuePairs.get(key); 
            savePref(prefs,key,value);
        }
        // 최종적으로 값들을 넘김
        prefs.commit();
    }

    private void  savePref(SharedPreferences.Editor prefs,
                        String  key, String  value) {
        String newkey = getStoredKeyForFieldName(key);
        prefs.putString(newkey, value);
    }
    private void  removePref(SharedPreferences.Editor prefs,  String key)  { 
        String newkey = getStoredKeyForFieldName(key); 
        prefs.remove(newkey);
    }
    protected  String  getStoredKeyForFieldName(String fieldName){
        return fieldName   + "_"  + iid;
    }
    public static  void  clearAllPreferences(Context context,  String prefname)  { 
        SharedPreferences prefs=context.getSharedPreferences(prefname,  0); 
        SharedPreferences.Editor prefsEdit  = prefs.edit();
        prefsEdit.clear();
        prefsEdit.commit();
    }

    public boolean  retrievePrefs(Context ctx) {
        SharedPreferences prefs  = ctx.getSharedPreferences(getPrefname(), 0);
        Map<String,?> keyValuePairs  = prefs.getAll();
        boolean prefFound = false;
        for (String key:   keyValuePairs.keySet()){
            if (isItMyPref(key) == true){
                String  value = (String)keyValuePairs.get(key);
                setValueForPref(key,value);
                prefFound = true;
            }
        }
        return  prefFound;
    }
    public void  removePrefs(Context context) { 
        Map<String,String> keyValuePairs = getPrefsToSave(); 
        if (keyValuePairs == null){
            return;
        }
        // 어떤 값들을 저장하려 함
        SharedPreferences.Editor prefs =
            context.getSharedPreferences(getPrefname(), 0).edit();

        for(String key:   keyValuePairs.keySet()){
            removePref(prefs,key);
        }
        // 최종적으로 값들을 넘김
        prefs.commit();
    }
    private boolean  isItMyPref(String keyname) {
        if (keyname.indexOf("_" + iid) > 0){
            return true;
        }
        return false;
    }
    public void  setValueForPref(String key,  String value) {
        return;
    }
}
