package com.appstudio.android.sample.ch_7;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DialogFragmentActivity extends Activity {
	 int mStackLevel = 0;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.dialogfragmentmain);

	        View tv = findViewById(R.id.text);
	        ((TextView)tv).setText("다이얼로그 프레그먼트를 보여주는 예제입니다.  "
	    			+ "첫번째 다이얼로그를 보려면 아래 '보여주기'버튼을 눌러주세요."
	    			+ "'보여주기'버튼을 계속 누를 경우에 다른 다이얼로그 스타일들이"
	    			+ "계속 보여주고 스택에 쌓여지고 '뒤로'버튼을 누르면 이전 다이얼로그가 보여주게됩니다."
	    			);


	        Button button = (Button)findViewById(R.id.show);
	        button.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                showDialog();
	            }
	        });

	        if (savedInstanceState != null) {
	            mStackLevel = savedInstanceState.getInt("level");
	        }
	    }

	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putInt("level", mStackLevel);
	    }
	    
	    void showDialog() {
	        mStackLevel++;

	        FragmentTransaction ft = getFragmentManager().beginTransaction();
	        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
	        if (prev != null) {
	            ft.remove(prev);
	        }
	        ft.addToBackStack(null);

	        DialogFragment newFragment = MyDialogFragment.newInstance(mStackLevel);
	        newFragment.show(ft, "dialog");
	    }
	    
	    static String getNameForNum(int num) {
	        switch ((num-1)%6) {
	            case 1: return "STYLE_NO_TITLE";
	            case 2: return "STYLE_NO_FRAME";
	            case 3: return "STYLE_NO_INPUT";
	            case 4: return "STYLE_NORMAL with dark fullscreen theme";
	            case 5: return "STYLE_NORMAL with light theme";
	            case 6: return "STYLE_NO_TITLE with light theme";
	            case 7: return "STYLE_NO_FRAME with light theme";
	            case 8: return "STYLE_NORMAL with light fullscreen theme";
	        }
	        return "STYLE_NORMAL";
	    }
	    
	    public static class MyDialogFragment extends DialogFragment {
	        int mNum;

	        static MyDialogFragment newInstance(int num) {
	            MyDialogFragment f = new MyDialogFragment();

	            Bundle args = new Bundle();
	            args.putInt("num", num);
	            f.setArguments(args);

	            return f;
	        }

	        @Override
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);
	            mNum = getArguments().getInt("num");

	            int style = DialogFragment.STYLE_NORMAL, theme = 0;
	            switch ((mNum-1)%6) {
	                case 1: style = DialogFragment.STYLE_NO_TITLE; break;
	                case 2: style = DialogFragment.STYLE_NO_FRAME; break;
	                case 3: style = DialogFragment.STYLE_NO_INPUT; break;
	                case 4: style = DialogFragment.STYLE_NORMAL; break;
	                case 5: style = DialogFragment.STYLE_NO_TITLE; break;
	                case 6: style = DialogFragment.STYLE_NO_FRAME; break;
	                case 7: style = DialogFragment.STYLE_NORMAL; break;
	            }
	            switch ((mNum-1)%6) {
	                case 2: theme = android.R.style.Theme_Panel; break;
	                case 4: theme = android.R.style.Theme; break;
	                case 5: theme = android.R.style.Theme_Light; break;
	                case 6: theme = android.R.style.Theme_Light_Panel; break;
	                case 7: theme = android.R.style.Theme_Light; break;
	            }
	            setStyle(style, theme);
	        }

	        @Override
	        public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                Bundle savedInstanceState) {
	            View v = inflater.inflate(R.layout.dialogfragmentmain, container, false);
	            View tv = v.findViewById(R.id.text);
	            ((TextView)tv).setText("Dialog # " + mNum + " using style"
	                    + getNameForNum(mNum));

	            Button button = (Button)v.findViewById(R.id.show);
	            button.setOnClickListener(new OnClickListener() {
	                public void onClick(View v) {
	                	((DialogFragmentActivity)getActivity()).showDialog();
	                }
	            });

	            return v;
	        }
	    }
	    
}
