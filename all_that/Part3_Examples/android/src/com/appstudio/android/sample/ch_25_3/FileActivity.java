package com.appstudio.android.sample.ch_25_3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FileActivity extends Activity {
    final private static String TAG = "appstudio";
    final private static String FILE_NAME = "appstudio.txt";
    private EditText mContentED;
    private TextView mStatusTV;
    private Button mSaveSDBT;
    private Button mLoadSDBT;
    private String mSdPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_activity);
        mContentED = (EditText) findViewById(R.id.editText1);
        mStatusTV = (TextView) findViewById(R.id.status);
        mSaveSDBT = (Button) findViewById(R.id.savetosd);
        mLoadSDBT = (Button) findViewById(R.id.loadfromsd);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String ext = Environment.getExternalStorageState();
        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            mSdPath = Environment.getExternalStorageDirectory()
                                            .getAbsolutePath();
            mStatusTV.setText("SDCard Mount");
            mSaveSDBT.setText("Save to "+mSdPath
                                +File.separatorChar+FILE_NAME);
            mLoadSDBT.setText("Load from "+mSdPath
                                +File.separatorChar+FILE_NAME);
        }else {
            mStatusTV.setText("SDCard Unmount");
        }
    }

    public void mOnClickLoadFromResource(View v)  {
        InputStream fres = null;
        try {
            fres = getResources().openRawResource(
                    R.raw.appstudio);
            inputStream2Display(fres);
            mStatusTV.setText("res/raw/"+FILE_NAME
                                        +"이 로드되었습니다.");
        } finally{
            try {if(fres!=null) fres.close();
            }catch(Exception e){};
        }
    }

    private void inputStream2Display(InputStream fres)  {
        BufferedInputStream bis = null;
        try {
            StringBuffer sb = new StringBuffer();
            bis = new BufferedInputStream(fres);
            byte[] buf = new byte[1024];
            int numRead=0;
            while((numRead=bis.read(buf)) != -1){
                String readData = new String(buf,0,numRead);
                sb.append(readData);
            }
            mContentED.setText(sb.toString());
        } catch (IOException e) {
            mStatusTV.setText(
                    "파일을 읽다가 IO 에러가 발생했습니다.");
        } finally {
            try {if(bis!=null) bis.close();
            }catch(Exception e){};
        }
    }
    
    public void mOnClickLoadFromSD(View v)  {
        FileInputStream fis = null;
        String filePath = null;
        try {
            filePath = mSdPath+ File.separatorChar + FILE_NAME;
            fis = new FileInputStream(mSdPath 
                    + File.separatorChar + FILE_NAME);
            inputStream2Display(fis);
            mStatusTV.setText(filePath+"이 로드되었습니다.");
        } catch (FileNotFoundException e) {
            mStatusTV.setText(
                    filePath +" 파일을 찾을 수가 없습니다.");
        } finally{
            try {if(fis!=null) fis.close();
            }catch(Exception e){};
        }
    }
    
    public void mOnClickLoadFromLocal(View v)  {
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            inputStream2Display(fis);
            mStatusTV.setText(FILE_NAME+"이 로드되었습니다.");
        } catch (FileNotFoundException e) {
            mStatusTV.setText(
                    FILE_NAME +" 파일을 찾을 수가 없습니다.");
        } finally{
            try {if(fis!=null) fis.close();
            }catch(Exception e){};
        }
    }
    
    public void mOnClickSaveToSD(View v)  {
        BufferedOutputStream bos = null;
        String filePath = null;
        try {
            filePath = mSdPath+ File.separatorChar + FILE_NAME;
            bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bos.write(mContentED.getText().toString().getBytes());
            mStatusTV.setText(filePath+"로 저장되었습니다.");
        } catch (FileNotFoundException ex) {
            mStatusTV.setText(filePath +" 파일을 찾을 수가 없습니다.");
        } catch (IOException ex) {
            mStatusTV.setText(filePath 
                    +" 파일을 IO하다가 에러가 발생했습니다.");
        } finally {
            try{if (bos != null)  {
                bos.flush();
                bos.close();}
            }catch(Exception ex) {};
        }
    }
    
    public void mOnClickSaveToLocal(View v)  {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(openFileOutput(
                    FILE_NAME, Context.MODE_PRIVATE));
            bos.write(mContentED.getText()
                                       .toString().getBytes());
            mStatusTV.setText(FILE_NAME+"가 저장되었습니다.");
        } catch (FileNotFoundException ex) {
            mStatusTV.setText(FILE_NAME 
                    +" 파일을 찾을 수가 없습니다.");
        } catch (IOException ex) {
            mStatusTV.setText(FILE_NAME 
                    +" 파일을 IO하다가 에러가 발생했습니다.");
        } finally {
            try{if (bos != null)  {
                bos.flush();
                bos.close();}
            }catch(Exception ex) {};
        }
    }
}