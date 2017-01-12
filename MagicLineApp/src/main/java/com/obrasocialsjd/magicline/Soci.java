package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class Soci extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soci);
        String url = "https://solidaritat.santjoandedeu.org";
        WebView view = (WebView) this.findViewById(R.id.websocis);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        finish();
    }
}
