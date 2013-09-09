package com.appstudio.android.sample.ch_33_2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.appstudio.android.sample.R;

public class TwitterTestLoginActivity extends Activity {
    Intent mIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_test_login_activity);
        WebView webView = (WebView) findViewById(R.id.webView1);

        webView.setWebViewClient(new WebViewClient()
        {
          public void onPageFinished(WebView view, String url)
          {
            super.onPageFinished(view, url);
            
            if (url != null && url.equals("http://mobile.twitter.com/"))
            {
              finish();
            }
            else if (url != null && url.startsWith(Constant.CALLBACK_URL))
            {
              String[] urlParameters = url.split("\\?")[1].split("&");
              String oauthToken = "";
              String oauthVerifier = "";

              try
              {
                if (urlParameters[0].startsWith(Constant.OAUTH_TOKEN))
                {
                  oauthToken = urlParameters[0].split("=")[1];
                }
                else if (urlParameters[1].startsWith(Constant.OAUTH_TOKEN))
                {
                  oauthToken = urlParameters[1].split("=")[1];
                }

                if (urlParameters[0].startsWith(Constant.OAUTH_VERIFIER))
                {
                  oauthVerifier = urlParameters[0].split("=")[1];
                }
                else if (urlParameters[1].startsWith(Constant.OAUTH_VERIFIER))
                {
                  oauthVerifier = urlParameters[1].split("=")[1];
                }

                mIntent.putExtra(Constant.OAUTH_TOKEN, oauthToken);
                mIntent.putExtra(Constant.OAUTH_VERIFIER, oauthVerifier);
                
                setResult(RESULT_OK, mIntent);
                finish();
              }
              catch(Exception e)
              {
                e.printStackTrace();
              }
            }
          }
        });

        mIntent = getIntent();
        webView.loadUrl(mIntent.getStringExtra(Constant.AUTH_URL));
      }
    }