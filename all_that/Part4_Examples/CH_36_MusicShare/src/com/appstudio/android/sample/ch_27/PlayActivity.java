package com.appstudio.android.sample.ch_27;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class PlayActivity extends Activity
	implements CreateNdefMessageCallback, OnNdefPushCompleteCallback{
    // for debugging
    private static final String TAG = "PlayActivity";
    private static final boolean D = false;
    
    //for nfc 
    NfcAdapter mNfcAdapter;
    private static final int NFC_MESSAGE_SENT = -1;
    private String other_device_macaddr = "";
    
    //for bluetooth message define
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    private static final int REQUEST_ENABLE_BT = 3;
    private BluetoothAdapter mBluetoothAdapter = null;
    private String mConnectedDeviceName = null;
    private BluetoothService mService = null;
	private static boolean IS_RECEIVER = false; // false : this is sender, true : this is receiver
	private static boolean IS_PLAYING = false; // true : playing, false : not playing

	
    //for Recording
	private File downloadingMediaFile;
	private FileOutputStream out;
	private int INTIAL_BUFFER = 96*BUF_SIZE*10/8;
	int totalBytesRead = 0;
	public static int BUF_SIZE = 348;
	private static boolean RECORING = false;
    private Thread sendThread = null;
	//for Playing
    private MediaPlayer player = null;
    private long prevTm, curTm, calcTm;
    private static long DURATION = 1000; // 3s珥덇컙寃��ъ깮
    Button send_button = null;

	//for action
	private static boolean IS_SEND_BUTTON_CLICK = false; // true : click, false : not click
	private AudioManager m_amAudioManager;  

	//for dialog
    private List mFileList = new ArrayList();
    private File mPath = new File(Environment.getExternalStorageDirectory(),"");
    private static final String[] FTYPE = {"mp3","wav"};    
    private static final int DIALOG_LOAD_FILE = 1000;
    private static String file_nm = null;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
		if(D) Log.d(TAG,"onCreate()");
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
        	ActionBar bar = getActionBar();
        	bar.setSubtitle(R.string.NFC_NOT_CONNECTED);
        }

		if(D) Log.d(TAG,"mNfcAdapter " + mNfcAdapter);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        
		if(D) Log.d(TAG,"mBluetoothAdapter " + mBluetoothAdapter);
		
        send_button = (Button)findViewById(R.id.send_button);
        send_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				if(!IS_SEND_BUTTON_CLICK){
					if(file_nm == null){
						Toast.makeText(getApplicationContext(), 
		                		R.string.plz_select_file, Toast.LENGTH_LONG).show();
						return;
					}
					send_button.setText(R.string.stop_button);
					send_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stop, 0, 0, 0);
			        IS_SEND_BUTTON_CLICK = true;
					startSend();
				}else{
					send_button.setText(R.string.send_button);
					send_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.play, 0, 0, 0);
					IS_SEND_BUTTON_CLICK = false;
					stopSend();
				}
			}

        });
       
        final Button select_button = (Button) this.findViewById(R.id.select_button);
        select_button.setOnClickListener(new OnClickListener(){
        	@Override
			public void onClick(View v) {
        		onCreateDialog(DIALOG_LOAD_FILE);
        	}
        });
        
        getActionBar().setSubtitle(R.string.start_actionbar);
        
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        
               
        
    }
	
    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }
    
	@Override
	public void onNdefPushComplete(NfcEvent event) {
		if(D) Log.d(TAG, "onNdefPushComplete");
		mHandler.obtainMessage(NFC_MESSAGE_SENT).sendToTarget();
	}


	@Override
	public NdefMessage createNdefMessage(NfcEvent event) {
		if(D) Log.d(TAG, "createNdfMessage");
		String macAddr = mBluetoothAdapter.getAddress();
		if(D) Log.d(TAG, "createNdfMessage macAddr is "  + macAddr);
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMimeRecord(
                        "text/plain", macAddr.getBytes())
        });
		if(D) Log.d(TAG, "createNdfMessage return msg : " + msg);
        return msg;

	}
    private int receiveCnt = 0;
    private int recordCnt = 0;
    
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	if(D) Log.d(TAG, "handleMessage return msg : " + msg.what);
            switch (msg.what) {
            case NFC_MESSAGE_SENT:
                Toast.makeText(getApplicationContext(), 
                		R.string.sent_macaddress, Toast.LENGTH_LONG).show();
                break;
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
	                case BluetoothService.STATE_CONNECTED:
	                    setStatus(getString(R.string.title_connected_to) + " " + mConnectedDeviceName);
	                    break;
	                case BluetoothService.STATE_CONNECTING:
	                    setStatus(R.string.title_connecting);
	                    break;
	                case BluetoothService.STATE_LISTEN:
	                case BluetoothService.STATE_NONE:
	                    setStatus(R.string.title_not_connected);
	                    break;
                }
                break;
            case MESSAGE_WRITE:
                break;
            case MESSAGE_READ:
                IS_RECEIVER = true;
                receiveCnt++;
                byte[] readBuf = (byte[]) msg.obj;
				writeBufferFile(readBuf);
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_DEVICE_NAME:
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), getString(R.string.connect_to)+ " " + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            }
        }

    };

    
    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }
    
    @Override
    public synchronized void onPause() {
        super.onPause();
        if (mNfcAdapter != null) mNfcAdapter.disableForegroundDispatch(this);
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        stopSend();
        try {
        	if(out != null){
        		out.flush();
        		out.close();
        	}
		} catch (IOException e) {}
        if(D) Log.e(TAG, "-- ON STOP --");
    }
    
    @Override
    public void onDestroy() {
     
      super.onDestroy();
    }
    
    @Override
    public synchronized void onResume() {
        super.onResume();
        reset();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mService == null) setupBluetooth();
        }
    }
    
    public void reset(){
    	if(D) Log.e(TAG, "reset()");
		m_amAudioManager = (AudioManager)getSystemService(AUDIO_SERVICE);  
    	m_amAudioManager.setMode(AudioManager.MODE_NORMAL); 
    	
    	if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
        if (mService != null) {
            if (mService.getState() == BluetoothService.STATE_NONE) {
              mService.start();
            }
        }
        connected();
        if(player == null){
			player = new MediaPlayer();
        }
        receiveCnt = 0; recordCnt = 0;
    }
    
    private void connected(){
    	if(D) Log.e(TAG, "connected()");
		try {
			downloadingMediaFile = new File("/sdcard","downloadingMedia.dat");
			out = new FileOutputStream(downloadingMediaFile);
			if(D) Log.d(TAG, "FileOutPutStream name " + downloadingMediaFile +":"+out);
		} catch (IOException e) {
			if(D) Log.d(TAG, "downloadfile error " + e.getMessage());
		}
    }
    
    private void setupBluetooth() {
        if(D) Log.d(TAG, "setupBluetooth()");
        mService = new BluetoothService(this, mHandler);
	}

	void processIntent(Intent intent) {
		if(D) Log.d(TAG, "processIntent()");
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        other_device_macaddr = new String(msg.getRecords()[0].getPayload());
        getActionBar().setSubtitle(other_device_macaddr);
        connectDevice(true);
    }

    
    private void connectDevice(boolean secure) {
		if(D) Log.d(TAG, "connectDevice()");
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(other_device_macaddr);
        mService.connect(device, secure);
    }
    
    private void startSend() {
    	if(D) Log.d(TAG, "startSend()"+downloadingMediaFile);
    	
//    	if(downloadingMediaFile != null && downloadingMediaFile.length() > 1){
//    		connected();
//    	}
//        if (mService.getState() != BluetoothService.STATE_CONNECTED) {
//            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
//            return;
//        }
        if(IS_RECEIVER){
        	Toast.makeText(this, R.string.already_music_playing, Toast.LENGTH_SHORT).show();
            return;
        }
    	RECORING = true;

    	sendThread = new Thread(){
        	public void run(){
        		File f = new File(file_nm);
        		InputStream fi = null;

        		byte[] buf = new byte[BUF_SIZE];
				try {
					fi = new FileInputStream(f);
				} catch (Exception e1) {if(D) Log.d(TAG,"fileinputstream:"+e1.getMessage());}

				int nread = 0;
        		while(RECORING && nread != -1){
	       			if(D) Log.d(TAG, "startSend() nread:"+nread);
	    			try {
	    				recordCnt++;
	    				nread = fi.read(buf, 0, BUF_SIZE);
		    			mService.write(buf);
						writeBufferFile(buf);
					} catch (Exception e) {if(D) Log.d(TAG,"writebuffer:"+e.getMessage());}
        		}
        	}

        };
        sendThread.start();

    }

    private void writeBufferFile(byte[] buf){
		if(D) Log.d(TAG, "writeBufferFile()");
		if(receiveCnt == 1){
			IS_PLAYING = true;
			IS_RECEIVER = true;
			Toast.makeText(this, R.string.music_playing, Toast.LENGTH_SHORT).show();
	    	connected();
			send_button.setText(R.string.stop_button);
			send_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.stop, 0, 0, 0);
	        IS_SEND_BUTTON_CLICK = true;
		}
		if(recordCnt == 1){
			IS_PLAYING = true;
			connected();
		}
		
		try {
			if(buf!=null && out !=null){
				totalBytesRead += buf.length;
				out.write(buf);
		        out.flush();
			}
	        if(D) Log.d(TAG, "writeBufferFile is " + totalBytesRead);
	        
			if(totalBytesRead >= INTIAL_BUFFER){
				curTm = totalBytesRead;
				calcTm += (curTm - prevTm);
				if(D) Log.d(TAG,"calcTm:"+calcTm);
				if(calcTm > DURATION*1024){
					firePlayer();
					calcTm = 0L;
				}
				prevTm = curTm;
			}

		} catch (Exception e) {
			if(D) Log.d(TAG,"writeBufferFile Exception is " + e.getMessage());
		}
	}
	
    private void firePlayer(){
    	Thread t = new Thread(){
    		public void run(){
    			try {
					startPlayer();
				} catch (Exception e) {if(D) Log.d(TAG,"firePlayer Exception:"+e.getMessage());}
    		}
    	};
    	t.start();
    }
    
	private void startPlayer() throws Exception {
		if(D) Log.d(TAG, "startPlayer()");
		if(player == null){
	        player = new MediaPlayer();
        }
		boolean wasPlaying = player.isPlaying();
		if(D) Log.d(TAG,"wasPlaying:"+wasPlaying);
		int curPosition = player.getCurrentPosition();
		if(D) Log.d(TAG,"downloadingMediaFile size " + downloadingMediaFile.length() );
		FileInputStream fi = new FileInputStream(downloadingMediaFile.getAbsolutePath());
		player.reset();
		player.setDataSource(fi.getFD());
		player.prepare();
		player.seekTo(curPosition);
		
		boolean atEndOfFile = (player.getDuration()-player.getCurrentPosition()) <= 100;
		
		if(D) Log.d(TAG,"atEndOfFile:"+atEndOfFile);
		if(D) Log.d(TAG,"wasPlaying || atEndOfFile:"+(wasPlaying || atEndOfFile));
		player.start();
		
		player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            	IS_RECEIVER = false;
            	IS_PLAYING = false;
            }
        });
	}

	private void stopPlay(){
		if(D) Log.d(TAG, "stopPlay()");
		try {
			IS_PLAYING = false;
			receiveCnt = 0; recordCnt = 0;
			if(player != null && player.isPlaying()){
	            player.pause();
			}
			downloadingMediaFile.delete();
		} catch (Exception e) {
            Log.e(TAG, "stopPlay() failed");
        }
	}
	
	private void stopSend() {
		if(D) Log.d(TAG, "stopRecording()");
		try {
			RECORING = false;
			IS_RECEIVER = false;
			stopPlay();
        } catch (Exception e) {
            Log.e(TAG, "stopRecording() failed");
        }
	}

    private final void setStatus(int resId) {
    	if(D) Log.d(TAG,"setStatus(int resId) " + resId);
        getActionBar().setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
    	if(D) Log.d(TAG,"setStatus(CharSequence subTitle) " + subTitle);
        getActionBar().setSubtitle(subTitle);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	if(D) Log.d(TAG, "onCreateOptionsMenu()");
        if (mNfcAdapter == null) {
            return super.onCreateOptionsMenu(menu);
        }
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if(D) Log.d(TAG, "onOptionsItemSelected()");
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(Settings.ACTION_NFCSHARING_SETTINGS);
                startActivity(intent);
                return true;
            case R.id.menu_reset:
            	reset();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    /**
     * For Dialog get File list
     * @param file
     */
    private void loadAllAudioList(File file){
    	if(D) Log.d(TAG, "loadAllAudioList:"+ file.getName());
        if (file != null && file.isDirectory())
        {
           File[] children = file.listFiles();
           if (children != null)
           {
               for(int i = 0; i < children.length; i++)
               {
                   if (children[i] != null)
                   {
                	   for(int j=0;j<FTYPE.length;j++){
                		   if(FTYPE[j].equals(children[i].getName().substring(children[i].getName().lastIndexOf(".")+1,
                    			   children[i].getName().length()))){
                			   mFileList.add(children[i].getAbsolutePath());
                               if(D) Log.d(TAG, children[i].getName());
                		   }
                	   }
                   }
                   loadAllAudioList(children[i]);
               }
            }
        }
	}
    
    protected Dialog onCreateDialog(int id){
    	mFileList.clear();
		loadAllAudioList(mPath);
		Dialog dialog = null;
		AlertDialog.Builder builder = new Builder(this);
		if(D) Log.d(TAG, "onCreateDialog" + mFileList.size());

	  switch(id){
		  case DIALOG_LOAD_FILE:
		   builder.setTitle("Choose your file");
		   if(mFileList == null){
		     Log.e(TAG, "Showing file picker before loading the file list");
		     dialog = builder.create();
		     return dialog;
		   }
		 builder.setItems((CharSequence[]) mFileList.toArray(new String[mFileList.size()]), new DialogInterface.OnClickListener(){
		   public void onClick(DialogInterface dialog, int which){
		      file_nm = (String) mFileList.get(which);
		      setFilename(file_nm);
		   }
		  });
	     break;
	  }
	  dialog = builder.show();
	  return dialog;
	 } 
    
    public void setFilename(String file_name){
    	TextView  tx = (TextView)findViewById(R.id.file_name);
    	tx.setText(file_name);
    }
}