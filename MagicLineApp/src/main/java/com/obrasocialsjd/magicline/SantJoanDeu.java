package com.obrasocialsjd.magicline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        button = (Button) findViewById(R.id.button_soci);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(animation);}
        });
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SantJoanDeu.this, Soci.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

    }

}
