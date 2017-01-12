package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Youtube extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
        String url = "https://www.youtube.com/user/obrasocialsjd";
        WebView view = (WebView) this.findViewById(R.id.webViewyoutube);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        finish();
    }

}