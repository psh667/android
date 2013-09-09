package pro.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import java.util.HashMap;
import java.util.StringTokenizer;

public class MainActivity extends Activity implements OnInitListener, OnUtteranceCompletedListener {
	private EditText words = null;
	private Button  speakBtn  = null;
	private static final int REQ_TTS_STATUS_CHECK = 0;
	private static final String TAG = "TTS Demo";
	private TextToSpeech mTts;
	private int uttCount = 0;
	private int lastUtterance = -1;
	private HashMap<String, String>  params = new HashMap<String, String>();

	
	/** 액티비티 최초 생성 시에 호출됨 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		words = (EditText)findViewById(R.id.wordsToSpeak);
		speakBtn  = (Button)findViewById(R.id.speak);
		speakBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mTts.speak(words.getText().toString(), TextToSpeech.QUEUE_ADD, null);
			}
		}
	);
	
	// TTS가 존재하는지와 사용 가능한지 검사
	Intent checkIntent = new Intent();
	checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_TTS_STATUS_CHECK) {
			switch (resultCode) {
				case  TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
					// TTS가 존재하며 실행 중이면
					mTts = new TextToSpeech(this, this);
					Log.v(TAG, "Pico가 성공적으로 설치됐어요");
					break;
				case  TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
				case  TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
				case  TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
					// 데이터가 없어서 설치해야 되면
					Log.v(TAG, "언어 요소 필요: " + resultCode);
					Intent installIntent = new Intent();
					installIntent.setAction(
					TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
					startActivity(installIntent);
					break;
				case  TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
				default:
					Log.e(TAG, "실패했어요. TTS가 없는 것 같아요");
		}
		}
		else {
		// 그 외의 경우에 대한 처리
		}
	}
	
	//@Override
	public void onClick(View view) {
		StringTokenizer st = new StringTokenizer(words.getText().toString(),",.");
		while (st.hasMoreTokens()) {
			params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(uttCount++));
			mTts.speak(st.nextToken(), TextToSpeech.QUEUE_ADD, params);
		}
	}

	@Override
	public void onInit(int status) {
		// TTS 엔진을 완성했으니 이제 버튼을 가용화하자.
		if( status == TextToSpeech.SUCCESS) {
			speakBtn.setEnabled(true);
			mTts.setOnUtteranceCompletedListener(this);
		}
	}

	@Override
	public void onUtteranceCompleted(String uttId) {
		Log.v(TAG, "어터런스 Id별 읽기 완료 메시지: " + uttId);
		lastUtterance = Integer.parseInt(uttId);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		// 포커스가 없으면, 음성 중지
		if( mTts != null)
		mTts.stop();
	}
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mTts.shutdown();
	}
}
