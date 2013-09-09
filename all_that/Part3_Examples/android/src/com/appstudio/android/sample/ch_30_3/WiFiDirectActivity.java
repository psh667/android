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

package com.appstudio.android.sample.ch_30_3;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.appstudio.android.sample.R;
import com.appstudio.android.sample.ch_30_3.DeviceListFragment.DeviceActionListener;

/**
 * An activity that uses WiFi Direct APIs to discover and connect with available
 * devices. WiFi Direct APIs are asynchronous and rely on callback mechanism
 * using interfaces to notify the application of operation success or failure.
 * The application should also register a BroadcastReceiver for notification of
 * WiFi state related events.
 */
public class WiFiDirectActivity extends Activity implements ChannelListener, DeviceActionListener {

  public static final String TAG = "wifidirectdemo";
  private WifiP2pManager manager;
  private boolean isWifiP2pEnabled = false;
  private boolean retryChannel = false;

  private final IntentFilter intentFilter = new IntentFilter();
  private Channel channel;
  private BroadcastReceiver receiver = null;

  /**
   * @param isWifiP2pEnabled the isWifiP2pEnabled to set
   */
  public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
    this.isWifiP2pEnabled = isWifiP2pEnabled;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.wifidirect_activity);

    // wifi p2p 사용 가능 상태 변경
    intentFilter.addAction(WifiP2pManager.
                                WIFI_P2P_STATE_CHANGED_ACTION);
    // 피어 목록 변경
    intentFilter.addAction(WifiP2pManager.
                                WIFI_P2P_PEERS_CHANGED_ACTION);
    // p2p 연결 상태 변경
    intentFilter.addAction(WifiP2pManager.
                           WIFI_P2P_CONNECTION_CHANGED_ACTION);
    // 이 디바이스 상세 정보 변경
    intentFilter.addAction(WifiP2pManager.
                          WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    manager = (WifiP2pManager) 
                    getSystemService(Context.WIFI_P2P_SERVICE);
    // p2p 오퍼레이션을 부르기전에 호출되어 초기화
    // 어플리케이션과 와이파이 프레임워크를 연결하는 채널을 반환한다.
    channel = manager.initialize(this, getMainLooper(), null);
  }

  @Override
  public void onResume() {
    super.onResume();    
    receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
    registerReceiver(receiver, intentFilter);
  }

  @Override
  public void onPause() {
    super.onPause();
    unregisterReceiver(receiver);
  }

  /**
   * 화면의 피어 목록고 상세를 지운다.
   * 브로드캐스트 리시버가 상태 변경을 수신했을 때 호출
   */
  public void resetData() {
    DeviceListFragment fragmentList = (DeviceListFragment) getFragmentManager()
        .findFragmentById(R.id.frag_list);
    DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) getFragmentManager()
        .findFragmentById(R.id.frag_detail);
    if (fragmentList != null) {
      fragmentList.clearPeers();
    }
    if (fragmentDetails != null) {
      fragmentDetails.resetViews();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.wifidirect_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.atn_direct_enable:
        if (manager != null && channel != null) {
          // 시스템의 무선통신 관련 설정 액티비티를 기동
          // 결과값이 없으며 브로드캐스트 리시버로 변경 유무 알수 있음
          startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        } else {
          Log.e(TAG, "channel or manager is null");
        }
        return true;

      case R.id.atn_direct_discover:
        // 일단 wifi p2p 가 on 상태 인지 체크
        if (!isWifiP2pEnabled) {
          Toast.makeText(WiFiDirectActivity.this, R.string.p2p_off_warning,
              Toast.LENGTH_SHORT).show();
          return true;
        }
        final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
            .findFragmentById(R.id.frag_list);
        fragment.onInitiateDiscovery();
        // 피어 탐색을 위한 리스너
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {

          @Override
          public void onSuccess() {
            Toast.makeText(WiFiDirectActivity.this, "Discovery Initiated",
                Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onFailure(int reasonCode) {
            Toast.makeText(WiFiDirectActivity.this, "Discovery Failed : " + reasonCode,
                Toast.LENGTH_SHORT).show();
          }
        });
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void showDetails(WifiP2pDevice device) {
    DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()
        .findFragmentById(R.id.frag_detail);
    fragment.showDetails(device);
  }

  @Override
  public void connect(WifiP2pConfig config) {
    manager.connect(channel, config, new ActionListener() {

      @Override
      public void onSuccess() {
        // WiFiDirectBroadcastReceiver will notify us. Ignore for now.
      }

      @Override
      public void onFailure(int reason) {
        Toast.makeText(WiFiDirectActivity.this, "연결 실패",
            Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void disconnect() {
    final DeviceDetailFragment fragment = (DeviceDetailFragment) getFragmentManager()
        .findFragmentById(R.id.frag_detail);
    fragment.resetViews();
    manager.removeGroup(channel, new ActionListener() {

      @Override
      public void onFailure(int reasonCode) {
        Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);

      }

      @Override
      public void onSuccess() {
        fragment.getView().setVisibility(View.GONE);
      }

    });
  }

  @Override
  public void onChannelDisconnected() {
    // we will try once more
    if (manager != null && !retryChannel) {
      Toast.makeText(this, "Channel lost. Trying again", Toast.LENGTH_LONG).show();
      resetData();
      retryChannel = true;
      manager.initialize(this, getMainLooper(), this);
    } else {
      Toast.makeText(this,
          "Severe! Channel is probably lost premanently. Try Disable/Re-Enable P2P.",
          Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void cancelDisconnect() {

    /*
     * A cancel abort request by user. Disconnect i.e. removeGroup if
     * already connected. Else, request WifiP2pManager to abort the ongoing
     * request
     */
    Log.d(TAG, "activity cancelDisconnect");
    if (manager != null) {
      final DeviceListFragment fragment = (DeviceListFragment) getFragmentManager()
          .findFragmentById(R.id.frag_list);
      if (fragment.getDevice() == null
          || fragment.getDevice().status == WifiP2pDevice.CONNECTED) {
        disconnect();
      } else if (fragment.getDevice().status == WifiP2pDevice.AVAILABLE
          || fragment.getDevice().status == WifiP2pDevice.INVITED) {

        manager.cancelConnect(channel, new ActionListener() {

          @Override
          public void onSuccess() {
            Toast.makeText(WiFiDirectActivity.this, "Aborting connection",
                Toast.LENGTH_SHORT).show();
          }

          @Override
          public void onFailure(int reasonCode) {
            Toast.makeText(WiFiDirectActivity.this,
                "Connect abort request failed. Reason Code: " + reasonCode,
                Toast.LENGTH_SHORT).show();
          }
        });
      }
    }

    }
}
