package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Usuari on 05/02/2017.
 */

public class PetitExplorador extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petit_explorador);
        String url = "https://www.facebook.com/PetitExplorador";
        WebView view = (WebView) this.findViewById(R.id.webViewPetitExplorador);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        finish();
    }
}
