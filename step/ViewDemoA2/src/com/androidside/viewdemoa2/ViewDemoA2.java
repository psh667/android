package com.androidside.viewdemoa2;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ViewDemoA2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 
                                                LayoutParams.FILL_PARENT));
        
        EditText edit = new EditText(this);
        edit.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 
                                              LayoutParams.WRAP_CONTENT));
        
        Button button = new Button(this);
        button.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 
                                                LayoutParams.WRAP_CONTENT));
        button.setText("¹öÆ°");
        
        layout.addView(edit);
        layout.addView(button);
        
        setContentView(layout); 
    }
}