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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;

import com.appstudio.android.sample.R;

/**
 * wifi 이벤트에 대한 브로드캐스트 리시버
 */
public class WiFiDirectBroadcastReceiver 
                                    extends BroadcastReceiver {

  private WifiP2pManager manager;
  private Channel channel;
  private WiFiDirectActivity activity;

  public WiFiDirectBroadcastReceiver(WifiP2pManager manager,
                Channel channel, WiFiDirectActivity activity) {
    super();
    this.manager = manager;
    this.channel = channel;
    this.activity = activity;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    String action = intent.getAction();
    // 와이파이 다이렉트의 가능여부 상태 변경
    // 최초 한번은 무조건 WIFI_P2P_STATE_CHANGED_ACTION 발송 
    if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(
                                                     action)) {
      Log.d(WiFiDirectActivity.TAG, "WIFI_P2P_STATE_CHANGED_ACTION");
      // wifi 상태를 보여주도록 UI 수정
      int state = intent.getIntExtra(
                          WifiP2pManager.EXTRA_WIFI_STATE, -1);
      if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
        activity.setIsWifiP2pEnabled(true);
      } else {
        activity.setIsWifiP2pEnabled(false);
        activity.resetData();
      }
      Log.d(WiFiDirectActivity.TAG, "P2P 상태 변경" + state);
    // 피어 발견 시   
    } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.
                                              equals(action)) {
      // 연결이 가능한 피어 조회를 요청한다. 비동기 메서드이기 때문에
      // 콜백 메서드가 사용된다. 
      if (manager != null) {
        manager.requestPeers(channel, (PeerListListener)
                                 activity.getFragmentManager().
                             findFragmentById(R.id.frag_list));
      }
      Log.d(WiFiDirectActivity.TAG, "P2P peers changed");
    // 디바이스의 와이파이 연결 상태 변경  
    } else if (WifiP2pManager.
           WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
      if (manager == null) {
        return;
      }

      NetworkInfo networkInfo = (NetworkInfo) intent
        .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);

      if (networkInfo.isConnected()) {
        // 다른 디바이스와 연결을 위해서
        // 그룹 오너 IP를 찾기 위해 연결 정보를 요청한다.
        DeviceDetailFragment fragment = (DeviceDetailFragment) 
                                 activity.getFragmentManager().
                            findFragmentById(R.id.frag_detail);
        
        manager.requestConnectionInfo(channel, fragment);
      } else {
        // 연결이 없으면
        activity.resetData();
      }
    // 디바이스 변경 시   
    } else if (WifiP2pManager.
          WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
      
      DeviceListFragment fragment = (DeviceListFragment)
                                 activity.getFragmentManager().
                              findFragmentById(R.id.frag_list);
      
      fragment.updateThisDevice((WifiP2pDevice) intent.
                             getParcelableExtra(WifiP2pManager.
                                       EXTRA_WIFI_P2P_DEVICE));

    }
  }
}
