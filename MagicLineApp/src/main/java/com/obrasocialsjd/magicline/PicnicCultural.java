package com.obrasocialsjd.magicline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Usuari on 04/02/2017.
 */

public class PicnicCultural extends AppCompatActivity {

    private Activity activity;
    private ImageButton returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picnic_cultural);

        activity = this;

        ButtonReturn();
    }

    private void ButtonReturn() {
        final Animation backAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        returnButton = (ImageButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(backAnimation);}
        });
        backAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                activity.finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

}
