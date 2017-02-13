package com.obrasocialsjd.magicline;

import android.Manifest;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.obrasocialsjd.magicline.Announcements.AnnouncementActivity;
import com.obrasocialsjd.magicline.Announcements.DatabaseAnnouncements;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static Activity activity;
    private static final String TAG = "DemoButtonApp";
    private Button btnRuta;
    private Button btnSOS;
    private Button btnMLC;
    private Button btnGim;
    private Button btnSJD;
    private Button btnApp;

    private static ImageButton facebookbutton;
    private static ImageButton twitterbutton;
    private static ImageButton youtubebutton;
    private static ImageButton announcements;

    private static ViewPager viewPager;
    private static int page;
    private static CustomPagerAdapter customPagerAdapter;
    private ImageFragment[] images;
    private static int num_images;
    private static RadioGroup radioGroup;
    private static final int tempsScroll = 5000;

    private static final int MY_PERMISSION_CALL_PHONE_REQUEST = 0x1;
    private static final int MY_PERMISSIONS_LOCATION_REQUEST = 0x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        num_images = 6;

        ButtonToMap();
        ButtonToTrucadaEmergencia();
        ButtonToMLCultural();
        ButtonToGimcana();
        ImageButtontofacebook();
        ImageButtontotwitter();
        ImageButtontoyoutube();
        CreationImagesViewPager();
        IniciarViewPager();
        ButtonToSobreApp();
        ButtonToSantJoanDeu();

        ButtonAnnouncements();

        //GetTokenId();

        SubscribeToAnnouncements();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_CALL_PHONE_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                }
                return;
            }

            case MY_PERMISSIONS_LOCATION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
                return;
            }
        }
    }

    private void GetTokenId(){
        Log.i("MyFirebaseIIDService", "Getting token");
        // Get token
        String token = FirebaseInstanceId.getInstance().getToken();

        // Log
        Log.i("MyFirebaseIIDService", token);
    }

    private void CreationImagesViewPager() {
        images = new ImageFragment[num_images];
        for (int i = 0; i < num_images; i++) {
            String imageName = "noticia" + String.valueOf(i+1);
            int imageIdentifier = getResources().getIdentifier(imageName, "drawable", getPackageName());
            images[i] = ImageFragment.newInstance(imageIdentifier);
        }
    }

    private void ButtonToMap() {
        final Animation mapAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnRuta= (Button) this.findViewById(R.id.button1);
        btnRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(mapAnimation);}
        });
        mapAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {

                //Check permission
                int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]
                                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            MY_PERMISSIONS_LOCATION_REQUEST);
                }
                else {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void ButtonToMLCultural(){
        final Animation culturalAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnMLC = (Button) findViewById(R.id.button2);
        btnMLC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(culturalAnimation);}
        });
        culturalAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, MLCultural.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void ButtonToGimcana(){
        final Animation gimcanaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnGim = (Button) findViewById(R.id.button3);
        btnGim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(gimcanaAnimation);}
        });
        gimcanaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, Gimcana.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void ButtonToTrucadaEmergencia(){
        final Animation emergenciaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnSOS = (Button) findViewById(R.id.button4);
        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(emergenciaAnimation);}
        });
        emergenciaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {

                //Check permission
                int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]
                            {Manifest.permission.CALL_PHONE}, MY_PERMISSION_CALL_PHONE_REQUEST);
                }
                else {
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
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
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
        },tempsScroll,tempsScroll);
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

    private void ImageButtontofacebook() {
        final Animation facebookAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        facebookbutton = (ImageButton) findViewById(R.id.facebook);
        facebookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(facebookAnimation);}
        });
        facebookAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, Facebook.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
    private void ImageButtontotwitter(){
        final Animation twitterAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        twitterbutton = (ImageButton) findViewById(R.id.twitter);
        twitterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(twitterAnimation);}
        });
        twitterAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, Twitter.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }


    private void ImageButtontoyoutube(){
        final Animation youtubeAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        youtubebutton = (ImageButton) findViewById(R.id.youtube);
        youtubebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(youtubeAnimation);}
        });
        youtubeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, Youtube.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void ButtonToSantJoanDeu(){
        final Animation SJDAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnSJD = (Button) findViewById(R.id.button5);
        btnSJD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(SJDAnimation);}
        });
        SJDAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, SantJoanDeu.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }
    public void ButtonToSobreApp(){
        final Animation sobreAppAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnApp = (Button) findViewById(R.id.button6);
        btnApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(sobreAppAnimation);}
        });
        sobreAppAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, Sobrelapp.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void ButtonAnnouncements(){
        final Animation announcementsAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        announcements = (ImageButton) findViewById(R.id.announcements);
        announcements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(announcementsAnimation);}
        });
        announcementsAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                DatabaseAnnouncements databaseAnnouncements = new DatabaseAnnouncements(getApplicationContext());
                if (databaseAnnouncements.getCountAnnouncements() == 0) {
                    Toast.makeText(getApplicationContext(),R.string.no_notificacions, Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, AnnouncementActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void SubscribeToAnnouncements(){
        FirebaseMessaging.getInstance().subscribeToTopic("announcements");
        //Log.i("FirebaseTopics","Subscribed");
    }

}
