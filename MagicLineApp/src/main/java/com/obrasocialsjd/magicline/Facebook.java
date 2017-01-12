package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Facebook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        String url = "https://www.facebook.com/bml.santjoandedeu";
        WebView view = (WebView) this.findViewById(R.id.webViewfacebook);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        finish();
    }
}
