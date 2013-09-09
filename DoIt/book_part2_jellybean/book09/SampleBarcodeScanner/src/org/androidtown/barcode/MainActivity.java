package org.androidtown.barcode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 바코드 스캔 화면을 띄우는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	public static final int REQUEST_CODE_SCAN = 1001;
	public static final int DIALOG_SCANNER_NEEDED = 1002;
	public static final int DIALOG_SHOW_URL = 1003;

	public static final String PRODUCT_CODE_TYPES = "UPC_A,UPC_E,EAN_8,EAN_13";
	public static final String ONE_D_CODE_TYPES = PRODUCT_CODE_TYPES + ",CODE_39,CODE_93,CODE_128";
	public static final String QR_CODE_TYPES = "QR_CODE";
	public static final String ALL_CODE_TYPES = null;

	private TextView contentsText;
	private String scannedUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentsText = (TextView) findViewById(R.id.contentsText);

        Button scanBtn = (Button) findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		scan();
        	}
        });

    }

    private void scan() {
    	scan(ALL_CODE_TYPES);
    }

    private void scan(String formats) {
    	Intent intentScan = new Intent("com.google.zxing.client.android.SCAN");
        intentScan.addCategory(Intent.CATEGORY_DEFAULT);

        if (formats != null) {
            intentScan.putExtra("SCAN_FORMATS", formats);
        }

        try {
            startActivityForResult(intentScan, REQUEST_CODE_SCAN);
        } catch (ActivityNotFoundException e) {
            showDialog(DIALOG_SCANNER_NEEDED);
        }
    }


	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == REQUEST_CODE_SCAN) {
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult called with code : " + resultCode, Toast.LENGTH_LONG);
			toast.show();

			if (resultCode == Activity.RESULT_OK) {
		        String contents = intent.getStringExtra("SCAN_RESULT");
		        String formatName = intent.getStringExtra("SCAN_RESULT_FORMAT");

		        contentsText.append("\nSCAN RESULT FORMAT : " + formatName);
		        contentsText.append("\nSCAN RESULT : " + contents);

		        if (contents != null && contents.indexOf("http://") >= 0) {
		        	int startIndex = contents.indexOf("http://");
		        	scannedUrl = contents.substring(startIndex);

		        	showDialog(DIALOG_SHOW_URL);
		        }

		    } else {
		    	contentsText.append("\nSCAN FAILED.");

		    }

		}

	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = null;

		switch(id) {
			case DIALOG_SCANNER_NEEDED:
				builder = new AlertDialog.Builder(this);
				builder.setTitle("바코드 스캐너 앱 설치");
				builder.setMessage("바코드 스캐너 앱이 필요합니다. 자동 설치할까요?");
				builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {
	        	    	Uri uri = Uri.parse("market://details?id=com.google.zxing.client.android");
	    				Intent intent = new Intent(Intent.ACTION_VIEW, uri);

	    				startActivity(intent);
                    }
                });

				builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

				break;
			case DIALOG_SHOW_URL:
				builder = new AlertDialog.Builder(this);
				builder.setTitle("웹으로 보기");
				builder.setMessage("스캔한 결과를 웹으로 보시겠습니까?");
				builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {
	    				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedUrl));
	    				startActivity(intent);
                    }
                });

				builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
	        	    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

				break;
			default:
				break;
		}

		return builder.create();
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
