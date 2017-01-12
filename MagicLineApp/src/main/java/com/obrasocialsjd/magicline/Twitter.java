package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Twitter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        String url = "https://twitter.com/bmlsjd";
        WebView view = (WebView) this.findViewById(R.id.webViewtwitter);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        finish();
    }

}