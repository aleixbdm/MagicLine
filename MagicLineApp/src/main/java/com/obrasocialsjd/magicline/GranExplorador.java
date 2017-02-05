package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Usuari on 05/02/2017.
 */

public class GranExplorador extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gran_explorador);
        String url = "https://www.facebook.com/GranExplorador";
        WebView view = (WebView) this.findViewById(R.id.webViewGranExplorador);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        finish();
    }
}
