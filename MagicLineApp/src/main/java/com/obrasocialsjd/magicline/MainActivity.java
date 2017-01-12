package com.obrasocialsjd.magicline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DemoButtonApp";
    private Button btnRuta;
    private Button btnInfo;
    private static Button button;
    private static ImageButton facebookbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRuta= (Button) this.findViewById(R.id.buttonRuta);
        btnInfo=(Button)this.findViewById(R.id.buttonInfo);
        ButtontoInfo();
        ButtontoMap();
        ImageButtontofacebook();
        ImageButtontotwitter();
        ImageButtontoyoutube();
        Buttontosobrelapp();
        Buttontrucadaemergencia();
        ButtonSantJoanDeu();
        ButtonCalculs();
    }



    private void ButtontoMap() {

        btnRuta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });


    }

    private void ButtontoInfo() {

        btnInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Què és La Magic Line?", Toast.LENGTH_LONG).show();
                //Toast.makeText().show() pop ups a message on mobile screen
                Intent intent =new Intent(MainActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });

    }

    public void ImageButtontofacebook(){
        facebookbutton = (ImageButton) findViewById(R.id.facebook);
        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Facebook");
                startActivity(intent);
            }
        });
    }
    public void ImageButtontotwitter(){
        facebookbutton = (ImageButton) findViewById(R.id.twitter);
        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Twitter");
                startActivity(intent);
            }
        });
    }


    public void ImageButtontoyoutube(){
        facebookbutton = (ImageButton) findViewById(R.id.youtube);
        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Youtube");
                startActivity(intent);
            }
        });
    }

    public void Buttontosobrelapp(){
        button = (Button) findViewById(R.id.buttonsobrelapp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Sobrelapp");
                startActivity(intent);
            }
        });
    }

    public void Buttontrucadaemergencia(){
        button = (Button) findViewById(R.id.buttonemergencia);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Emergencia");
                startActivity(intent);
            }
        });
    }
    public void ButtonSantJoanDeu(){
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.SantJoanDeu");
                startActivity(intent);
            }
        });
    }

    private void ButtonCalculs() {
    }

}
