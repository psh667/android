package com.appstudio.android.sample.ch_31_2;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.appstudio.android.sample.R;
import com.appstudio.appengine.sample.User;
import com.appstudio.appengine.sample.UserService;

public class AppEngineActivity extends ListActivity {
  final public static String TAG = "appstudio";
  private EditText mTelNo;
  private EditText mName;
  private ArrayAdapter<User> mAdapter; 

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(
        Window.FEATURE_INDETERMINATE_PROGRESS);

    setContentView(R.layout.appengine_activity);
    mTelNo = (EditText)findViewById(R.id.telNo);
    mName = (EditText)findViewById(R.id.name);
    Button button = (Button)findViewById(R.id.add);
    button.setOnClickListener(mClicker);
    
    mAdapter = new ArrayAdapter<User>(AppEngineActivity.this, 
                           android.R.layout.simple_list_item_1, 
                           new ArrayList<User>());
    setListAdapter(mAdapter);
    new AllListTask().execute();
  }  
  
  OnClickListener mClicker = new OnClickListener(){
    @Override
    public void onClick(View v) {
      new AddTask().execute();
    }    
  };
  
  private class AllListTask extends 
                   AsyncTask<Object, Object, ArrayList<User>> {

    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected ArrayList<User> doInBackground(Object... params) {
      ServerManager sManager = ServerManager.getInstance();
      ArrayList<NameValuePair> paramList = 
                                new ArrayList<NameValuePair>();
      NameValuePair param = new BasicNameValuePair(
                    UserService.ACTION, UserService.ALL_LIST );
      paramList.add(param);      
      ArrayList<User> list = (ArrayList<User>)sManager.process(
                     "user", paramList, new ArrayList<User>());
      return list;
    }

    @Override
    protected void onPostExecute(ArrayList<User> result) {
      super.onPostExecute(result);
      ArrayList<User> list = (ArrayList<User>) result;
      setProgressBarIndeterminateVisibility(false);  
      mAdapter.clear();
      mAdapter.addAll(list);
      mAdapter.notifyDataSetChanged();     
    }
  }  

  private class AddTask extends 
                            AsyncTask<Object, Object, Object> {
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      setProgressBarIndeterminateVisibility(true);
    }

    @Override
    protected Object doInBackground(Object... params) {
      ServerManager sManager = ServerManager.getInstance();
      String telNo = mTelNo.getText().toString();
      String name = mName.getText().toString();
      if(telNo==null || "".equals(telNo))  {
        return null;
      }
      ArrayList<NameValuePair> paramList = 
                                new ArrayList<NameValuePair>();
      NameValuePair param = new BasicNameValuePair(
                      UserService.ACTION, UserService.UPSERT );
      NameValuePair param1 = new BasicNameValuePair(
                                          User.TEL_NO, telNo );
      NameValuePair param2 = new BasicNameValuePair(
                                             User.NAME, name );
      paramList.add(param);
      paramList.add(param1);
      paramList.add(param2);
      sManager.process("user", paramList, new ArrayList<User>());
      return null;
    }

    @Override
    protected void onPostExecute(Object result) {
      super.onPostExecute(result);
      mName.setText(null);
      mTelNo.setText(null);
      new AllListTask().execute();
    }  
  }  
}
      