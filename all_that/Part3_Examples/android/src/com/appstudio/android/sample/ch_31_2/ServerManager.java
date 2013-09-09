package com.appstudio.android.sample.ch_31_2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.google.gson.Gson;

public class ServerManager {
  public final static String VERSION = "1.0";
//  public final static String SERVER = 
//                     "https://appstudiozero01.appspot.com/";
  public final static String SERVER = 
                               "http://192.168.123.126:8888/";
  private static ServerManager _instance= new ServerManager();
  private HttpClient _httpClient;
  private ServerManager()  {
    HttpParams params = new BasicHttpParams();
    HttpProtocolParams.setVersion(params,HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(params, "utf-8");
    params.setBooleanParameter("http.protocol.expect-continue",
                               false);
    SchemeRegistry registry = new SchemeRegistry();
    registry.register(new Scheme("http", 
                         PlainSocketFactory.getSocketFactory(),
                         8080));
    final SSLSocketFactory sslSocketFactory = 
                          SSLSocketFactory.getSocketFactory();
    sslSocketFactory.setHostnameVerifier(
                 SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    registry.register(
                   new Scheme("https", sslSocketFactory, 443));
    ThreadSafeClientConnManager manager = 
             new ThreadSafeClientConnManager(params, registry);
    _httpClient = new DefaultHttpClient(manager, params);				
  }
  
  public static ServerManager getInstance() {
    synchronized(ServerManager.class) {
      if (_instance == null) {
        _instance = new ServerManager();
      }
    }
    return _instance;
  }
  public Object process(String postUrl, 
      ArrayList<NameValuePair> nameValuePairs, Object object) {
    HttpResponse response = null;
    InputStream is = null;
    try {
      response = _httpClient.execute(
                    preProcess(SERVER+postUrl,nameValuePairs));
      HttpEntity entityResponse = response.getEntity();
      is = entityResponse.getContent();
      String result = postProcess(is);
      Object ro = new Gson().
                           fromJson(result, object.getClass());
      return ro;
    } catch (Exception e)  {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally  {
      try {is.close(); }catch(Exception e)  {}
    }
  }
  
  private HttpPost preProcess(String urlStr, 
                    ArrayList<NameValuePair> nameValuePairs)  {
    HttpPost httpPost = new HttpPost(urlStr);
    UrlEncodedFormEntity entityRequest;
    try {
      entityRequest = 
             new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    httpPost.setEntity(entityRequest);
    return httpPost;
  }
  
  private String postProcess(InputStream is)  {
    int bufferSize = 1024;
    byte[] buffer = new byte[bufferSize];
    int readByte = 0;
    int totalByte = 0;
    int count = 0;

    ByteArrayOutputStream byteArrayOutputStream = 
                     new ByteArrayOutputStream(bufferSize * 2);
    try {
      while((readByte = is.read(buffer)) != -1) {
        totalByte += readByte;
        count ++;
        byteArrayOutputStream.write(buffer, 0, readByte);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }        
    byte[] lDataBytes = byteArrayOutputStream.toByteArray();        
    return new String(lDataBytes);
  }
  





}
