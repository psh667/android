package com.appstudio.android.sample.ch_25_2;

import com.appstudio.android.sample.R;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;

public class PreferenceScreenCodeActivy 
                                  extends PreferenceActivity  {
    final private String TAG = "appstudio";
    CheckBoxPreference mChildCheckBoxPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());  
        mChildCheckBoxPref.setDependency(
                "parent_checkbox_preference");

    }

    private PreferenceScreen createPreferenceHierarchy() {
        // Root
        PreferenceScreen root = getPreferenceManager()
                                .createPreferenceScreen(this);
        // 인라인 프레퍼런스 카테고리
        PreferenceCategory inlinePrefCat = 
            new PreferenceCategory(this);
        inlinePrefCat.setTitle("인라인 프레퍼런스");
        root.addPreference(inlinePrefCat);
        // 체크박스 프레퍼런스
        CheckBoxPreference checkboxPref = 
            new CheckBoxPreference(this);
        checkboxPref.setKey("checkbox_preference");
        checkboxPref.setTitle("checkbox 프레퍼런스");
        checkboxPref.setSummary(
                "checkbox 형태의 프레퍼런스를 입력받습니다.");
        inlinePrefCat.addPreference(checkboxPref);
        // 스위치 프레퍼런스
        SwitchPreference switchPref = 
            new SwitchPreference(this);
        switchPref.setKey("switch_preference");
        switchPref.setTitle("switch 프레퍼런스");
        switchPref.setSummary(
                "swtich 형태의 프레퍼런스를 입력받습니다.");
        inlinePrefCat.addPreference(switchPref);
        
        // 대화창 기반 프레퍼런스 카테고리
        PreferenceCategory dialogBasedPrefCat = 
            new PreferenceCategory(this);
        dialogBasedPrefCat.setTitle("다이얼로그 프레퍼런스");
        root.addPreference(dialogBasedPrefCat);
        // Edit text 프레퍼런스
        EditTextPreference editTextPref = 
            new EditTextPreference(this);
        editTextPref.setDialogTitle("대화창의 제목");
        editTextPref.setKey("edittext_preference");
        editTextPref.setTitle("edittext 프레퍼런스");
        editTextPref.setSummary(
                "edittext 형태의 프레퍼런스를 입력받습니다.");
        dialogBasedPrefCat.addPreference(editTextPref);
        // 리스트 프레퍼런스
        ListPreference listPref = new ListPreference(this);
        listPref.setEntries(R.array.entries_list_preference);
        listPref.setEntryValues(
                R.array.entryvalues_list_preference);
        listPref.setDialogTitle("리스트창의 제목");
        listPref.setKey("list_preference");
        listPref.setTitle("list 프레퍼런스");
        listPref.setSummary(
                "list 형태의 프레퍼런스를 입력받습니다.");
        dialogBasedPrefCat.addPreference(listPref);
        
        // 종속 프레퍼런스 
        PreferenceCategory prefAttrsCat = 
            new PreferenceCategory(this);
        prefAttrsCat.setTitle("종속 프레퍼런스");
        root.addPreference(prefAttrsCat);
        CheckBoxPreference parentCheckBoxPref = 
            new CheckBoxPreference(this);
        parentCheckBoxPref.setKey(
                "parent_checkbox_preference");
        parentCheckBoxPref.setTitle("부모 프레퍼런스");
        parentCheckBoxPref.setSummary(
                "선택하면 자식 프레퍼런스가 입력가능해집니다.");
        prefAttrsCat.addPreference(parentCheckBoxPref);
        TypedArray a = obtainStyledAttributes(
                R.styleable.TogglePrefAttrs);
        CheckBoxPreference childCheckBoxPref = 
            new CheckBoxPreference(this);
        childCheckBoxPref.setKey("child_checkbox_preference");
        childCheckBoxPref.setTitle("자식 프레러펀스");
        childCheckBoxPref.setSummary(
                "부모가 선택되면 자식이 입력가능해집니다.");
        childCheckBoxPref.setLayoutResource(
                a.getResourceId(R.styleable
                .TogglePrefAttrs_android_preferenceLayoutChild,
                0));
        prefAttrsCat.addPreference(childCheckBoxPref);
        mChildCheckBoxPref = childCheckBoxPref;
        a.recycle();
        return root;
    }
}