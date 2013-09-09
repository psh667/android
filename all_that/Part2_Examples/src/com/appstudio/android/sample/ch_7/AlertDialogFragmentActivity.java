package com.appstudio.android.sample.ch_7;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AlertDialogFragmentActivity extends Activity{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogfragmentmain);

        View tv = findViewById(R.id.text);
        ((TextView)tv).setText("다이얼로그 프레그먼트로 얼럿 다이얼로그를 보여주는 샘플 예제");

        Button button = (Button)findViewById(R.id.show);
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    void showDialog() { 
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                R.string.alert_dialog_two_buttons_title);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        Log.i("TestAlertDialogFragmentActivity", "Positive click!");
    }

    public void doNegativeClick() {
        Log.i("TestAlertDialogFragmentActivity", "Negative click!");
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setIcon(R.drawable.alert_dialog_icon) 
                    .setTitle(title)
                    .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((AlertDialogFragmentActivity)getActivity()).doPositiveClick();
                            }
                        }
                    )
                    .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((AlertDialogFragmentActivity)getActivity()).doNegativeClick();
                            }
                        }
                    )
                    .create();
        }
    }
}
