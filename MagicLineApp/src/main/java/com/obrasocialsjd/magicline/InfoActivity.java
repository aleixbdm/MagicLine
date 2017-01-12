package com.obrasocialsjd.magicline;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarContextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private static Button montjuicbutton;
    private static Button estadibutton;
    private static Button espaibutton;
    private static Button festabutton;

    private TextView text1;
    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Buttontomontjuic();
        Buttontoestadi();
        Buttontoespai();
        Buttontofesta();

/*
        text1 = (TextView) findViewById(R.id.txt_help_gest);
        text2 = (TextView) findViewById(R.id.textView11);

// hide until its title is clicked
        text1.setVisibility(View.GONE);
        desplegable(text1); */
    }

/*
    public void desplegable(View v){

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent("com.obrasocialsjd.magicline.Montjuic");
                //startActivity(intent);

                if(text2.isShown()){
                    slide_up(this, text2);
                    txt_help_gest.setVisibility(View.GONE);
                }
                else{
                    txt_help_gest.setVisibility(View.VISIBLE);
                    slide_down(this, text2);
                }
    }
    /**
     * onClick handler
     */

    /*
    public void toggle_contents(View v){

    }

    public  void slide_down(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    */

    public void Buttontomontjuic(){
        montjuicbutton = (Button) findViewById(R.id.buttonmontjuic);
        montjuicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Montjuic");
                startActivity(intent);
            }
        });
    }
    public void Buttontoestadi(){
        estadibutton = (Button) findViewById(R.id.buttonestadi);
        estadibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Estadi");
                startActivity(intent);
            }
        });
    }
    public void Buttontoespai(){
        espaibutton = (Button) findViewById(R.id.buttonespai);
        espaibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Espai");
                startActivity(intent);
            }
        });
    }
    public void Buttontofesta(){
        festabutton = (Button) findViewById(R.id.buttonfesta);
        festabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Festa");
                startActivity(intent);
            }
        });
    }
    public void ButtonSantJoanDeu(){
        espaibutton = (Button) findViewById(R.id.buttonespai);
        espaibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Espai");
                startActivity(intent);
            }
        });
    }
}
