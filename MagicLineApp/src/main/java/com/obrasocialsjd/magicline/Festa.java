package com.obrasocialsjd.magicline;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ScrollView;
import android.widget.TextView;

public class Festa extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festa);
        TextView text = (TextView) findViewById(R.id.textview_festa);
        //mScrollView = (ScrollView) findViewById(R.id.SCROLLER_ID);
        //text.setMovementMethod(new ScrollingMovementMethod());


    }

}
