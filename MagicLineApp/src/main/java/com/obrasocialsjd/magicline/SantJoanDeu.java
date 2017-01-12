package com.obrasocialsjd.magicline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SantJoanDeu extends AppCompatActivity {

    private static Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sant_joan_deu);
        TextView text = (TextView) findViewById(R.id.textview_ObraSocial);
        soci();
    }

    public void soci(){
        button = (Button) findViewById(R.id.button_soci);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Soci");
                startActivity(intent);
            }
        });
    }

}
