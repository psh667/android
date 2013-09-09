/*
 * Copyright (C) 2009 The Android Open Source Project
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

package com.appstudio.android.sample.ch_30_1;

import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.appstudio.android.sample.R;

/**
 * 이 액티비티는 대화창으로 나타난다.
 * 페어링된 디바이스와 발견된 디바이스의 목록이 출력된다.
 * 사용자에의해 디바이스가 선택되면 디바이스의 맥주소가 이 액티비티를 
 * 호출한 원래 액티비티로 인텐트를 반환한다.
 */
public class DeviceListActivity extends Activity {
  // 디버깅용 
  private static final String TAG = "DeviceListActivity";
  private static final boolean D = true;

  // 여러 디바이스 중 클릭에 의해 선택된 디바이스 주소를 위한 엑스트라 
  public static String EXTRA_DEVICE_ADDRESS = "device_address";

  // Member fields
  private BluetoothAdapter mBtAdapter;
  private ArrayAdapter<String> mPairedDevicesArrayAdapter;
  private ArrayAdapter<String> mNewDevicesArrayAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Setup the window
    requestWindowFeature(
                        Window.FEATURE_INDETERMINATE_PROGRESS);
    setContentView(R.layout.device_list_activity);

    // 사용자가 중간에 취소를 하고 빠져나가는 것을 대비하여 미리 취소 선택
    setResult(Activity.RESULT_CANCELED);

    // 디바이스 발견을 수행할 버튼 초기화 
    Button scanButton = (Button) findViewById(R.id.button_scan);
    scanButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        doDiscovery();
        v.setVisibility(View.GONE);
      }
    });

    // 이미 페어링 된 디바이스를 위한 어댑터 준비 
    // 새롭게 발견된 디바이스를 위한 어댑터 준비
    mPairedDevicesArrayAdapter = 
          new ArrayAdapter<String>(this, R.layout.device_name);
    mNewDevicesArrayAdapter = 
          new ArrayAdapter<String>(this, R.layout.device_name);

    // 페어링된 디바이스 목록 화면 출력 및 아이템 클릭 준비 
    ListView pairedListView = (ListView) 
                             findViewById(R.id.paired_devices);
    pairedListView.setAdapter(mPairedDevicesArrayAdapter);
    pairedListView.
                  setOnItemClickListener(mDeviceClickListener);

    // 페어링된 디바이스 목록 화면 출력 및 아이템 클릭 준비 
    ListView newDevicesListView = 
                     (ListView) findViewById(R.id.new_devices);
    newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
    newDevicesListView.setOnItemClickListener
                                        (mDeviceClickListener);

    // 새로운 디비아스가 발견되었을 때를 위해 브로드캐스트 리시버 등록
    IntentFilter filter = 
                new IntentFilter(BluetoothDevice.ACTION_FOUND);
    this.registerReceiver(mReceiver, filter);

    // 디바이스 발견 프로세스가 종료를 대비하는 브로드캐스트 리시버 등록
    filter = new IntentFilter(BluetoothAdapter.
                                    ACTION_DISCOVERY_FINISHED);
    this.registerReceiver(mReceiver, filter);

    // 블루투스 어댑터 획득
    mBtAdapter = BluetoothAdapter.getDefaultAdapter();

    // 현재 페어링된 디바이스 목록 조회
    Set<BluetoothDevice> pairedDevices = 
                                 mBtAdapter.getBondedDevices();

    // 페어링된 디바이스를 어댑터에 추가
    if (pairedDevices.size() > 0) {
      findViewById(R.id.title_paired_devices).setVisibility(
                                                 View.VISIBLE);
      for (BluetoothDevice device : pairedDevices) {
        mPairedDevicesArrayAdapter.add(device.getName() + "\n" 
                                        + device.getAddress());
      }
    } else {
      String noDevices = getResources().getText(
                              R.string.none_paired).toString();
      mPairedDevicesArrayAdapter.add(noDevices);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    // Make sure we're not doing discovery anymore
    if (mBtAdapter != null) {
      mBtAdapter.cancelDiscovery();
    }

    // Unregister broadcast listeners
    this.unregisterReceiver(mReceiver);
  }

  /**
   * 디바이스 발견 시작
   */
  private void doDiscovery() {
    if (D) Log.d(TAG, "doDiscovery()");

    // 타이틀에 스캔 중임을 표시
    setProgressBarIndeterminateVisibility(true);
    setTitle(R.string.scanning);

    // 새로이 발견된 디바이스를 위한 필드 visible
    findViewById(R.id.title_new_devices).setVisibility(
                                                 View.VISIBLE);

    // 이미 진행 중인 발견 프로세스 중지
    if (mBtAdapter.isDiscovering()) {
      mBtAdapter.cancelDiscovery();
    }

    // 블루트스 어댑터에 발견 요청
    mBtAdapter.startDiscovery();
  }

  // 디바이스 목록에 표시되는 디바이스를 위한 클릭 리스너
  private OnItemClickListener mDeviceClickListener = 
                                    new OnItemClickListener() {
    public void onItemClick(
              AdapterView<?> av, View v, int arg2, long arg3) {
      // 발견 프로세스는 비용이 많이 들기 때문에 연결할 디바이스가 
      // 결정되었으면 발견 프로세스를 중지한다. 
      mBtAdapter.cancelDiscovery();

      // 연결할 디바이스의 맥 주소를 가져온다. 
      String info = ((TextView) v).getText().toString();
      String address = info.substring(info.length() - 17);

      // 맥주소를 인텐트에 저장한다.
      Intent intent = new Intent();
      intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

      // 호출 액티비티로 반환한다.
      setResult(Activity.RESULT_OK, intent);
      finish();
    }
  };

  // 디바이스 발견과 발견 프로세스 종료를 인식하기 위한 리시버
  private final BroadcastReceiver mReceiver = 
                                      new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();

      // 디바이스를 발견했을 때 
      if (BluetoothDevice.ACTION_FOUND.equals(action)) {
        // 발견된 디바이스에 대한 정보 획득
        BluetoothDevice device = intent.getParcelableExtra(
                                 BluetoothDevice.EXTRA_DEVICE);
        // 발견된 디바이스가 이미 페어링된 디바이스면 어댑처 추가 생략
        if (device.getBondState() != 
                                 BluetoothDevice.BOND_BONDED) {
          
          mNewDevicesArrayAdapter.add(device.getName() + "\n" +
                                          device.getAddress());
        }
        // 발견 프로세스가 종료하면 액티비티 타이틀 변경 
      } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.
                                              equals(action)) {
        setProgressBarIndeterminateVisibility(false);
        setTitle(R.string.select_device);
        if (mNewDevicesArrayAdapter.getCount() == 0) {
          String noDevices = getResources().
                       getText(R.string.none_found).toString();
          mNewDevicesArrayAdapter.add(noDevices);
        }
      }
    } 
  };

}
