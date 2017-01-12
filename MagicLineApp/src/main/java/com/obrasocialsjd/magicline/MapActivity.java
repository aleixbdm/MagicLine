package com.obrasocialsjd.magicline;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;

import android.net.Uri;
import android.preference.EditTextPreference;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapActivity extends FragmentActivity implements OnMapReadyCallback
        , ConnectionCallbacks, OnConnectionFailedListener {

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final int MY_PERMISSIONS_LOCATION =101;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    private GoogleMap mMap;
    private Location lastLocation;
    private Location mCurrentLocation;
    private EditTextPreference mLatitudeText = null;
    private EditTextPreference mLastUpdateTimeText = null;
    private EditTextPreference mLongitudeText = null;
    private LocationRequest mLocationRequest;
    private boolean mRequestingLocationUpdates;
    private String mLastUpdateTime;

    private GoogleApiClient client;
    private boolean mapready;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mapready=false;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mResolvingError = savedInstanceState != null
                && savedInstanceState.getBoolean(STATE_RESOLVING_ERROR, false);


        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API).build();

        //updateValuesFromBundle(savedInstanceState);
        mRequestingLocationUpdates=false;
        onConnected(savedInstanceState);

    }

    @Override
    public void onStart() {
        client.connect();
        super.onStart();
        if (!mResolvingError) {  // more about this later
            client.connect();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.obrasocialsjd.magicline/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        client.disconnect();
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.obrasocialsjd.magicline/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
    }


    //rutes mapa
    public void setLines(GoogleMap googleMap) {

        Polyline adaptline = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(41.369405, 2.170250), new LatLng(41.369804, 2.168759), new LatLng(41.369925, 2.168727), new LatLng(41.369895, 2.169132), new LatLng(41.369997, 2.169510), new LatLng(41.370126, 2.170122), new LatLng(41.370219, 2.170644), new LatLng(41.370394, 2.170787), new LatLng(41.370947, 2.170572), new LatLng(41.370690, 2.169939), new LatLng(41.370537, 2.168813), new LatLng(41.370456, 2.168180), new LatLng(41.370617, 2.167616), new LatLng(41.370766, 2.167604), new LatLng(41.370839, 2.167756), new LatLng(41.370941, 2.168245), new LatLng(41.371028, 2.168852), new LatLng(41.371422, 2.169317), new LatLng(41.371741, 2.169446), new LatLng(41.372123, 2.170594), new LatLng(41.372195, 2.171044), new LatLng(41.372407, 2.171965) )
                .width(7)
                .color(Color.BLUE));

        Polyline tram = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng( 41.412327 , 2.166127), new LatLng( 41.412464 , 2.166601), new LatLng( 41.412623 , 2.166684), new LatLng( 41.412703 , 2.166676), new LatLng( 41.412757 , 2.166806), new LatLng( 41.412999 , 2.166796), new LatLng( 41.413252 , 2.166688), new LatLng( 41.413500 , 2.166473), new LatLng( 41.413718 , 2.166155), new LatLng( 41.414408 , 2.166501), new LatLng( 41.414691 , 2.166652), new LatLng( 41.414814 , 2.166602), new LatLng( 41.414978 , 2.166672), new LatLng( 41.415160 , 2.166796), new LatLng( 41.415396 , 2.167201), new LatLng( 41.415536 , 2.167190), new LatLng( 41.416601 , 2.166198), new LatLng( 41.416852 , 2.165911), new LatLng( 41.417085 , 2.165562), new LatLng( 41.417259 , 2.165111), new LatLng( 41.417347 , 2.165004), new LatLng( 41.417405 , 2.165207), new LatLng( 41.417518 , 2.165510), new LatLng( 41.417633 , 2.165752), new LatLng( 41.417663 , 2.165907), new LatLng( 41.417674 , 2.166032), new LatLng( 41.417630 , 2.166293), new LatLng( 41.417570 , 2.166581), new LatLng( 41.417600 , 2.166648), new LatLng( 41.417688 , 2.166738), new LatLng( 41.417976 , 2.166889), new LatLng( 41.417911 , 2.166762), new LatLng( 41.417818 , 2.166531), new LatLng( 41.417889 , 2.166343), new LatLng( 41.417993 , 2.166163), new LatLng( 41.418121 , 2.165988), new LatLng( 41.418182 , 2.165979), new LatLng( 41.418215 , 2.166062), new LatLng( 41.418227 , 2.166223), new LatLng( 41.418242 , 2.166363), new LatLng( 41.418255 , 2.166380), new LatLng( 41.418334 , 2.166362), new LatLng( 41.418383 , 2.166220), new LatLng( 41.418411 , 2.166089), new LatLng( 41.418419 , 2.165912), new LatLng( 41.418401 , 2.165724), new LatLng( 41.418358 , 2.165567), new LatLng( 41.418422 , 2.165295), new LatLng( 41.418527 , 2.165124), new LatLng( 41.418748 , 2.164885), new LatLng( 41.418846 , 2.164985), new LatLng( 41.418990 , 2.165035), new LatLng( 41.419056 , 2.165051), new LatLng( 41.419149 , 2.165179))
                .width(7)
                .color(Color.RED));


        Polyline redline = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(41.460047, 2.167861), new LatLng(41.460009, 2.167992), new LatLng(41.460068, 2.168333), new LatLng(41.459934, 2.168443), new LatLng(41.459949, 2.168573), new LatLng(41.459875, 2.168595), new LatLng(41.459821, 2.168109), new LatLng(41.459799, 2.167960), new LatLng(41.459758, 2.167899), new LatLng(41.459698, 2.167908), new LatLng(41.459668, 2.167950), new LatLng(41.459663, 2.168040), new LatLng(41.459670, 2.168278), new LatLng(41.459637, 2.168448), new LatLng(41.459573, 2.168601), new LatLng(41.459505, 2.168756), new LatLng(41.459455, 2.168751), new LatLng(41.459368, 2.168679), new LatLng(41.459247, 2.168704), new LatLng(41.459132, 2.168811), new LatLng(41.459074, 2.168978), new LatLng(41.459090, 2.169317), new LatLng(41.459087, 2.169741), new LatLng(41.459069, 2.170031), new LatLng(41.459068, 2.170131), new LatLng(41.459155, 2.170606), new LatLng(41.459139, 2.170689), new LatLng(41.459097, 2.170731), new LatLng(41.459070, 2.170787), new LatLng(41.459069, 2.170853), new LatLng(41.459068, 2.170895), new LatLng(41.459024, 2.170839), new LatLng(41.458965, 2.170798), new LatLng(41.458908, 2.170790), new LatLng(41.458849, 2.170756), new LatLng(41.458784, 2.170686), new LatLng(41.458673, 2.170535), new LatLng(41.458615, 2.170430), new LatLng(41.458582, 2.170384), new LatLng(41.458573, 2.170323), new LatLng(41.458529, 2.170300), new LatLng(41.458478, 2.170248), new LatLng(41.458416, 2.170228), new LatLng(41.458341, 2.170189), new LatLng(41.458283, 2.170159), new LatLng(41.458215, 2.170134), new LatLng(41.458148, 2.170099), new LatLng(41.457923, 2.170114), new LatLng(41.457746, 2.170022), new LatLng(41.457468, 2.169782), new LatLng(41.457361, 2.169813), new LatLng(41.457348, 2.169939), new LatLng(41.457631, 2.170450), new LatLng(41.457811, 2.170744), new LatLng(41.457818, 2.170895), new LatLng(41.457686, 2.171150), new LatLng(41.457625, 2.171315), new LatLng(41.457512, 2.171797), new LatLng(41.457451, 2.172168), new LatLng(41.457310, 2.172305), new LatLng(41.457057, 2.172533), new LatLng(41.456993, 2.172642), new LatLng(41.457053, 2.172734), new LatLng(41.457434, 2.172809), new LatLng(41.457676, 2.172950), new LatLng(41.457774, 2.173071), new LatLng(41.457762, 2.173226), new LatLng(41.457725, 2.173448), new LatLng(41.457847, 2.173597), new LatLng(41.457988, 2.173740), new LatLng(41.458007, 2.173772), new LatLng(41.457998, 2.173871), new LatLng(41.458016, 2.173925), new LatLng(41.458069, 2.174114), new LatLng(41.458063, 2.174285), new LatLng(41.458077, 2.174644), new LatLng(41.458144, 2.174873), new LatLng(41.458239, 2.175206), new LatLng(41.458212, 2.175274), new LatLng(41.458113, 2.175282), new LatLng(41.457997, 2.175287), new LatLng(41.457998, 2.175285), new LatLng(41.457920, 2.175294), new LatLng(41.457807, 2.175296), new LatLng(41.457828, 2.175196), new LatLng(41.457781, 2.175018), new LatLng(41.457667, 2.174872), new LatLng(41.457351, 2.174479), new LatLng(41.457178, 2.174293), new LatLng(41.456903, 2.174015), new LatLng(41.456320, 2.173796), new LatLng(41.455976, 2.173618), new LatLng(41.455843, 2.173517), new LatLng(41.455606, 2.173359), new LatLng(41.455490, 2.173292), new LatLng(41.455399, 2.173214), new LatLng(41.455236, 2.173108), new LatLng(41.455092, 2.173150), new LatLng(41.454947, 2.173128), new LatLng(41.454774, 2.173229), new LatLng(41.454601, 2.173359), new LatLng(41.454533, 2.173488), new LatLng(41.454386, 2.174322), new LatLng(41.454410, 2.174531), new LatLng(41.454627, 2.174912), new LatLng(41.454842, 2.175162), new LatLng(41.454923, 2.175245), new LatLng(41.454879, 2.175269), new LatLng(41.454497, 2.174977), new LatLng(41.454225, 2.174826), new LatLng(41.454040, 2.174606), new LatLng(41.453852, 2.174338), new LatLng(41.453474, 2.173945), new LatLng(41.453329, 2.173668), new LatLng(41.453249, 2.173337), new LatLng(41.453159, 2.173339), new LatLng(41.453091, 2.173458), new LatLng(41.452951, 2.173688), new LatLng(41.452706, 2.173808), new LatLng(41.452445, 2.173850), new LatLng(41.452488, 2.174209), new LatLng(41.452340, 2.174358), new LatLng(41.452070, 2.174467), new LatLng(41.452024, 2.174709), new LatLng(41.451996, 2.174809), new LatLng(41.451985, 2.174903), new LatLng(41.452167, 2.175015), new LatLng(41.452380, 2.175400), new LatLng(41.452469, 2.175698), new LatLng(41.452453, 2.176113), new LatLng(41.452300, 2.176331), new LatLng(41.452079, 2.176571), new LatLng(41.451996, 2.176677), new LatLng(41.451921, 2.176849), new LatLng(41.451781, 2.177439), new LatLng(41.451778, 2.177656), new LatLng(41.451805, 2.177841), new LatLng(41.451914, 2.178027), new LatLng(41.452034, 2.178163), new LatLng(41.452158, 2.178439), new LatLng(41.452191, 2.178800), new LatLng(41.452162, 2.178910), new LatLng(41.452133, 2.178650), new LatLng(41.452055, 2.178510), new LatLng(41.451956, 2.178395), new LatLng(41.451822, 2.178260), new LatLng(41.451715, 2.178130), new LatLng(41.451628, 2.178013), new LatLng(41.451577, 2.177907), new LatLng(41.451503, 2.177645), new LatLng(41.451474, 2.177462), new LatLng(41.451437, 2.177176), new LatLng(41.451435, 2.176886), new LatLng(41.451415, 2.176732), new LatLng(41.451472, 2.176472), new LatLng(41.451451, 2.176283), new LatLng(41.451366, 2.176108), new LatLng(41.451145, 2.175819), new LatLng(41.451033, 2.175663), new LatLng(41.450862, 2.175473), new LatLng(41.450806, 2.175293), new LatLng(41.450727, 2.175049), new LatLng(41.450683, 2.174853), new LatLng(41.450694, 2.174584), new LatLng(41.450671, 2.174268), new LatLng(41.450452, 2.173525), new LatLng(41.450426, 2.173251), new LatLng(41.450369, 2.172998), new LatLng(41.450387, 2.172710), new LatLng(41.450312, 2.172432), new LatLng(41.450468, 2.172217), new LatLng(41.450587, 2.172005), new LatLng(41.450860, 2.171353), new LatLng(41.450900, 2.171114), new LatLng(41.451007, 2.170701), new LatLng(41.451095, 2.170516), new LatLng(41.451230, 2.170390), new LatLng(41.451155, 2.169808), new LatLng(41.451234, 2.169462), new LatLng(41.451298, 2.168430), new LatLng(41.451365, 2.168059), new LatLng(41.451425, 2.168111), new LatLng(41.451543, 2.168073), new LatLng(41.451638, 2.167815), new LatLng(41.451769, 2.167429), new LatLng(41.451893, 2.167056), new LatLng(41.452013, 2.166754), new LatLng(41.452234, 2.166320), new LatLng(41.452359, 2.165987), new LatLng(41.452713, 2.165678), new LatLng(41.452963, 2.165435), new LatLng(41.453126, 2.165088), new LatLng(41.453165, 2.164866), new LatLng(41.453129, 2.164637), new LatLng(41.453095, 2.164355), new LatLng(41.453003, 2.164038), new LatLng(41.452939, 2.163897), new LatLng(41.452734, 2.164002), new LatLng(41.452400, 2.163883), new LatLng(41.452323, 2.163943), new LatLng(41.452107, 2.164297), new LatLng(41.451917, 2.164301), new LatLng(41.451831, 2.164265), new LatLng(41.451612, 2.163873), new LatLng(41.451488, 2.163686), new LatLng(41.451365, 2.163248), new LatLng(41.451330, 2.163148), new LatLng(41.451201, 2.163061), new LatLng(41.451037, 2.163033), new LatLng(41.450915, 2.162860), new LatLng(41.450795, 2.162676), new LatLng(41.450686, 2.162526), new LatLng(41.450570, 2.162090), new LatLng(41.450647, 2.161681), new LatLng(41.450727, 2.161314), new LatLng(41.450873, 2.160918), new LatLng(41.450975, 2.160674), new LatLng(41.450932, 2.160505), new LatLng(41.450799, 2.160274), new LatLng(41.450740, 2.160088), new LatLng(41.450848, 2.159556), new LatLng(41.450789, 2.159300), new LatLng(41.450811, 2.159151), new LatLng(41.450743, 2.158915), new LatLng(41.450615, 2.159135), new LatLng(41.450297, 2.159164), new LatLng(41.449987, 2.159082), new LatLng(41.449810, 2.159017), new LatLng(41.449770, 2.159034), new LatLng(41.449815, 2.159283), new LatLng(41.449694, 2.159774), new LatLng(41.449239, 2.159870), new LatLng(41.448608, 2.159870), new LatLng(41.448471, 2.159838), new LatLng(41.448467, 2.159656), new LatLng(41.448785, 2.159538), new LatLng(41.449277, 2.159448), new LatLng(41.449328, 2.159355), new LatLng(41.449196, 2.159205), new LatLng(41.448793, 2.158910), new LatLng(41.448293, 2.158491), new LatLng(41.447980, 2.158210), new LatLng(41.447662, 2.157880), new LatLng(41.447414, 2.157463), new LatLng(41.447377, 2.157290), new LatLng(41.447340, 2.157178), new LatLng(41.447269, 2.157102), new LatLng(41.447092, 2.157377), new LatLng(41.446903, 2.157569), new LatLng(41.446747, 2.157590), new LatLng(41.446578, 2.157569), new LatLng(41.446039, 2.157316), new LatLng(41.445896, 2.157212), new LatLng(41.445665, 2.156341), new LatLng(41.445584, 2.156018), new LatLng(41.445563, 2.155893), new LatLng(41.445467, 2.155872), new LatLng(41.445404, 2.155962), new LatLng(41.445423, 2.156188), new LatLng(41.445126, 2.156826), new LatLng(41.444993, 2.156996), new LatLng(41.444921, 2.157153), new LatLng(41.444803, 2.157576), new LatLng(41.444637, 2.157815), new LatLng(41.444361, 2.158041), new LatLng(41.444036, 2.158062), new LatLng(41.443893, 2.158319), new LatLng(41.443695, 2.158492), new LatLng(41.443449, 2.158540), new LatLng(41.443217, 2.158607), new LatLng(41.442838, 2.158813), new LatLng(41.442420, 2.159141), new LatLng(41.442262, 2.159398), new LatLng(41.442114, 2.159699), new LatLng(41.441125, 2.159430), new LatLng(41.440280, 2.161199), new LatLng(41.440397, 2.161466), new LatLng(41.440313, 2.161453), new LatLng(41.440029, 2.161450), new LatLng(41.439780, 2.161531), new LatLng(41.439619, 2.161694), new LatLng(41.439536, 2.161702), new LatLng(41.439496, 2.161933), new LatLng(41.439416, 2.162172), new LatLng(41.439347, 2.162349), new LatLng(41.439347, 2.162638), new LatLng(41.439341, 2.162826), new LatLng(41.439388, 2.163129), new LatLng(41.439164, 2.163215), new LatLng(41.438766, 2.163540), new LatLng(41.438489, 2.163845), new LatLng(41.438087, 2.164409), new LatLng(41.437948, 2.164505), new LatLng(41.437240, 2.164693), new LatLng(41.437162, 2.164696), new LatLng(41.436432, 2.164291), new LatLng(41.436402, 2.164419), new LatLng(41.436299, 2.164462), new LatLng(41.436192, 2.164425), new LatLng(41.435670, 2.166439), new LatLng(41.434115, 2.165801), new LatLng(41.434047, 2.166109), new LatLng(41.434017, 2.165967), new LatLng(41.433968, 2.165809), new LatLng(41.433951, 2.165609), new LatLng(41.433876, 2.165701), new LatLng(41.433820, 2.165414), new LatLng(41.433711, 2.165825), new LatLng(41.433339, 2.166741), new LatLng(41.433386, 2.166085), new LatLng(41.433174, 2.166726), new LatLng(41.433094, 2.166847), new LatLng(41.433054, 2.166804), new LatLng(41.433094, 2.166259), new LatLng(41.433146, 2.166155), new LatLng(41.433066, 2.166104), new LatLng(41.432913, 2.166125), new LatLng(41.432971, 2.165935), new LatLng(41.432897, 2.165755), new LatLng(41.432887, 2.165602), new LatLng(41.432800, 2.165589), new LatLng(41.432881, 2.165685), new LatLng(41.432882, 2.165777), new LatLng(41.432824, 2.165849), new LatLng(41.432667, 2.165819), new LatLng(41.432587, 2.165691), new LatLng(41.432607, 2.165449), new LatLng(41.432696, 2.165262), new LatLng(41.432813, 2.165040), new LatLng(41.432933, 2.164832), new LatLng(41.432715, 2.164634), new LatLng(41.432426, 2.164593), new LatLng(41.432285, 2.164784), new LatLng(41.431994, 2.165571), new LatLng(41.431984, 2.165369), new LatLng(41.431955, 2.165273), new LatLng(41.431799, 2.165229), new LatLng(41.431569, 2.165208), new LatLng(41.431258, 2.165096), new LatLng(41.431229, 2.165155), new LatLng(41.430763, 2.165030), new LatLng(41.430620, 2.165669), new LatLng(41.430454, 2.165596), new LatLng(41.430371, 2.166123), new LatLng(41.430363, 2.166477), new LatLng(41.430256, 2.166418), new LatLng(41.430015, 2.166914), new LatLng(41.428867, 2.164818), new LatLng(41.427765, 2.165975), new LatLng(41.427742, 2.166206), new LatLng(41.427620, 2.166182), new LatLng(41.427586, 2.166023), new LatLng(41.423829, 2.165388), new LatLng(41.423803, 2.165530), new LatLng(41.423746, 2.165530), new LatLng(41.423688, 2.165337), new LatLng(41.421934, 2.165074), new LatLng(41.421715, 2.164958), new LatLng(41.421641, 2.164814), new LatLng(41.421592, 2.164682), new LatLng(41.421630, 2.164489), new LatLng(41.421932, 2.164425), new LatLng(41.421956, 2.164291), new LatLng(41.421502, 2.164291), new LatLng(41.421299, 2.164339), new LatLng(41.421112, 2.164768), new LatLng(41.421176, 2.164881), new LatLng(41.421359, 2.165028), new LatLng(41.421574, 2.165594), new LatLng(41.421540, 2.165833), new LatLng(41.421262, 2.166522), new LatLng(41.421311, 2.166694), new LatLng(41.421735, 2.167024), new LatLng(41.422137, 2.167185), new LatLng(41.422230, 2.167381), new LatLng(41.422304, 2.167810), new LatLng(41.422228, 2.168067), new LatLng(41.422115, 2.168148), new LatLng(41.421940, 2.168115), new LatLng(41.421767, 2.168013), new LatLng(41.421495, 2.167947), new LatLng(41.421305, 2.168000), new LatLng(41.421063, 2.168073), new LatLng(41.420814, 2.168196), new LatLng(41.420540, 2.168443), new LatLng(41.420412, 2.168579), new LatLng(41.420235, 2.168759), new LatLng(41.420226, 2.168700), new LatLng(41.420121, 2.168148), new LatLng(41.419981, 2.167501), new LatLng(41.419895, 2.167072), new LatLng(41.419830, 2.166828), new LatLng(41.419822, 2.166753), new LatLng(41.419983, 2.166951), new LatLng(41.420395, 2.167386), new LatLng(41.420594, 2.167288), new LatLng(41.420440, 2.167145), new LatLng(41.420468, 2.166911), new LatLng(41.420446, 2.166643), new LatLng(41.420412, 2.166220), new LatLng(41.420327, 2.165511), new LatLng(41.420148, 2.165599), new LatLng(41.420195, 2.166013), new LatLng(41.420127, 2.166168), new LatLng(41.419963, 2.166206), new LatLng(41.419525, 2.166085), new LatLng(41.419287, 2.165905), new LatLng(41.419159, 2.165610), new LatLng(41.419181, 2.165211), new LatLng(41.419179, 2.165049), new LatLng(41.419189, 2.164516), new LatLng(41.419221, 2.164001), new LatLng(41.419511, 2.163674), new LatLng(41.419551, 2.162569), new LatLng(41.419527, 2.162402), new LatLng(41.419607, 2.162405), new LatLng(41.419633, 2.162373), new LatLng(41.419528, 2.162307), new LatLng(41.419398, 2.161976), new LatLng(41.419229, 2.161567), new LatLng(41.419227, 2.161496), new LatLng(41.419243, 2.161423), new LatLng(41.419231, 2.161354), new LatLng(41.419271, 2.161364), new LatLng(41.419271, 2.161287), new LatLng(41.419215, 2.161211), new LatLng(41.419138, 2.161136), new LatLng(41.419068, 2.161118), new LatLng(41.419010, 2.161230), new LatLng(41.418982, 2.161300), new LatLng(41.418930, 2.161313), new LatLng(41.418958, 2.161502), new LatLng(41.418945, 2.161623), new LatLng(41.418951, 2.161708), new LatLng(41.418874, 2.161620), new LatLng(41.418751, 2.161472), new LatLng(41.418617, 2.161362), new LatLng(41.418503, 2.161311), new LatLng(41.418353, 2.161237), new LatLng(41.418295, 2.161174), new LatLng(41.418249, 2.161048), new LatLng(41.418261, 2.160924), new LatLng(41.418304, 2.160722), new LatLng(41.418599, 2.160090), new LatLng(41.418575, 2.159763), new LatLng(41.418475, 2.159554), new LatLng(41.418123, 2.159173), new LatLng(41.417781, 2.158749), new LatLng(41.417755, 2.158540), new LatLng(41.418189, 2.157620), new LatLng(41.418257, 2.157497), new LatLng(41.418348, 2.157448), new LatLng(41.418328, 2.157314), new LatLng(41.418675, 2.157159), new LatLng(41.418686, 2.157145), new LatLng(41.418561, 2.157124), new LatLng(41.418565, 2.157051), new LatLng(41.418604, 2.156863), new LatLng(41.418605, 2.156675), new LatLng(41.418597, 2.156603), new LatLng(41.418623, 2.156506), new LatLng(41.418618, 2.156383), new LatLng(41.418669, 2.156442), new LatLng(41.418715, 2.156436), new LatLng(41.418764, 2.156357), new LatLng(41.418792, 2.156122), new LatLng(41.418817, 2.156045), new LatLng(41.418778, 2.155986), new LatLng(41.418762, 2.155796), new LatLng(41.418684, 2.155434), new LatLng(41.418667, 2.155250), new LatLng(41.418628, 2.155165), new LatLng(41.418590, 2.155006), new LatLng(41.418591, 2.154903), new LatLng(41.418585, 2.154729), new LatLng(41.418599, 2.154597), new LatLng(41.418557, 2.154278), new LatLng(41.418520, 2.154191), new LatLng(41.418489, 2.154018), new LatLng(41.418464, 2.153886), new LatLng(41.418460, 2.153684), new LatLng(41.418422, 2.153481), new LatLng(41.418437, 2.153443), new LatLng(41.418282, 2.153267), new LatLng(41.418146, 2.153251), new LatLng(41.417965, 2.153230), new LatLng(41.417818, 2.153170), new LatLng(41.417606, 2.153067), new LatLng(41.417602, 2.153108), new LatLng(41.417566, 2.153350), new LatLng(41.417453, 2.153317), new LatLng(41.417270, 2.153158), new LatLng(41.417018, 2.153179), new LatLng(41.416961, 2.153232), new LatLng(41.416834, 2.153179), new LatLng(41.416718, 2.153185), new LatLng(41.416605, 2.153078), new LatLng(41.416582, 2.152989), new LatLng(41.416664, 2.152886), new LatLng(41.416763, 2.152778), new LatLng(41.416865, 2.152733), new LatLng(41.417491, 2.151872), new LatLng(41.417614, 2.151604), new LatLng(41.417656, 2.151252), new LatLng(41.417771, 2.151012), new LatLng(41.417978, 2.150960), new LatLng(41.418569, 2.151276), new LatLng(41.419022, 2.151566), new LatLng(41.419354, 2.151663), new LatLng(41.419720, 2.151663), new LatLng(41.420114, 2.151733), new LatLng(41.420235, 2.151502), new LatLng(41.420291, 2.150579), new LatLng(41.420420, 2.149367), new LatLng(41.420745, 2.149538), new LatLng(41.421570, 2.149512), new LatLng(41.421647, 2.149388), new LatLng(41.421401, 2.149131), new LatLng(41.421144, 2.148723), new LatLng(41.420943, 2.148401), new LatLng(41.420798, 2.148026), new LatLng(41.420705, 2.147795), new LatLng(41.420597, 2.147715), new LatLng(41.420520, 2.147661), new LatLng(41.420380, 2.147641), new LatLng(41.420352, 2.147737), new LatLng(41.420257, 2.147763), new LatLng(41.420175, 2.147748), new LatLng(41.420096, 2.147647), new LatLng(41.420043, 2.147493), new LatLng(41.419986, 2.147364), new LatLng(41.419985, 2.147284), new LatLng(41.419907, 2.147053), new LatLng(41.419910, 2.146874), new LatLng(41.419999, 2.146632), new LatLng(41.420010, 2.146251), new LatLng(41.419789, 2.145804), new LatLng(41.419609, 2.145628), new LatLng(41.419281, 2.145446), new LatLng(41.419311, 2.145348), new LatLng(41.419469, 2.145432), new LatLng(41.419550, 2.145137), new LatLng(41.419289, 2.144963), new LatLng(41.418887, 2.144606), new LatLng(41.418914, 2.144525), new LatLng(41.419004, 2.144536), new LatLng(41.419104, 2.144624), new LatLng(41.419414, 2.144890), new LatLng(41.419589, 2.144960), new LatLng(41.419788, 2.144984), new LatLng(41.419901, 2.144963), new LatLng(41.420013, 2.144842), new LatLng(41.420068, 2.144715), new LatLng(41.420206, 2.143981), new LatLng(41.420226, 2.143876), new LatLng(41.420166, 2.143651), new LatLng(41.420130, 2.142838), new LatLng(41.419981, 2.142610), new LatLng(41.419794, 2.142135), new LatLng(41.419873, 2.141772), new LatLng(41.420113, 2.141507), new LatLng(41.421170, 2.140899), new LatLng(41.421198, 2.140727), new LatLng(41.421047, 2.140679), new LatLng(41.420758, 2.140486), new LatLng(41.420349, 2.140317), new LatLng(41.420305, 2.140261), new LatLng(41.420398, 2.139899), new LatLng(41.419174, 2.139298), new LatLng(41.419168, 2.139205), new LatLng(41.419052, 2.139158), new LatLng(41.419000, 2.139051), new LatLng(41.419072, 2.138734), new LatLng(41.419140, 2.138716), new LatLng(41.419581, 2.138855), new LatLng(41.420231, 2.139064), new LatLng(41.420442, 2.139075), new LatLng(41.420572, 2.138981), new LatLng(41.420677, 2.138761), new LatLng(41.420564, 2.138692), new LatLng(41.420512, 2.138767), new LatLng(41.419513, 2.137699), new LatLng(41.419559, 2.137619), new LatLng(41.419615, 2.137619), new LatLng(41.419891, 2.137777), new LatLng(41.420371, 2.137855), new LatLng(41.420486, 2.137780), new LatLng(41.420581, 2.137608), new LatLng(41.420665, 2.137184), new LatLng(41.420761, 2.137015), new LatLng(41.421480, 2.136455), new LatLng(41.421518, 2.136369), new LatLng(41.421455, 2.136315), new LatLng(41.421188, 2.136465), new LatLng(41.421021, 2.136546), new LatLng(41.420649, 2.136532), new LatLng(41.420341, 2.136723), new LatLng(41.420192, 2.136787), new LatLng(41.420072, 2.136739), new LatLng(41.419901, 2.136500), new LatLng(41.419903, 2.136382), new LatLng(41.420003, 2.136286), new LatLng(41.420392, 2.136022), new LatLng(41.420772, 2.135803), new LatLng(41.421309, 2.135406), new LatLng(41.421562, 2.135293), new LatLng(41.421811, 2.135223), new LatLng(41.422031, 2.135202), new LatLng(41.422093, 2.134872), new LatLng(41.422145, 2.134741), new LatLng(41.422284, 2.134327), new LatLng(41.422288, 2.134003), new LatLng(41.422359, 2.133719), new LatLng(41.422415, 2.133464), new LatLng(41.422511, 2.133263), new LatLng(41.422622, 2.132902), new LatLng(41.422574, 2.131855), new LatLng(41.422678, 2.131184), new LatLng(41.422777, 2.130586), new LatLng(41.422700, 2.130178), new LatLng(41.422544, 2.129856), new LatLng(41.422334, 2.129502), new LatLng(41.422169, 2.129336), new LatLng(41.421892, 2.129006), new LatLng(41.421773, 2.128695), new LatLng(41.421516, 2.127920), new LatLng(41.421219, 2.126789), new LatLng(41.421204, 2.126679), new LatLng(41.421337, 2.126587), new LatLng(41.421389, 2.126345), new LatLng(41.421431, 2.126055), new LatLng(41.421355, 2.125860), new LatLng(41.421195, 2.125542), new LatLng(41.421121, 2.125312), new LatLng(41.421248, 2.125011), new LatLng(41.421582, 2.124779), new LatLng(41.421725, 2.124564), new LatLng(41.421749, 2.124194), new LatLng(41.421715, 2.123885), new LatLng(41.421749, 2.123745), new LatLng(41.421935, 2.123646), new LatLng(41.422121, 2.123637), new LatLng(41.422247, 2.123497), new LatLng(41.422538, 2.123056), new LatLng(41.422721, 2.122660), new LatLng(41.422950, 2.122536), new LatLng(41.423298, 2.122582), new LatLng(41.423459, 2.122775), new LatLng(41.423522, 2.123043), new LatLng(41.423528, 2.123394), new LatLng(41.423662, 2.123575), new LatLng(41.423935, 2.123727), new LatLng(41.424586, 2.123427), new LatLng(41.425084, 2.123208), new LatLng(41.425245, 2.123167), new LatLng(41.425412, 2.123266), new LatLng(41.425582, 2.123365), new LatLng(41.426176, 2.123344), new LatLng(41.426667, 2.123824), new LatLng(41.426817, 2.123829), new LatLng(41.427061, 2.123486), new LatLng(41.427129, 2.123274), new LatLng(41.428237, 2.123325), new LatLng(41.428265, 2.123212), new LatLng(41.428330, 2.122936), new LatLng(41.428076, 2.122714), new LatLng(41.427977, 2.122395), new LatLng(41.427810, 2.122255), new LatLng(41.427495, 2.122122), new LatLng(41.427266, 2.121908), new LatLng(41.427167, 2.121586), new LatLng(41.426622, 2.121042), new LatLng(41.426585, 2.120786), new LatLng(41.426721, 2.120340), new LatLng(41.426777, 2.119969), new LatLng(41.426604, 2.119639), new LatLng(41.426561, 2.119474), new LatLng(41.426858, 2.119020), new LatLng(41.426690, 2.118343), new LatLng(41.426462, 2.117955), new LatLng(41.426195, 2.117815), new LatLng(41.426121, 2.118228), new LatLng(41.425991, 2.118451), new LatLng(41.425802, 2.118763), new LatLng(41.425645, 2.119267), new LatLng(41.425601, 2.119523), new LatLng(41.423984, 2.119213), new LatLng(41.423489, 2.119149), new LatLng(41.423074, 2.118950), new LatLng(41.422704, 2.118666), new LatLng(41.422351, 2.118339), new LatLng(41.422097, 2.118269), new LatLng(41.421847, 2.118486), new LatLng(41.421550, 2.119042), new LatLng(41.421355, 2.119165), new LatLng(41.421289, 2.119296), new LatLng(41.421208, 2.119237), new LatLng(41.421264, 2.119025), new LatLng(41.421196, 2.118956), new LatLng(41.421107, 2.119090), new LatLng(41.421053, 2.119278), new LatLng(41.420943, 2.119095), new LatLng(41.420935, 2.119353), new LatLng(41.420959, 2.119487), new LatLng(41.420872, 2.119473), new LatLng(41.420506, 2.119119), new LatLng(41.420094, 2.118983), new LatLng(41.419682, 2.118795), new LatLng(41.419581, 2.118387), new LatLng(41.419410, 2.117808), new LatLng(41.419265, 2.117414), new LatLng(41.418919, 2.116746), new LatLng(41.418851, 2.116386), new LatLng(41.418642, 2.115957), new LatLng(41.418593, 2.115625), new LatLng(41.418601, 2.115276), new LatLng(41.418581, 2.115195), new LatLng(41.418527, 2.115104), new LatLng(41.418320, 2.114873), new LatLng(41.418072, 2.114452), new LatLng(41.417899, 2.114348), new LatLng(41.417775, 2.114262), new LatLng(41.417761, 2.114045), new LatLng(41.417855, 2.113629), new LatLng(41.417868, 2.113508), new LatLng(41.417829, 2.113332), new LatLng(41.417801, 2.113186), new LatLng(41.417704, 2.113050), new LatLng(41.417668, 2.112877), new LatLng(41.417632, 2.112883), new LatLng(41.417575, 2.112999), new LatLng(41.417537, 2.112905), new LatLng(41.417486, 2.112826), new LatLng(41.417455, 2.112773), new LatLng(41.417392, 2.112611), new LatLng(41.417330, 2.112453), new LatLng(41.417245, 2.112341), new LatLng(41.417154, 2.112199), new LatLng(41.417055, 2.112022), new LatLng(41.416884, 2.111797), new LatLng(41.416754, 2.111574), new LatLng(41.416575, 2.111176), new LatLng(41.416415, 2.110924), new LatLng(41.416041, 2.110354), new LatLng(41.415979, 2.110190), new LatLng(41.415522, 2.109636), new LatLng(41.415121, 2.108907), new LatLng(41.414905, 2.108509), new LatLng(41.414927, 2.108270), new LatLng(41.414969, 2.106666), new LatLng(41.414872, 2.106390), new LatLng(41.414586, 2.105926), new LatLng(41.414397, 2.105687), new LatLng(41.414371, 2.105702), new LatLng(41.414325, 2.105652), new LatLng(41.414353, 2.105509), new LatLng(41.413966, 2.105294), new LatLng(41.413359, 2.104908), new LatLng(41.413196, 2.104849), new LatLng(41.413013, 2.104705), new LatLng(41.412934, 2.104619), new LatLng(41.413060, 2.104442), new LatLng(41.412908, 2.104222), new LatLng(41.411170, 2.103023), new LatLng(41.410711, 2.102893), new LatLng(41.409972, 2.102352), new LatLng(41.409244, 2.102088), new LatLng(41.408705, 2.101843), new LatLng(41.408189, 2.101720), new LatLng(41.407931, 2.101703), new LatLng(41.407849, 2.101698), new LatLng(41.407704, 2.101658), new LatLng(41.407226, 2.101480), new LatLng(41.406692, 2.101402), new LatLng(41.406436, 2.101866), new LatLng(41.406292, 2.102301), new LatLng(41.406219, 2.102399), new LatLng(41.406211, 2.102413), new LatLng(41.405961, 2.102589), new LatLng(41.405606, 2.102868), new LatLng(41.405357, 2.102948), new LatLng(41.405210, 2.103246), new LatLng(41.404731, 2.103635), new LatLng(41.404470, 2.103734), new LatLng(41.404020, 2.103573), new LatLng(41.403768, 2.103544), new LatLng(41.403599, 2.103635), new LatLng(41.403321, 2.103613), new LatLng(41.402979, 2.103683), new LatLng(41.402677, 2.103560), new LatLng(41.402327, 2.103345), new LatLng(41.402138, 2.103023), new LatLng(41.401812, 2.102873), new LatLng(41.401551, 2.102879), new LatLng(41.401181, 2.102965), new LatLng(41.400818, 2.102852), new LatLng(41.400480, 2.102669), new LatLng(41.399969, 2.102240), new LatLng(41.399386, 2.101758), new LatLng(41.398871, 2.101119), new LatLng(41.398762, 2.100846), new LatLng(41.398674, 2.100422), new LatLng(41.398541, 2.100288), new LatLng(41.398267, 2.100459), new LatLng(41.398070, 2.100481), new LatLng(41.397897, 2.100320), new LatLng(41.397527, 2.100245), new LatLng(41.397124, 2.100025), new LatLng(41.396601, 2.099578), new LatLng(41.396118, 2.099220), new LatLng(41.395748, 2.099016), new LatLng(41.395342, 2.098893), new LatLng(41.394959, 2.098469), new LatLng(41.394750, 2.098415), new LatLng(41.394215, 2.098040), new LatLng(41.393947, 2.097817), new LatLng(41.393598, 2.098153), new LatLng(41.393187, 2.098450), new LatLng(41.393072, 2.098523), new LatLng(41.393016, 2.098431), new LatLng(41.392851, 2.097997), new LatLng(41.392746, 2.097756), new LatLng(41.392541, 2.097487), new LatLng(41.392348, 2.097284), new LatLng(41.392295, 2.097096), new LatLng(41.392273, 2.096790), new LatLng(41.392323, 2.096656), new LatLng(41.392368, 2.096613), new LatLng(41.392404, 2.096559), new LatLng(41.392350, 2.096460), new LatLng(41.392293, 2.096498), new LatLng(41.392219, 2.096624), new LatLng(41.392098, 2.096946), new LatLng(41.392076, 2.097174), new LatLng(41.392187, 2.097552), new LatLng(41.392345, 2.097976), new LatLng(41.392412, 2.098281), new LatLng(41.392368, 2.098646), new LatLng(41.392283, 2.099081), new LatLng(41.392213, 2.099268), new LatLng(41.392022, 2.099461), new LatLng(41.391844, 2.099547), new LatLng(41.391535, 2.099692), new LatLng(41.391424, 2.099743), new LatLng(41.391368, 2.099840), new LatLng(41.391281, 2.099874), new LatLng(41.391241, 2.099794), new LatLng(41.391277, 2.099714), new LatLng(41.391383, 2.099602), new LatLng(41.391523, 2.099504), new LatLng(41.391704, 2.099354), new LatLng(41.391836, 2.099274), new LatLng(41.391881, 2.099220), new LatLng(41.391836, 2.099097), new LatLng(41.391704, 2.099161), new LatLng(41.391458, 2.099306), new LatLng(41.391296, 2.099346), new LatLng(41.391090, 2.099369), new LatLng(41.390919, 2.099360), new LatLng(41.390701, 2.099357), new LatLng(41.390521, 2.099349), new LatLng(41.390355, 2.099322), new LatLng(41.390758, 2.099236), new LatLng(41.391128, 2.099188), new LatLng(41.391434, 2.099059), new LatLng(41.391571, 2.098963), new LatLng(41.391646, 2.098815), new LatLng(41.391663, 2.098603), new LatLng(41.391587, 2.098378), new LatLng(41.391502, 2.098142), new LatLng(41.391442, 2.097879), new LatLng(41.391418, 2.097702), new LatLng(41.391386, 2.097579), new LatLng(41.391247, 2.097241), new LatLng(41.391193, 2.096903), new LatLng(41.391138, 2.096622), new LatLng(41.390967, 2.096398), new LatLng(41.390746, 2.096060), new LatLng(41.390637, 2.095883), new LatLng(41.390283, 2.095678), new LatLng(41.390009, 2.095594), new LatLng(41.389772, 2.095562), new LatLng(41.389524, 2.095553), new LatLng(41.389160, 2.095546), new LatLng(41.388975, 2.095497), new LatLng(41.388806, 2.095406), new LatLng(41.388629, 2.095368), new LatLng(41.388565, 2.095444), new LatLng(41.388565, 2.095615), new LatLng(41.388675, 2.095768), new LatLng(41.389035, 2.095980), new LatLng(41.389281, 2.096098), new LatLng(41.389611, 2.096325), new LatLng(41.389752, 2.096511), new LatLng(41.389953, 2.096854), new LatLng(41.389993, 2.096991), new LatLng(41.389953, 2.097072), new LatLng(41.389840, 2.096999), new LatLng(41.389655, 2.096791), new LatLng(41.389454, 2.096698), new LatLng(41.389235, 2.096593), new LatLng(41.388885, 2.096692), new LatLng(41.388618, 2.096762), new LatLng(41.388176, 2.096978), new LatLng(41.387914, 2.097106), new LatLng(41.387591, 2.097302), new LatLng(41.387417, 2.097466), new LatLng(41.387285, 2.097857), new LatLng(41.387126, 2.098296), new LatLng(41.387027, 2.098711), new LatLng(41.387021, 2.098847), new LatLng(41.386971, 2.098866), new LatLng(41.387021, 2.099917), new LatLng(41.385852, 2.100014), new LatLng(41.385719, 2.099966), new LatLng(41.384930, 2.100293), new LatLng(41.384930, 2.100389), new LatLng(41.385389, 2.101562), new LatLng(41.385057, 2.101983), new LatLng(41.385173, 2.102299), new LatLng(41.385126, 2.102433), new LatLng(41.384926, 2.102621), new LatLng(41.384492, 2.103104), new LatLng(41.384545, 2.103194), new LatLng(41.384556, 2.103249), new LatLng(41.384572, 2.103351), new LatLng(41.384620, 2.103447), new LatLng(41.384572, 2.103533), new LatLng(41.385051, 2.104059), new LatLng(41.385061, 2.104212), new LatLng(41.384822, 2.104566), new LatLng(41.384464, 2.104579), new LatLng(41.384244, 2.104693), new LatLng(41.383992, 2.105031), new LatLng(41.383844, 2.105484), new LatLng(41.383590, 2.106030), new LatLng(41.383507, 2.106379), new LatLng(41.383522, 2.106712), new LatLng(41.383455, 2.106778), new LatLng(41.383572, 2.107196), new LatLng(41.383694, 2.107748), new LatLng(41.383819, 2.108161), new LatLng(41.383992, 2.108878), new LatLng(41.383881, 2.108856), new LatLng(41.383911, 2.109053), new LatLng(41.383977, 2.109064), new LatLng(41.384017, 2.109139), new LatLng(41.384391, 2.110684), new LatLng(41.384318, 2.110721), new LatLng(41.384415, 2.111161), new LatLng(41.384669, 2.111832), new LatLng(41.385007, 2.113103), new LatLng(41.385043, 2.113602), new LatLng(41.385123, 2.113720), new LatLng(41.385381, 2.114659), new LatLng(41.385413, 2.115104), new LatLng(41.385647, 2.115507), new LatLng(41.385667, 2.115936), new LatLng(41.385763, 2.116424), new LatLng(41.385916, 2.117159), new LatLng(41.387148, 2.121766), new LatLng(41.386677, 2.121987), new LatLng(41.386737, 2.122276), new LatLng(41.386525, 2.122478), new LatLng(41.384839, 2.122261), new LatLng(41.384806, 2.122688), new LatLng(41.384728, 2.122860), new LatLng(41.384693, 2.123076), new LatLng(41.384623, 2.123226), new LatLng(41.384619, 2.123487), new LatLng(41.384556, 2.123673), new LatLng(41.384374, 2.123842), new LatLng(41.383022, 2.124739), new LatLng(41.383051, 2.125002), new LatLng(41.382913, 2.125247), new LatLng(41.382760, 2.125230), new LatLng(41.382659, 2.125235), new LatLng(41.382432, 2.125463), new LatLng(41.382230, 2.125410), new LatLng(41.381272, 2.126192), new LatLng(41.382274, 2.127769), new LatLng(41.382089, 2.127915), new LatLng(41.382217, 2.128140), new LatLng(41.379535, 2.129055), new LatLng(41.379657, 2.129400), new LatLng(41.380725, 2.131671), new LatLng(41.380914, 2.132360), new LatLng(41.381425, 2.134174), new LatLng(41.381852, 2.135391), new LatLng(41.382209, 2.136449), new LatLng(41.383155, 2.138695), new LatLng(41.383025, 2.138827), new LatLng(41.383023, 2.139216), new LatLng(41.380230, 2.142668), new LatLng(41.380126, 2.142632), new LatLng(41.380008, 2.142802), new LatLng(41.379493, 2.142180), new LatLng(41.379058, 2.142170), new LatLng(41.378873, 2.141889), new LatLng(41.377014, 2.141898), new LatLng(41.376920, 2.140821), new LatLng(41.376854, 2.140841), new LatLng(41.376899, 2.141415), new LatLng(41.376627, 2.141721), new LatLng(41.376543, 2.141887), new LatLng(41.376357, 2.142063), new LatLng(41.375349, 2.142085), new LatLng(41.375125, 2.148380), new LatLng(41.374968, 2.148313), new LatLng(41.374749, 2.148336), new LatLng(41.374750, 2.148331), new LatLng(41.374460, 2.148791), new LatLng(41.374364, 2.149420), new LatLng(41.371361, 2.151153), new LatLng(41.370862, 2.149710), new LatLng(41.370759, 2.149597), new LatLng(41.370063, 2.149482), new LatLng(41.369592, 2.149136), new LatLng(41.369465, 2.148766), new LatLng(41.369376, 2.148342), new LatLng(41.369435, 2.147366), new LatLng(41.369348, 2.146757), new LatLng(41.369044, 2.145947), new LatLng(41.368104, 2.146620), new LatLng(41.367750, 2.146159), new LatLng(41.367110, 2.146249), new LatLng(41.368405, 2.150027), new LatLng(41.368064, 2.150913), new LatLng(41.369341, 2.154364), new LatLng(41.369149, 2.154480), new LatLng(41.369360, 2.154999), new LatLng(41.368966, 2.155750), new LatLng(41.368161, 2.156652), new LatLng(41.367476, 2.157145), new LatLng(41.367359, 2.157488), new LatLng(41.367307, 2.157966), new LatLng(41.367464, 2.158502), new LatLng(41.367581, 2.159157), new LatLng(41.368197, 2.160122), new LatLng(41.368056, 2.160289), new LatLng(41.367379, 2.159232), new LatLng(41.366378, 2.157051), new LatLng(41.366175, 2.156240), new LatLng(41.366056, 2.155318), new LatLng(41.366037, 2.153954), new LatLng(41.365174, 2.153894), new LatLng(41.365166, 2.153526), new LatLng(41.364168, 2.153381), new LatLng(41.364128, 2.153737), new LatLng(41.363782, 2.154020), new LatLng(41.363520, 2.154402), new LatLng(41.363554, 2.154580), new LatLng(41.364191, 2.155139), new LatLng(41.365323, 2.155150), new LatLng(41.365463, 2.155357), new LatLng(41.365552, 2.155674), new LatLng(41.365438, 2.155977), new LatLng(41.365312, 2.156145), new LatLng(41.364227, 2.156130), new LatLng(41.364024, 2.155891), new LatLng(41.363980, 2.155634), new LatLng(41.363999, 2.155450), new LatLng(41.364112, 2.155217), new LatLng(41.363361, 2.154554), new LatLng(41.363124, 2.154442), new LatLng(41.361962, 2.154442), new LatLng(41.362417, 2.155726), new LatLng(41.362657, 2.156402), new LatLng(41.362846, 2.157175), new LatLng(41.362508, 2.157183), new LatLng(41.362210, 2.157338), new LatLng(41.362069, 2.157719), new LatLng(41.362262, 2.158071), new LatLng(41.362369, 2.158162), new LatLng(41.362333, 2.158309), new LatLng(41.362689, 2.158709), new LatLng(41.362564, 2.158859), new LatLng(41.362387, 2.158840), new LatLng(41.362256, 2.158650), new LatLng(41.361858, 2.158577), new LatLng(41.362011, 2.158851), new LatLng(41.362047, 2.159047), new LatLng(41.362278, 2.159498), new LatLng(41.362125, 2.159420), new LatLng(41.361914, 2.159398), new LatLng(41.361597, 2.158950), new LatLng(41.361525, 2.158628), new LatLng(41.361337, 2.158229), new LatLng(41.361465, 2.158921), new LatLng(41.361658, 2.159739), new LatLng(41.362091, 2.160265), new LatLng(41.362125, 2.160391), new LatLng(41.362232, 2.160493), new LatLng(41.362305, 2.160812), new LatLng(41.362460, 2.161091), new LatLng(41.362621, 2.161472), new LatLng(41.362391, 2.161522), new LatLng(41.361719, 2.160772), new LatLng(41.360511, 2.159999), new LatLng(41.360312, 2.160066), new LatLng(41.359315, 2.160745), new LatLng(41.359367, 2.160932), new LatLng(41.360016, 2.161945), new LatLng(41.360073, 2.162034), new LatLng(41.360068, 2.162048), new LatLng(41.360292, 2.162069), new LatLng(41.360600, 2.162097), new LatLng(41.361056, 2.162083), new LatLng(41.361462, 2.161813), new LatLng(41.361897, 2.162526), new LatLng(41.362404, 2.162772), new LatLng(41.362768, 2.163164), new LatLng(41.362066, 2.162858), new LatLng(41.361116, 2.162472), new LatLng(41.360423, 2.162193), new LatLng(41.360190, 2.162215), new LatLng(41.360520, 2.162697), new LatLng(41.360739, 2.162984), new LatLng(41.361525, 2.164079), new LatLng(41.361913, 2.164961), new LatLng(41.362235, 2.165304), new LatLng(41.362662, 2.166388), new LatLng(41.363179, 2.167684), new LatLng(41.363334, 2.167700), new LatLng(41.363554, 2.167739), new LatLng(41.363965, 2.167767), new LatLng(41.364178, 2.167511), new LatLng(41.364482, 2.167504), new LatLng(41.364578, 2.167515), new LatLng(41.365537, 2.167482), new LatLng(41.366358, 2.167740), new LatLng(41.366809, 2.167944), new LatLng(41.367823, 2.168781), new LatLng(41.368765, 2.169059), new LatLng(41.369104, 2.170079), new LatLng(41.369248, 2.170272), new LatLng(41.369405, 2.170250), new LatLng(41.369804, 2.168759), new LatLng(41.369925, 2.168727), new LatLng(41.369895, 2.169132), new LatLng(41.369997, 2.169510), new LatLng(41.370126, 2.170122), new LatLng(41.370219, 2.170644), new LatLng(41.370394, 2.170787), new LatLng(41.370947, 2.170572), new LatLng(41.371145, 2.171602), new LatLng(41.370476, 2.171790), new LatLng(41.370484, 2.172024), new LatLng(41.370505, 2.172252), new LatLng(41.370529, 2.172450), new LatLng(41.370336, 2.172745), new LatLng(41.370182, 2.172793), new LatLng(41.370066, 2.172981), new LatLng(41.370182, 2.173040), new LatLng(41.370456, 2.172976), new LatLng(41.370702, 2.172927), new LatLng(41.370988, 2.172761), new LatLng(41.371157, 2.172879), new LatLng(41.371310, 2.172825), new LatLng(41.371514, 2.172557), new LatLng(41.371686, 2.172315), new LatLng(41.371858, 2.172086), new LatLng(41.371832, 2.170540), new LatLng(41.371966, 2.171162), new LatLng(41.372159, 2.171884), new LatLng(41.372414, 2.172004), new LatLng(41.374739, 2.173190), new LatLng(41.375102, 2.173877), new LatLng(41.375706, 2.174944), new LatLng(41.377310, 2.176482), new LatLng(41.378773, 2.175400), new LatLng(41.378998, 2.175722), new LatLng(41.380258, 2.176972), new LatLng(41.380335, 2.177160), new LatLng(41.380512, 2.177213), new LatLng(41.380685, 2.177090), new LatLng(41.381425, 2.177514), new LatLng(41.381900, 2.177224), new LatLng(41.382259, 2.177766), new LatLng(41.382907, 2.177079), new LatLng(41.383172, 2.176822), new LatLng(41.383382, 2.177149), new LatLng(41.383651, 2.176790), new LatLng(41.383627, 2.176570), new LatLng(41.383696, 2.176473), new LatLng(41.383470, 2.176162), new LatLng(41.383909, 2.175556), new LatLng(41.383957, 2.175347), new LatLng(41.384150, 2.175186), new LatLng(41.384263, 2.175164)
                )
                .width(7)
                .color(Color.RED));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mapready=true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_LOCATION);
            return;
        }else {
            mMap.setMyLocationEnabled(true);
        }

        Toast.makeText(MapActivity.this, "Clica la icona per obtenir informació", Toast.LENGTH_LONG).show();

        setMarker("Festa Magic Line", "Photocall 11-18:30 h. Concert: 17-18 h.", 41.38477, 2.17574, R.drawable.festa);
        setMarker("Arribada Pl. de la Catedral", "Tancament a les 18:30 h", 41.3844, 2.1752, R.drawable.sortida); //Pl de la Catedral,
        setMarker("Tram adaptat sense escales", " ", 41.3708, 2.169, R.drawable.adapt);

        setMarker("Font", "", 41.41649, 2.15293, R.drawable.fonts);
        setMarker("Font", "", 41.42317, 2.12254, R.drawable.fonts);
        setMarker("Font", "", 41.41949, 2.16376, R.drawable.fonts);
        setMarker("Font", "", 41.42042, 2.16717, R.drawable.fonts);
        setMarker("Font", "", 41.43336, 2.1666, R.drawable.fonts);
        setMarker("Font", "", 41.45397, 2.17446, R.drawable.fonts);
        setMarker("Font", "", 41.41437, 2.10534, R.drawable.fonts);
        setMarker("Font", "", 41.38575, 2.11641, R.drawable.fonts);
        setMarker("Font", "", 41.4544, 2.17441, R.drawable.fonts);
        setMarker("Font", "", 41.43488, 2.1662, R.drawable.fonts);
        setMarker("Font", "", 41.43336, 2.1666, R.drawable.fonts);
        setMarker("Font", "", 41.43272, 2.16557, R.drawable.fonts);
        setMarker("Font", "", 41.43293, 2.16495, R.drawable.fonts);
        setMarker("Font", "", 41.43214, 2.165, R.drawable.fonts);
        setMarker("Font", "", 41.42409, 2.16546, R.drawable.fonts);
        setMarker("Font", "", 41.37281, 2.15025, R.drawable.fonts);
        setMarker("Font", "", 41.36102, 2.16168, R.drawable.fonts);
        setMarker("Font", "", 41.38462, 2.12367, R.drawable.fonts);
        setMarker("Font", "", 41.38219, 2.12768, R.drawable.fonts);
        setMarker("Font", "", 41.37281, 2.15025, R.drawable.fonts);

        setMarker("Fes-te una foto!", "Us hi espera una sorpresa! Tancament 16.30 h", 41.36487, 2.15557, R.drawable.cam);

        setMarker("Control Castell de Montjuïc", "Tancament: 17.15 h", 41.3607, 2.16243, R.drawable.control);
        setMarker("Control Mont Tàber", "Tancament: 18.20 h", 41.38343, 2.17714, R.drawable.control);
        setMarker("Control Sant Pere Màrtir", "Tancament: 14.05 h", 41.3938, 2.09778, R.drawable.control);
        setMarker("Control Tibidabo", "Tancament: 13.05 h", 41.4218, 2.1187, R.drawable.control);
        setMarker("Control Turó Creueta del Coll", "Tancament: 11.30 h", 41.41979, 2.14688, R.drawable.control);
        setMarker("Control Turó del Carmel", "Tancament: 11.15 h", 41.41856, 2.15435, R.drawable.control);
        setMarker("Control Turó de la Peira", "Tancament: 10.00 h", 41.43287, 2.16559, R.drawable.control);
        setMarker("Control Torre Baró", "Tancament: 08.30 h", 41.4514, 2.1764, R.drawable.control);
        setMarker("Control Turó de La Rovira", "Tancament: 11.00 h", 41.41927, 2.16177, R.drawable.control);

        setMarker("Assistència mèdica", "Tancament: 13.30 h", 41.40716, 2.10119, R.drawable.ambulancia);
        setMarker("Ambulància", "Assistència mèdica", 41.42678, 2.12344, R.drawable.ambulancia);

        setMarker("Avituallament Tibidabo", "Tancament: 13:05 h", 441.41914, 2.13904, R.drawable.avituallament);
        setMarker("Avituallament Castell de Montjüic", "Tancament: 17:05 h", 441.41914, 2.13904, R.drawable.avituallament);
        setMarker("Avituallament Hospital Sant Joan de Déu", "Tancament: 14.50 h", 41.38485, 2.10231, R.drawable.avituallament);
        setMarker("Avituallament Rda de Dalt", "Tancament: 11:50 h", 41.42165, 2.11906, R.drawable.avituallament);
        setMarker("Avituallament Castell de Montjuïc", "Tancament: 17:05 h", 41.36122, 2.16233, R.drawable.avituallament);


        setMarker("Lavabos", "", 41.38504, 2.10287, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.42142, 2.1194, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.38505, 2.17607, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.3709, 2.15036, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.41419, 2.10503, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.42188, 2.16504, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.36166, 2.16223, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.41917, 2.13929, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.36886, 2.14674, R.drawable.lavabos);
        setMarker("Lavabos", "IES Les Corts. Horaris: 10.30 - 15.20 h", 41.38144, 2.12618, R.drawable.lavabos);

        setMarker("Benvinguts al Parc Natural de Collserola", "", 41.42153, 2.13645, R.drawable.museu);
        setMarker("CaixaFòrum", "Entrada lliure 10:00 - 15:00 h.", 41.37135, 2.15, R.drawable.museu);
        setMarker("Poble Espanyol", "Entrada lliure de 10.00 a 15.00 h",41.36914, 2.14704 , R.drawable.museu);
        setMarker("Museu Nacional d'Art de Catalunya", "Entrada lliure de 10 a 14.00 h",41.36893, 2.15323 , R.drawable.museu);
        setMarker("Fundació Miró", "Entrada lliure de 10 a 13.45 h", 41.36818, 2.16011, R.drawable.museu);
        setMarker("Museu Olímpic i de l'Esport", "Entrada lliure de 10.30 a 14.00 h", 41.36635, 2.15711, R.drawable.museu);
        setMarker("Benvinguts al Jardí Botànic", "Entrada lliure de 10.30 a 16.45 h", 41.36282, 2.15724, R.drawable.museu);
        setMarker("Castell de Montjuïc", "Visita gratuïta de 15 - 17:15 h.", 41.36402, 2.16677, R.drawable.museu);

        //Sortides
        setMarker("Sortida 42 km. Can Cuiàs", "Sortides de 7:00 - 7:30 h. Tancament: 7:50 h", 41.46001, 2.16822, R.drawable.sortida);
        setMarker("Sortida 30 km. Parc de les Aigües", " Sortides: 8:00 - 9:00 h. Tancament: 9:30 h", 41.4124, 2.16605, R.drawable.sortida);
        setMarker("Sortida 20 km. Vallvidrera", "sortides: 9:00 - 11:00 h. Tancament: 11:20 h", 41.41419, 2.10518, R.drawable.sortida);
        setMarker("Sortida 10 km. Font de Montjuïc", "sortida: 10:00 - 12:00 h. Tancament: 12:20 h", 41.370, 2.150, R.drawable.sortida);


        LatLng pos = new LatLng(41.42188, 2.16504);
        int zoom = 12; //zoom has to be an integer 0-18
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, zoom);

        mMap.moveCamera(update);
        setLines(mMap);
    }

    //Add marker
    public void setMarker(String title, String text, double tal, double lon, int resourceId) {

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(tal, lon))
                .title(title)
                .snippet(text)
                .icon(BitmapDescriptorFactory.fromResource(resourceId));
        mMap.addMarker(markerOptions);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mapready){
                        mMap.setMyLocationEnabled(true);
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        // Connected to Google Play services!
        // The good stuff goes here.

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_LOCATION);
            return;
        }

       /* lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);

       if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }else {
           createLocationRequest();
           startLocationUpdates();
       }*/

    }

    //Save state in case of change
    public void onSaveInstanceState(Bundle savedInstanceState) {
       /*savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY,
                mRequestingLocationUpdates);
        savedInstanceState.putParcelable(LOCATION_KEY, mCurrentLocation);
        savedInstanceState.putString(LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);*/
        super.onSaveInstanceState(savedInstanceState);
    }


    protected void createLocationRequest() {

        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mRequestingLocationUpdates=true;
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},10);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(client, mLocationRequest, (LocationListener) this);
    }
/*
    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();
    }*/



    @Override
    public void onConnectionSuspended(int i) {
        // The connection has been interrupted.
        // Disable any UI components that depend on Google APIs
        // until onConnected() is called.
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // This callback is important for handling errors that
        // may occur while attempting to connect with Google.
        // More about this in the 'Handle Connection Failures' section.
        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.
                client.connect();
            }
        } else {
            // Show dialog using GoogleApiAvailability.getErrorDialog()
            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }


    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getFragmentManager(), "errordialog");
    }
    /* Called from ErrorDialogFragment when the dialog is dismissed. */


    public void onDialogDismissed() {
        mResolvingError = false;
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {
        public ErrorDialogFragment() { }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(), errorCode, REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((MapActivity) getActivity()).onDialogDismissed();
        }
    }
    /*Once the user completes the resolution provided by startResolutionForResult() or
    GoogleApiAvailability.getErrorDialog(), your activity receives the onActivityResult()
    callback with the RESULT_OK result code. You can then call connect() again. For example:*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            mResolvingError = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!client.isConnecting() &&
                        !client.isConnected()) {
                    client.connect();
                }
            }
        }
    }




}
