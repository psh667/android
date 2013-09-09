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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 블루투스 연결을 준비하고 관리하는 클래스이다.
 * 연결 요청을 수락하기 위한 쓰레드(AcceptThread),  
 * 연결 요청을 하는 쓰레드(ConnectThread) 
 * 데이터를 주고 받는 쓰레드가 있다(ConnectedThread). 
 */
public class BluetoothChatService {
  // 디버깅용 
  private static final String TAG = "BluetoothChatService";
  private static final boolean D = true;

  // 비보안 연결과 보안 연결 구분  
  private static final String NAME_SECURE = 
                                         "BluetoothChatSecure";
  private static final String NAME_INSECURE = 
                                       "BluetoothChatInsecure";

  // 보안 연결을 위한 UUID
  private static final UUID MY_UUID_SECURE =
       UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
  // 비보안 연결을 위한 UUID
  private static final UUID MY_UUID_INSECURE =
        UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

  // Member fields
  private final BluetoothAdapter mAdapter;
  private final Handler mHandler;
  private AcceptThread mSecureAcceptThread;
  private AcceptThread mInsecureAcceptThread;
  private ConnectThread mConnectThread;
  private ConnectedThread mConnectedThread;
  private int mState;
  
  // 아무것도 안하고 있는 상태를 나타냄 
  public static final int STATE_NONE = 0;   
  // 연결 요청이 들어오기를 기다리고는 상태를 나타냄 
  public static final int STATE_LISTEN = 1;    
  // 연결 요청을 한 상태를 나타냄 
  public static final int STATE_CONNECTING = 2;
  // 원격 디바이스와 연결이 된 상태를 나타냄
  public static final int STATE_CONNECTED = 3; 

  public BluetoothChatService(Context context,Handler handler){
    mAdapter = BluetoothAdapter.getDefaultAdapter();
    mState = STATE_NONE;
    mHandler = handler;
  }

  /**
   * 현재 상태를 설정함. 메인 액티비티에도 전달해서 
   * 그에 따른 처리를 수행한다. 
   */
  private synchronized void setState(int state) {
    if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
    mState = state;

    // 상태를 액티비티에도 전달한다. 
    mHandler.obtainMessage(BluetoothChat.MESSAGE_STATE_CHANGE, 
                                     state, -1).sendToTarget();
  }

  public synchronized int getState() {
    return mState;
  }

  /**
   * BluetoothChat 액티비티가 resume될 때 start()호출 
   * AcceptThread를 시작하여 서버로서 연결 요청 대기를 한다. 
   * 보안, 비보안 두개이기 때문에 AcceptThread도 두개이다.
   */ 
  public synchronized void start() {
    if (D) Log.d(TAG, "start");

    // 연결 요청(outgoing) 쓰레드 중지 
    if (mConnectThread != null) {
      mConnectThread.cancel(); 
      mConnectThread = null;
    }

    // 연결 쓰레드 중지
    if (mConnectedThread != null) {
      mConnectedThread.cancel(); 
      mConnectedThread = null;
    }

    setState(STATE_LISTEN);

    // 서버 쓰레드는 연결 요청을 대기하고 있다가 연결 요청을 수락하면
    // 연결 쓰레드를 생성한다. 보안과 비보안으로 구분하여 대기한다.
    // 보안과 비보안 구분하여 대기 하기 때문에 UUID도 별개로 주었다. 
    if (mSecureAcceptThread == null) {
      mSecureAcceptThread = new AcceptThread(true);
      mSecureAcceptThread.start();
    }
 
    if (mInsecureAcceptThread == null) {
      mInsecureAcceptThread = new AcceptThread(false);
      mInsecureAcceptThread.start();
    }
  }

  /**
   * BluetoothChat 메인 액티비티에서 첫째 검색 아이콘을 선택했으면 
   * 보안연결, 두번째 검색 아이콘을 선택했으면 비보안 연결
   * 연결 요청을 위해 쓰레드를 사용한다. 
   */
  public synchronized void connect(
                      BluetoothDevice device, boolean secure) {
    if (D) Log.d(TAG, "connect to: " + device);

    // 연결요청을 하고 있는 쓰레드, 연결된 쓰레드 모두 중지
    if (mState == STATE_CONNECTING) {
      if (mConnectThread != null) {
        mConnectThread.cancel(); 
        mConnectThread = null;
      }
    }
    if (mConnectedThread != null) {
      mConnectedThread.cancel(); 
      mConnectedThread = null;
    }

    // 이 디바이스가 클라이언트 역할을 수행 중 
    // 연결 요청을 위한 쓰레드 시작 
    mConnectThread = new ConnectThread(device, secure);
    mConnectThread.start();
    setState(STATE_CONNECTING);
  }

  /**
   * 서버 소켓은 대기하고 있다가 클라이언트로부터의 연결 요청을 수락하면
   * 연결을 맺기 위해 이 메서드를 호출한다. 
   * 서버에서 연결이 맺어질때도 별개의 쓰레드로 동작 
   */
  public synchronized void connected(BluetoothSocket socket, 
             BluetoothDevice device, final String socketType) {
    if (D) Log.d(TAG, "connected, Socket Type:" + socketType);

    // 연결 요청 쓰레드 중지 
    if (mConnectThread != null) {
      mConnectThread.cancel(); 
      mConnectThread = null;
    }

    // 이전에 사용되고 있던 연결된 쓰레드 중지 
    if (mConnectedThread != null) {
      mConnectedThread.cancel(); mConnectedThread = null;}

    // 새로운 연결된 쓰레드 생성 및 시작 
    mConnectedThread = new ConnectedThread(socket, socketType);
    mConnectedThread.start();
    try{Thread.sleep(1000);}catch(Exception e1){};
    // 연결이 맺어진 다음에는 서버활동은 중지 
    if (mSecureAcceptThread != null) {
      mSecureAcceptThread.cancel();
      mSecureAcceptThread = null;
    }
    if (mInsecureAcceptThread != null) {
      mInsecureAcceptThread.cancel();
      mInsecureAcceptThread = null;
    }


    // 연결이 맺어졌다는 정보를 액티비티로 전달
    Message msg = mHandler.obtainMessage(
                            BluetoothChat.MESSAGE_DEVICE_NAME);
    
    Bundle bundle = new Bundle();
    bundle.putString(BluetoothChat.DEVICE_NAME, 
                                             device.getName());
    
    msg.setData(bundle);
    mHandler.sendMessage(msg);

    setState(STATE_CONNECTED);
  }

  /**
   * 메인 액티비티 BluetoothChat이 소멸될 때 호출
   */
  public synchronized void stop() {
    if (D) Log.d(TAG, "stop");
    if (mConnectThread != null) {
      mConnectThread.cancel();
      mConnectThread = null;
    }
    if (mConnectedThread != null) {
      mConnectedThread.cancel();
      mConnectedThread = null;
    }
    if (mSecureAcceptThread != null) {
      mSecureAcceptThread.cancel();
      mSecureAcceptThread = null;
    }
    if (mInsecureAcceptThread != null) {
      mInsecureAcceptThread.cancel();
      mInsecureAcceptThread = null;
    }
    setState(STATE_NONE);
  }

  public void write(byte[] out) {
    ConnectedThread r;
    synchronized (this) {
      if (mState != STATE_CONNECTED) return;
      r = mConnectedThread;
    }
    r.write(out);
  }

  /**
   * 연결 될 수 없다는 토스트 메시지를 액티비티에 출력할 것을 요청 한다. 
   * 서비스를 다시 기동한다. 
   */
  private void connectionFailed() {
    Message msg = 
           mHandler.obtainMessage(BluetoothChat.MESSAGE_TOAST);
    Bundle bundle = new Bundle();
    bundle.putString(BluetoothChat.TOAST, 
                                   "Unable to connect device");
    msg.setData(bundle);
    mHandler.sendMessage(msg);
    // 서비스를 다시 시작한다.
    BluetoothChatService.this.start();
    }

  /**
   * 연결 될 수 없다는 토스트 메시지를 액티비티에 출력할 것을 요청 한다.
   * 서비스를 다시 기동한다. 
   */
  private void connectionLost() {
    Message msg = 
           mHandler.obtainMessage(BluetoothChat.MESSAGE_TOAST);
    Bundle bundle = new Bundle();
    bundle.putString(BluetoothChat.TOAST, 
                                 "Device connection was lost");
    msg.setData(bundle);
    mHandler.sendMessage(msg);
    // 서비스를 다시 시작한다.
    BluetoothChatService.this.start();
  }

  /**
   * 서버측에서 연결 요청이 들어올때까지 기다리는 데 이를 위한 쓰레드이다.
   * 연결 요청이 수락되면 연결된 쓰레드를 생성한다. 
   */
  private class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private String mSocketType;

    public AcceptThread(boolean secure) {
      BluetoothServerSocket tmp = null;
      mSocketType = secure ? "Secure":"Insecure";

      // 리스닝 서버 소켓 생성 
      try {
        if (secure) {
          tmp = mAdapter.listenUsingRfcommWithServiceRecord(
                                   NAME_SECURE,MY_UUID_SECURE);
        } else {
          tmp = mAdapter.
                    listenUsingInsecureRfcommWithServiceRecord(
                              NAME_INSECURE, MY_UUID_INSECURE);
        }
      } catch (IOException e) {
        Log.e(TAG, "Socket Type: " + mSocketType + 
                                         "listen() failed", e);
      }
      mmServerSocket = tmp;
    }

    public void run() {
      if (D) Log.d(TAG, "Socket Type: " + mSocketType +
                                 "BEGIN mAcceptThread" + this);
      setName("AcceptThread" + mSocketType);

      BluetoothSocket socket = null;

      // 연결 상태가 아니면 서버 소켓을 대기한다. 
      while (mState != STATE_CONNECTED) {
        try {
          // 클라이언트에서 연결 요청이 들어올때까지 블러킹 된다. 
          // 이런 이유로 쓰레드에서 수행된다.
          socket = mmServerSocket.accept();
        } catch (IOException e) {
          Log.e(TAG, "Socket Type: " + mSocketType + 
                                         "accept() failed", e);
          break;
        }
        // 클라이언트로 부터의 연결 요청이 수락되면 이곳이 수행된다. 
        if (socket != null) {
          synchronized (BluetoothChatService.this) {
            switch (mState) {
            case STATE_LISTEN:
            case STATE_CONNECTING:
              // 연결 요청 대기 중이었거나, 연결 요청 중이었음을 나타냄
              connected(socket, socket.getRemoteDevice(),
                                                  mSocketType);
              break;
            case STATE_NONE:
            case STATE_CONNECTED:
              // 이미 수립된 연결은 닫는다. 
              try {
                socket.close();
              } catch (IOException e) {
                Log.e(
                    TAG, "Could not close unwanted socket", e);
              }
              break;
            }
          }
        }
      }
    }

    public void cancel() {
      try {
        mmServerSocket.close();
      } catch (IOException e) {
        Log.e(TAG, "Socket Type" + mSocketType + 
                                "close() of server failed", e);
      }
    }
  }


  /**
   * outgoing 연결, 연결 요청을 위한 쓰레드
   * 서버는 비보안, 보안 모두 쓰레드가 떠있고
   * 클라이언트는 첫번째 메뉴에 의해 검색을 했는지 
   * 두번째 메뉴에 의해 검색했는지에 따라 보안, 비보안 연결 구분  
   */
  private class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private String mSocketType;

    public ConnectThread(
                      BluetoothDevice device, boolean secure) {
      mmDevice = device;
      BluetoothSocket tmp = null;
      mSocketType = secure ? "Secure" : "Insecure";

      try {
        if (secure) {
          tmp = device.createRfcommSocketToServiceRecord(
                                               MY_UUID_SECURE);
        } else {
          tmp=device.createInsecureRfcommSocketToServiceRecord(
                                             MY_UUID_INSECURE);
        }
      } catch (IOException e) {
        Log.e(TAG, "Socket Type: " + mSocketType + 
                                         "create() failed", e);
      }
      mmSocket = tmp;
    }

    public void run() {
      setName("ConnectThread" + mSocketType);

      // 연결이 되면 발견 프로세스는 중지한다.
      // 발견 프로세스는 자원을 소비해서 연결이 느려지거나 심지어 
      // 끊어질 수도 있다. 
      mAdapter.cancelDiscovery();
      try {
        mmSocket.connect();
      } catch (IOException e) {
        try {
          mmSocket.close();
        } catch (IOException e2) {
          Log.e(TAG, "unable to close() " + mSocketType +
                      " socket during connection failure", e2);
        }
        connectionFailed();
        return;
      }
      synchronized (BluetoothChatService.this) {
        mConnectThread = null;
      }
      connected(mmSocket, mmDevice, mSocketType);
    }

    public void cancel() {
      try {
        mmSocket.close();
      } catch (IOException e) {
        Log.e(TAG, "close() of connect " + mSocketType + 
                                          " socket failed", e);
      }
    }
  }

  /**
   * 연결된 쓰레드, 실질적인 데이터 통신을 담당하는 쓰레드이다.
   * 서버, 클라이언트 모두 사용된다.
   * 서버는 대기하고 있다가 연결요청이 들어오면 이 쓰레드가 생성되고
   * 클라이언트는 사용자가 특정 디바이스를 선택하면 
   * 연결 쓰레드(ConnectedThread)가 생성되었다가, 
   * 이 연결된 쓰레드로 이어진다.    
   */
  private class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    public ConnectedThread(
                   BluetoothSocket socket, String socketType) {
      mmSocket = socket;
      InputStream tmpIn = null;
      OutputStream tmpOut = null;

      // 소켓으로부터 입력, 출력 스트림을 획득한다. 
      try {
        tmpIn = socket.getInputStream();
        tmpOut = socket.getOutputStream();
      } catch (IOException e) {
        Log.e(TAG, "temp sockets not created", e);
      }

      mmInStream = tmpIn;
      mmOutStream = tmpOut;
    }

    public void run() {
      Log.i(TAG, "BEGIN mConnectedThread");
      byte[] buffer = new byte[1024];
      int bytes;

      // 연결 되있는 동안 입력 스트림을 대기한다. 
      while (true) {
        try {
          // 입력 스트림으로부터 읽어들인다. 
          bytes = mmInStream.read(buffer);

          // 읽어들인 입력값을 액티비티에 전달한다. 
          mHandler.obtainMessage(BluetoothChat.MESSAGE_READ, 
                             bytes, -1, buffer).sendToTarget();
        } catch (IOException e) {
          Log.e(TAG, "disconnected", e);
          connectionLost();
          // 에러 발생 시 블루투스 서비스를 재기동한다. 
          BluetoothChatService.this.start();
          break;
        }
      }
    }

    public void write(byte[] buffer) {
      try {
        mmOutStream.write(buffer);
        // 메시지를 전송하고, 전송한 메시지를 액티비티에도 전달한다. 
        mHandler.obtainMessage(BluetoothChat.MESSAGE_WRITE, 
                                -1, -1, buffer).sendToTarget();
      } catch (IOException e) {
        Log.e(TAG, "Exception during write", e);
      }
    }

    public void cancel() {
      try {
        mmSocket.close();
      } catch (IOException e) {
        Log.e(TAG, "close() of connect socket failed", e);
      }
    }
  }
}
