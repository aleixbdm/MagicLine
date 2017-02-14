package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Instagram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);
        String url = "https://www.instagram.com/magiclinesjd";
        WebView view = (WebView) this.findViewById(R.id.webViewinstagram);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        finish();
    }
}
