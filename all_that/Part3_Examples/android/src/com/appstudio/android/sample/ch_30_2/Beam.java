/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appstudio.android.sample.ch_30_2;

import java.nio.charset.Charset;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class Beam extends Activity 
                        implements CreateNdefMessageCallback,
                                 OnNdefPushCompleteCallback {
  NfcAdapter mNfcAdapter;
  TextView mInfoText;
  private static final int MESSAGE_SENT = 1;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.beam);

    mInfoText = (TextView) findViewById(R.id.textView);
    // Check for available NFC Adapter
    mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    if (mNfcAdapter == null) {
      mInfoText = (TextView) findViewById(R.id.textView);
      mInfoText.setText("NFC 미지원 디바이스");
    }
    // NDEF 메시지 생성을 위한 콜백 함수 설정
    mNfcAdapter.setNdefPushMessageCallback(this, this);
    // 메시지 전송 완료 후 콜백 함수 설정
    mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
  }


  /**
   * CreateNdefMessageCallback interface 구현
   */
  @Override
  public NdefMessage createNdefMessage(NfcEvent event) {
    Time time = new Time();
    time.setToNow();
    String text = ("Beam me up!\n\n" +
                    "Beam Time: " + time.format("%H:%M:%S"));
    NdefMessage msg = new NdefMessage(new NdefRecord[] { 
        createMimeRecord(
            "application/com.appstudio.sample.android.beam", 
                                             text.getBytes())
        // AAR을 사용하려면 아래 주석을 푼다.  
        // ,NdefRecord.createApplicationRecord(
        //                    "com.appstudio.sample.android")
    });
    return msg;
  }

  /**
   * 메시지 전송이 완료되었을 때 호출되는 콜백 
   */
  @Override
  public void onNdefPushComplete(NfcEvent arg0) {
    // UI쓰레드와는 다른 별개의 쓰레드가 이 메서드 콜백호출
    // UI 쓰레드로 전달
    mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
  }

  /** 메시지 전송 완료시 실행 */
  private final Handler mHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case MESSAGE_SENT:
          Toast.makeText(getApplicationContext(), 
                     "메시지 전송 완료!", Toast.LENGTH_LONG).show();
          break;
      }
    }
  };

  @Override
  public void onResume() {
    super.onResume();
    // NFC 태그에 의해 기동되었는지를 먼저 확인한다.
    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(
                                  getIntent().getAction())) {
      processIntent(getIntent());
    }
  }

  /**
   * 태그 메시지를 받으면 해당 인텐트에서 NDEF 메시지를 추출
   */
  void processIntent(Intent intent) {
    Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                             NfcAdapter.EXTRA_NDEF_MESSAGES);
    // only one message sent during the beam
    NdefMessage msg = (NdefMessage) rawMsgs[0];
    // 첫번째 레코드는 마임 타입을 담고 있으며, 
    // 두번째 레코드가 있다면 AAR을 담고 있다.
    mInfoText.setText(new String(
                          msg.getRecords()[0].getPayload()));
  }

  /**
   * TNF_MIME_MEDIA 타입의 태그 레코드 생성
   *
   * @param mimeType
   */
  public NdefRecord createMimeRecord(String mimeType, 
                                            byte[] payload) {
    byte[] mimeBytes = mimeType.getBytes(
                                Charset.forName("US-ASCII"));
    NdefRecord mimeRecord = new NdefRecord(
                        NdefRecord.TNF_MIME_MEDIA, mimeBytes,
                                       new byte[0], payload);
    return mimeRecord;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // NFC를 미지원하면 이 옵션 메뉴는 필요 없다.
    if (mNfcAdapter == null) {
      return super.onCreateOptionsMenu(menu);
    }
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.beam_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_settings:
        Intent intent = new Intent(
                        Settings.ACTION_NFCSHARING_SETTINGS);
        startActivity(intent);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
  
  @Override
  public void onNewIntent(Intent intent) {
    // onResume gets called after this to handle the intent
    setIntent(intent);
  }    
}
