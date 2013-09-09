package com.appstudio.android.sample.ch_24_5;

import java.util.ArrayList;
import java.util.List;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Speech2TextActivity extends Activity 
                                   implements OnClickListener {
    private static final String TAG = "VoiceRecognition";
    private static final int VOICE_RECOGNITION_REQUEST_CODE= 1;
    private ListView mList;
    private Handler mHandler;
    private Spinner mSupportedLanguageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        setContentView(R.layout.speech2text_activity);
        Button speakButton = 
            (Button) findViewById(R.id.btn_speak);
        mList = (ListView) findViewById(R.id.list);
        mSupportedLanguageView = 
            (Spinner) findViewById(R.id.supported_languages);
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities= pm.queryIntentActivities(
                new Intent(RecognizerIntent
                                 .ACTION_RECOGNIZE_SPEECH), 0);
        if(activities.size() != 0) {
            speakButton.setOnClickListener(this);
        } else {
            speakButton.setEnabled(false);
            speakButton.setText("음성인식기 없음!");
        }
        refreshVoiceSettings();
    }

    public void onClick(View v) {
        if(v.getId() == R.id.btn_speak) {
            startVoiceRecognitionActivity();
        }
    }

    // 음성인식을 위한 액티비티를 기동한다.
    private void startVoiceRecognitionActivity() {
        Intent intent = 
          new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, 
                        getClass().getPackage().getName());
        // 음성인식화면에 정보 출력
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, 
                        "Speech recognition demo");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
        // 디폴트 선택이 아니면 언어를 설정
        if(!mSupportedLanguageView.getSelectedItem()
                               .toString().equals("Default")) {
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                    mSupportedLanguageView.getSelectedItem()
                    .toString()
            );
        }
        startActivityForResult(intent, 
                VOICE_RECOGNITION_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, 
                                 int resultCode, Intent data) {
        if(requestCode == VOICE_RECOGNITION_REQUEST_CODE 
            && resultCode == RESULT_OK) {
            ArrayList<String> matches = 
                data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
            mList.setAdapter(new ArrayAdapter<String>(this, 
                 android.R.layout.simple_list_item_1,matches));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshVoiceSettings() {
        sendOrderedBroadcast(
            RecognizerIntent.getVoiceDetailsIntent(this), null,
                new SupportedLanguageBroadcastReceiver(), null,
                Activity.RESULT_OK, null, null);
    }

    private void updateSupportedLanguages(
                                      List<String> languages) {
        languages.add(0, "Default");
        SpinnerAdapter adapter = 
            new ArrayAdapter<CharSequence>(this
                ,android.R.layout.simple_spinner_item
                ,languages.toArray(
                        new String[languages.size()]));
        mSupportedLanguageView.setAdapter(adapter);
    }

    private void updateLanguagePreference(String language) {
        TextView textView = 
            (TextView) findViewById(R.id.language_preference);
        textView.setText(language);
    }

    private class SupportedLanguageBroadcastReceiver 
                                    extends BroadcastReceiver {
        @Override
        public void onReceive(
                        Context context, final Intent intent) {
            final Bundle extra = getResultExtras(false);
            if (getResultCode() != Activity.RESULT_OK) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast("Error code:" 
                                  + getResultCode());
                    }
                });
            }
            if (extra == null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast("No extra");
                    }
                });
            }
            if (extra.containsKey(RecognizerIntent
                                 .EXTRA_SUPPORTED_LANGUAGES)) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateSupportedLanguages(
                            extra.getStringArrayList(
                                 RecognizerIntent
                                 .EXTRA_SUPPORTED_LANGUAGES));
                    }
                });
            }

            if (extra.containsKey(RecognizerIntent
                                 .EXTRA_LANGUAGE_PREFERENCE)) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateLanguagePreference(
                            extra.getString(
                                RecognizerIntent
                                .EXTRA_LANGUAGE_PREFERENCE));
                    }
                });
            }
        }
        private void showToast(String text) {
            Toast.makeText(Speech2TextActivity.this, 
                    text, 1000).show();
        }
    }
}
