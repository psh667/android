package com.androidside.ThemeDemoA1;

import android.app.Activity;
import android.os.Bundle;

public class ThemeDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_big);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}