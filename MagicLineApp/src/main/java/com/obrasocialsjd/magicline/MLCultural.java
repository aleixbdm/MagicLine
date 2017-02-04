package com.obrasocialsjd.magicline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

/**
 * Created by Usuari on 04/02/2017.
 */

public class MLCultural extends AppCompatActivity {

    private Button btnConcerts;
    private Button btnArts;
    private Button btnMuseus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlcultural);
        btnConcerts = (Button) findViewById(R.id.concert);
        btnArts = (Button) findViewById(R.id.arts);
        btnMuseus = (Button) findViewById(R.id.museus);
        ButtonToConcerts();
        ButtonToArts();
        ButtonToMuseus();
    }

    public void ButtonToConcerts() {
        final Animation concertsAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnConcerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(concertsAnimation);}
        });
        concertsAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MLCultural.this, Concert.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

    }

    public void ButtonToArts() {
        final Animation artsAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnArts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(artsAnimation);}
        });
        artsAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MLCultural.this, ArtsAlCarrer.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void ButtonToMuseus() {
        final Animation museusAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnMuseus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(museusAnimation);}
        });
        museusAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MLCultural.this, Museus.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}
