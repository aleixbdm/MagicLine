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
import android.support.v4.app.FragmentStatePagerAdapter;
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
    private Button btnMLC;
    private Button btnGim;
    private Button btnSJD;
    private Button btnApp;
    private Button btnPic;
    private Button btnSOS;

    private static ImageButton facebookbutton;
    private static ImageButton twitterbutton;
    private static ImageButton youtubebutton;
    private static ImageButton emergenciabutton;
    private static ImageButton instagrambutton;
    private static ImageButton announcements;

    private static ViewPager viewPager;
    private static int page;
    private static CustomPagerAdapter customPagerAdapter;
    private static int num_images;
    private static RadioGroup radioGroup;
    private static Timer timer;
    private static TimerTask timerTask;
    private static final int tempsScroll = 5000;

    private static final int MY_PERMISSION_CALL_PHONE_REQUEST = 0x1;
    private static final int MY_PERMISSIONS_LOCATION_REQUEST = 0x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        num_images = 5;

        ButtonToMap();
        //ButtonToTrucadaEmergencia();
        ButtonToMLCultural();
        ButtonToGimcana();
        ButtonToPicnicCultural();
        ImageButtontofacebook();
        ImageButtontotwitter();
        //ImageButtontoyoutube();
        ImageButtontoinstagram();
        ImageButtontoTrucadaEmergencia();
        CreationImagesViewPager();
        IniciarViewPager();
        ButtonToSobreApp();
        ButtonToSantJoanDeu();

        ButtonAnnouncements();

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
        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        customPagerAdapter.setImages();
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

    public void ButtonToTrucadaEmergencia() {
        /* Versió 2017
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
        }); */
    }

    public void ButtonToPicnicCultural(){
        final Animation picnicAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        btnPic = (Button) findViewById(R.id.button4);
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(picnicAnimation);}
        });
        picnicAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, PicnicCultural.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

    }

    private void IniciarViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.removeAllViews();
        viewPager.setAdapter(customPagerAdapter);
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
                Log.e("onChecked-i",String.valueOf(i));
                Log.e("onChecked-page",String.valueOf(page));
                if (i > 0 && i <= num_images) {
                    viewPager.setCurrentItem(i - 1);
                    page = i - 1;
                    timer.cancel();
                    timer = new Timer();
                    final Handler handler = new Handler();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("Timer",String.valueOf(page));
                                    if (page >= num_images) page = 0;
                                    else {
                                        page++;
                                        if (page >= num_images) page = 0;
                                        viewPager.setCurrentItem(page);
                                    }
                                }
                            });
                        }
                    };
                    timer.scheduleAtFixedRate(timerTask,tempsScroll,tempsScroll);
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected",String.valueOf(page));
                if (position >= 0 && position < num_images) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                    radioButton.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onResume() {
        viewPager.removeAllViews();
        viewPager.setAdapter(customPagerAdapter);
        viewPager.setCurrentItem(page);
        final Handler handler = new Handler();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Timer",String.valueOf(page));
                        if (page >= num_images) page = 0;
                        else {
                            page++;
                            if (page >= num_images) page = 0;
                            viewPager.setCurrentItem(page);
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,tempsScroll,tempsScroll);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("onPause",String.valueOf(page));
        timer.cancel();
        timer = null;
        super.onPause();
    }

    public class CustomPagerAdapter extends FragmentStatePagerAdapter {


        private ImageFragment[] images;

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

        public void setImages() {
            images = new ImageFragment[num_images];
            for (int i = 0; i < num_images; i++) {
                String imageName = "noticia" + String.valueOf(i+1);
                int imageIdentifier = getResources().getIdentifier(imageName, "drawable", getPackageName());
                images[i] = ImageFragment.newInstance(imageIdentifier);
            }
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


    private void ImageButtontoyoutube() {
        /* Versió 2017
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
        });*/

    }

    private void ImageButtontoTrucadaEmergencia(){
        final Animation emergenciaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        emergenciabutton = (ImageButton) findViewById(R.id.youtube);
        emergenciabutton.setOnClickListener(new View.OnClickListener() {
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

    public void ImageButtontoinstagram() {
        final Animation instagramAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);
        instagrambutton = (ImageButton) findViewById(R.id.instagram);
        instagrambutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {view.startAnimation(instagramAnimation);}
        });
        instagramAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, Instagram.class);
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
