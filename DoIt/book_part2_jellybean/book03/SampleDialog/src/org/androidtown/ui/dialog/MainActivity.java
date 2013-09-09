package org.androidtown.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 프래그먼트와 대화상자를 사용하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 프로그레스바를 보여줄 때 사용할 상수
	 */
	public static final int PROGRESS_DIALOG = 1001;
	
	/**
	 * 프로그레스 대화상자 객체
	 */
	ProgressDialog progressDialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃에 추가된 프로그레스바 객체 참조
        ProgressBar proBar = (ProgressBar) findViewById(R.id.progressBar01);
        proBar.setIndeterminate(false);
        proBar.setMax(100);
        proBar.setProgress(80);

        // 아이콘 이미지 설정
        ImageView icon = (ImageView) findViewById(R.id.iconItem);
        Resources res = getResources();
        Drawable drawable = (Drawable) res.getDrawable(R.drawable.apple);
        icon.setImageDrawable(drawable);

        // 텍스트 설정
        TextView nameText = (TextView) findViewById(R.id.dataItem01);
        nameText.setText("사과");
		
        // 텍스트 설정
        TextView progressText = (TextView) findViewById(R.id.dataItem02);
		progressText.setText("80%");
		
		// 보여주기 버튼 이벤트 설정
		Button btnShow = (Button) findViewById(R.id.btnShow);
		btnShow.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment prev = getFragmentManager().findFragmentByTag("dialog");
				if (prev != null) {
					ft.remove(prev);
				}
				ft.addToBackStack(null);

				// 대화상자를 만들고 보여주기
				InfoDialogFragment dialog = new InfoDialogFragment();
				dialog.show(ft, "dialog");
			}
		});
		
		// 닫기 버튼 이벤트 설정
		Button btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * 새로 정의한 대화상자 프래그먼트
     */
    class InfoDialogFragment extends DialogFragment {
    	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    	View view = inflater.inflate(R.layout.info_dialog, container, false);
	
	    	Button btnShow = (Button) view.findViewById(R.id.btnShow);
	    	btnShow.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "예 버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
					dismiss();
				}
			});
	    	
	    	Button btnClose = (Button) view.findViewById(R.id.btnClose);
	    	btnClose.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "아니오 버튼이 눌렸습니다.", Toast.LENGTH_SHORT).show();
					dismiss();
				}
			});
	    	
	    	return view;
    	}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Toast.makeText(getApplicationContext(), "onCreateDialog() called in a Fragment.", Toast.LENGTH_SHORT).show();
			
			return super.onCreateDialog(savedInstanceState);
		}
    	
    }
    
}
