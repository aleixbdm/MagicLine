package com.obrasocialsjd.magicline;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static Activity activity;
    private static final String TAG = "DemoButtonApp";
    private Button btnRuta;
    private Button btnInfo;

    private static Button button;
    private static ImageButton facebookbutton;
    private static ImageButton twitterbutton;
    private static ImageButton youtubebutton;

    private static ViewPager viewPager;
    private static int page;
    private static CustomPagerAdapter customPagerAdapter;
    private ImageFragment[] images;
    private static int num_images;
    private static RadioGroup radioGroup;

    private static int MY_PERMISSION_CALL_PHONE_REQUEST;
    private static int MY_PERMISSIONS_LOCATION_REQUEST;
    private static int MY_PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        btnRuta= (Button) this.findViewById(R.id.button1);
        //btnInfo=(Button)this.findViewById(R.id.buttonInfo);
        //ButtontoInfo();
        ButtonToMap();
        ButtonTrucadaEmergencia();
        num_images = 4;
        ImageButtontofacebook();
        ImageButtontotwitter();
        ImageButtontoyoutube();
        CreationImagesViewPager();
        IniciarViewPager();
        /*Buttontosobrelapp();
        ButtonSantJoanDeu();*/
        //ButtonCalculs();

        CheckPermissions();

        GetTokenId();
    }

    private void CheckPermissions(){
        int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]
                    {Manifest.permission.CALL_PHONE}, MY_PERMISSION_CALL_PHONE_REQUEST);
        }
        permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_LOCATION_REQUEST);
        }
        permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST);
        }
    }

    private void GetTokenId(){
        Log.i("MyFirebaseIIDService", "Getting token");
        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();

        // Log
        // Log.i("MyFirebaseIIDService", token);
    }

    private void CreationImagesViewPager() {
        images = new ImageFragment[num_images];
        int[] id_images = new int[4];
        id_images[0] = R.drawable.descobrir_montjuic;
        id_images[1] = R.drawable.espai_dels_somnis;
        id_images[2] = R.drawable.festa_magic_line;
        id_images[3] = R.drawable.estadi_olimpic;
        for (int i = 0; i < num_images; i++) {
            images[i] = ImageFragment.newInstance(id_images[i]);
            Log.d("INFORMACIO","CREATIONIMAGESVIEWPAGER" + String.valueOf(i));
        }
    }


    private void ButtonToMap() {
        btnRuta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    public void ButtonTrucadaEmergencia(){
        button = (Button) findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.CustomAlertDialog);

                builder.setMessage(R.string.pop_up_question);
                builder.setPositiveButton(R.string.pop_up_answer2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, Emergencia.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.pop_up_answer1, null);
                final AlertDialog alertDialog = builder.show();
                alertDialog.setTitle(R.string.pop_up_title);
                /*alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextSize(20);
                    }
                });*/
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

    private void IniciarViewPager() {
        final Handler handler = new Handler();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customPagerAdapter);
        viewPager.invalidate();
        page = 0;

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        RadioButton radioButton;
        for (int i = 0; i < num_images; i++) {
            radioButton = new RadioButton(this);
            radioGroup.addView(radioButton);
        }
        radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                viewPager.setCurrentItem(i-1);
                page = i-1;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(page);
                        page++;
                        if (page == num_images) page = 0;
                    }
                });
            }
        },5000,5000);
    }

    private void ImageButtontofacebook() {
        facebookbutton = (ImageButton) findViewById(R.id.facebook);
        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Facebook");
                startActivity(intent);
            }
        });
    }
    private void ImageButtontotwitter(){
        twitterbutton = (ImageButton) findViewById(R.id.twitter);
        twitterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Twitter");
                startActivity(intent);
            }
        });
    }


    private void ImageButtontoyoutube(){
        youtubebutton = (ImageButton) findViewById(R.id.youtube);
        youtubebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Youtube");
                startActivity(intent);
            }
        });
    }

    public class CustomPagerAdapter extends FragmentPagerAdapter {

        public CustomPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return images[position];
        }

        @Override
        public int getCount() {
            return num_images;
        }
    }

    /*public void Buttontosobrelapp(){
        button = (Button) findViewById(R.id.buttonsobrelapp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.obrasocialsjd.magicline.Sobrelapp");
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
    }*/

    private void ButtonCalculs() {
    }

}
