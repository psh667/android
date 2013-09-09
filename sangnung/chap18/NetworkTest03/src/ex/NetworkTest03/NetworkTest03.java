package ex.NetworkTest03;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import android.widget.Gallery.LayoutParams;

public class NetworkTest03 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);
       
        final EditText address = (EditText) findViewById(R.id.address);
        final WebView webview = (WebView) findViewById(R.id.web);
        //webview.loadUrl("http://www.yahoo.co.kr/");

        Button get = (Button) findViewById(R.id.get);
        get.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                webview.loadUrl(address.getText().toString());
            }

        });
    }
}
