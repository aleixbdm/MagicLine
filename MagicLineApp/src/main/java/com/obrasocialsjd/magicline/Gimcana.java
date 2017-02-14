package com.obrasocialsjd.magicline;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by Usuari on 05/02/2017.
 */

public class Gimcana extends AppCompatActivity {

    private Activity activity;
    private ImageButton returnButton;
    private Button premis_condicions;
    private ImageButton petit_explorador;
    private ImageButton gran_explorador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gimcana);

        activity = this;

        ButtonReturn();
        ButtonToPremisICondicions();
        ButtonToPetitExplorador();
        ButtonToGranExplorador();
    }

    private void ButtonReturn() {
        returnButton = (ImageButton) findViewById(R.id.returnButton);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });
    }

    private void ButtonToPremisICondicions() {
        final Animation premisCondAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        premis_condicions = (Button) findViewById(R.id.premis_condicions);
        premis_condicions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(premisCondAnimation);}
        });
        premisCondAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Gimcana.this, PremisICondicions.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void ButtonToPetitExplorador() {
        final Animation petitExpAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        petit_explorador = (ImageButton) findViewById(R.id.button_petit_exp);
        petit_explorador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(petitExpAnimation);}
        });
        petitExpAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Gimcana.this, PetitExplorador.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void ButtonToGranExplorador() {
        final Animation granExpAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        gran_explorador = (ImageButton) findViewById(R.id.button_gran_exp);
        gran_explorador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(granExpAnimation);}
        });
        granExpAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(Gimcana.this, GranExplorador.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
}