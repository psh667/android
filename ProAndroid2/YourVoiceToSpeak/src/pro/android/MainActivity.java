package pro.android;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnInitListener {
	private EditText words = null;
	private Button speakBtn = null;
	private EditText filename = null;
	private Button recordBtn = null;
	private Button playBtn = null;
	private EditText useWith = null;
	private Button assocBtn = null;
	private String soundFilename = null;
	private File soundFile = null;
	private static final int REQ_TTS_STATUS_CHECK = 0;
	private static final String TAG = "TTS Demo";
	private TextToSpeech mTts = null;
	private MediaPlayer player = null;
	
	/** 액티비티 최초 생성 시에 호출됨 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		words = (EditText)findViewById(R.id.wordsToSpeak);
		filename = (EditText)findViewById(R.id.filename);
		useWith = (EditText)findViewById(R.id.realText);
		
		speakBtn = (Button)findViewById(R.id.speakBtn);
		speakBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
			mTts.speak(words.getText().toString(), TextToSpeech.QUEUE_ADD, null);
		}});
		
		recordBtn = (Button)findViewById(R.id.recordBtn);
		recordBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				soundFilename = filename.getText().toString();
				soundFile = new File(soundFilename);
				if (soundFile.exists())
					soundFile.delete();
				
				if(mTts.synthesizeToFile(words.getText().toString(), null,
				soundFilename)
				== TextToSpeech.SUCCESS) {
					Toast.makeText(getBaseContext(),
					"Sound file created",
					Toast.LENGTH_SHORT).show();
					playBtn.setEnabled(true);
					assocBtn.setEnabled(true);
				}
				else {
					Toast.makeText(getBaseContext(),
					"Oops! Sound file not created",
					Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		playBtn = (Button)findViewById(R.id.playBtn);
		playBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					player = new MediaPlayer();
					player.setDataSource(soundFilename);
					player.prepare();
					player.start();
				}
				catch(Exception e) {
					Toast.makeText(getBaseContext(),
					"Hmmmmm. Can't play file",
					Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}
			}
		});
		
		assocBtn = (Button)findViewById(R.id.assocBtn);
		assocBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				mTts.addSpeech(useWith.getText().toString(), soundFilename);
				Toast.makeText(getBaseContext(),
				"Associated!",
				Toast.LENGTH_SHORT).show();
			}
		});
		
		// TTS가 존재하는지와 사용 준비가 됐는지 검사
		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, REQ_TTS_STATUS_CHECK);
	}
		
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQ_TTS_STATUS_CHECK) {
			switch (resultCode) {
			case TextToSpeech.Engine.CHECK_VOICE_DATA_PASS:
				// TTS가 존재하며 실행 중이면
				mTts = new TextToSpeech(this, this);
				Log.v(TAG, "Pico가 제대로 설치돼 있네요");
				break;
			case TextToSpeech.Engine.CHECK_VOICE_DATA_BAD_DATA:
			case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_DATA:
			case TextToSpeech.Engine.CHECK_VOICE_DATA_MISSING_VOLUME:
				// 데이터가 없어서 설치해야 하면
				Log.v(TAG, "언어 요소 필요: " + resultCode);
				Intent installIntent = new Intent();
				installIntent.setAction(
				TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
				break;
			case TextToSpeech.Engine.CHECK_VOICE_DATA_FAIL:
			default:
				Log.e(TAG, "실패했어요. TTS가 없는 것 같아요");
			}
		}
		else {
		// 그 외의 경우에 대한 코드 넣기
		}
	}
	
	@Override
	public void onInit(int status) {
		// TTS 엔진이 준비되었으니 버튼을 가용화
		if( status == TextToSpeech.SUCCESS) {
			speakBtn.setEnabled(true);
			recordBtn.setEnabled(true);
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		// 포커스가 벗어나면, 재생 중지
		if(player != null) {
			player.stop();
		}
		// 포커스가 벗어나면, 읽기 중지
		if( mTts != null)
			mTts.stop();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(player != null) {
			player.release();
		}
		if( mTts != null) {
			mTts.shutdown();
		}
	}
}
