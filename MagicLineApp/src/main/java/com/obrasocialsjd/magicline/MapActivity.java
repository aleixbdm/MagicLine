package com.obrasocialsjd.magicline;

import android.Manifest;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;

import android.net.Uri;
import android.preference.EditTextPreference;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback
        , ConnectionCallbacks, OnConnectionFailedListener {

    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final int MY_PERMISSIONS_LOCATION = 101;
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

    private View viewFragment;

    private Button button_10km;
    private Button button_15km;
    private Button button_20km;
    private Button button_25km;
    private Button button_30km;
    private Button button_40km;
    private Button actual_button;
    private ImageButton button_hide_markers;

    private boolean markers_are_hidden = true;

    private static int INITIAL_DEPARTURE = 10;

    private KmlLayer layerMap;

    private ArrayList<Marker> arrayListMarker = new ArrayList<>();
    private HashMap<Integer, Marker> hashMapMarkerDepartures = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mapready = false;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        viewFragment = mapFragment.getView();

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
        mRequestingLocationUpdates = false;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mapready = true;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_LOCATION);
            return;
        } else {
            mMap.setMyLocationEnabled(true);
        }

        Toast.makeText(MapActivity.this, "Clica la icona per obtenir informació", Toast.LENGTH_LONG).show();

        setMarker("Concert solidari", "Música per la inclusió de 17 a 18:30h.", 41.384649, 2.175834, R.drawable.festa);
        setMarker("PicNic i Espectacles", "Tancament 17:15h.", 41.361957, 2.162834, R.drawable.festa);
        setMarker("Arribada Pl. de la Catedral", "Tancament a les 19 h", 41.384161, 2.175301, R.drawable.sortida);

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
        setMarker("Font", "", 41.34789, 2.11109, R.drawable.fonts);


        setMarker("Font d'en Fargues", "Hora tancament: 10:30h", 41.421444, 2.164811,
                R.drawable.fonts);

        //setMarker("Fes-te una foto!", "Us hi espera una sorpresa! Tancament 16.30 h", 41.36487, 2.15557, R.drawable.cam);

        setMarker("Control Castell de Montjuïc", "Tancament: 17:15 h",
                41.361774, 2.162314, R.drawable.control);
        setMarker("Control Mont Tàber", "Tancament: 18:45 h", 41.383420, 2.177092, R.drawable.control);
        setMarker("Control Sant Pere Màrtir", "Tancament: 14.30 h", 41.393061, 2.097455, R.drawable.control);
        setMarker("Control Tibidabo", "Tancament: 13.05 h", 41.421686, 2.119099, R.drawable.control);
        setMarker("Control Turó Creueta del Coll", "Tancament: 11.45 h", 41.419911, 2.146881, R.drawable.control);
        setMarker("Control Turó del Carmel", "Tancament: 11.30 h", 41.418475, 2.153175, R.drawable.control);
        setMarker("Control Turó de la Peira", "Tancament: 10.00 h", 41.432796, 2.165544, R.drawable.control);
        setMarker("Control Turó de les Roquetes", "Tancament: 08.30 h", 41.451314, 2.176326, R.drawable.control);
        setMarker("Control Turó de La Rovira", "Tancament: 11.00 h", 41.419472, 2.162085, R.drawable.control);
        setMarker("Control La Muntanyeta", "Tancament: 11.15h", 41.34255, 2.03369, R.drawable.control);

        setMarker("Assistència mèdica", "Tancament: 13.30 h", 41.40716, 2.10119, R.drawable.ambulancia);
        setMarker("Ambulància", "Assistència mèdica", 41.42678, 2.12344, R.drawable.ambulancia);

        setMarker("Avituallament Tibidabo", "Tancament: 13:05 h", 41.421690, 2.119167,
                R.drawable.avituallament);
        setMarker("Avituallament Hospital Sant Joan de Déu", "Tancament: 14.50 h", 41.384850, 2.102310,
                R.drawable.avituallament);
        setMarker("Avituallament Ronda de Dalt", "Tancament: 11:50 h", 41.420494, 2.139901,
                R.drawable.avituallament);
        setMarker("Avituallament Montjuïc", "Tancament: 17:15 h", 41.361957, 2.162834,
                R.drawable.avituallament);
        setMarker("Avituallament Maternitat", "Tancament: 15:15 h", 41.381334, 2.126326,
                R.drawable.avituallament);
        setMarker("Avituallament Estadi Olímpic", "Tancament: 16:15 h", 41.365892, 2.154148,
                R.drawable.avituallament);
        setMarker("Avituallament", "10-12.30h\n", 41.34752, 2.05318,
                R.drawable.avituallament);
        setMarker("Avituallament Bellvitge", "10 a 14h\n", 41.34868, 2.11076,
                R.drawable.avituallament);

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
        setMarker("Lavabos", "", 41.35327, 2.03535, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.34708, 2.04575, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.34707, 2.04671, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.34744, 2.05354, R.drawable.lavabos);
        setMarker("Lavabos", "", 41.34836, 2.11134, R.drawable.lavabos);

        setMarker("Dinamització Sortida 40km",
                "Speaker Enric Lucema\n" +
                        "7.00 a 7.45h\n", 41.45997, 2.16831, R.drawable.theater);
        setMarker("Dinamització Sortida 30km",
                "Speaker Emma\n" +
                        "8.00 a 9.30h", 41.41247, 2.16611, R.drawable.theater);
        setMarker("Dinamització Sortida 25km",
                "Speaker \n" +
                        "Exposició La Mirada de l'altre\n" +
                        "Taller de llanes\n" +
                        "8.30 a 10.30 h", 41.35333, 2.03565, R.drawable.theater);
        setMarker("Dinamització Sortida 15km",
                "Speaker Jordi Mercader\n" +
                        "10h a 11.30h", 41.3838, 2.10191, R.drawable.theater);
        setMarker("Dinamització Sortida 10km\n",
                "9.30 a 12.30h\n" +
                        "Speaker Vanessa", 41.37078, 2.15027, R.drawable.theater);

        setMarker("Espectacle Sonambulistes\n",
                "17.10 a 18h\n" +
                        "Fundació AIMS, Xamfrà, Fundació Vozes", 41.38466, 2.17579, R.drawable.theater);
        setMarker("Dinamització Fes-te la foto",
                "7.30 a 8.45h\n" +
                        "Institut Roquetes", 41.45134, 2.1764, R.drawable.theater);
        setMarker("Dinamització Gegants + S de solidaritat",
                "8.30 a 11.30h\n" +
                        "Escola Virolai\n", 41.41661, 2.15304, R.drawable.theater);
        setMarker("Dinamització teatral  Fans de la Magic line",
                "8.40 a 11.45 h\n" +
                        "Escola de la Mercè", 41.41955, 2.14513, R.drawable.theater);
        setMarker("Mural col·laboratiu Jardí Vertical\n",
                "13 a 17 h\n" +
                        "Torrents d'Art i Katia", 41.38432, 2.17557, R.drawable.theater);
        setMarker("Exposició SJD\n",
                "11 a 19h\n", 41.38439, 2.17549, R.drawable.theater);
        setMarker("Dinamització Mural Refugiarte\n",
                "11.30-16.30h\n" +
                        "Col.lectiu Refugiarte\n", 41.36231, 2.16312, R.drawable.theater);
        setMarker("Dinamització Mural S de Solidaritat\n",
                "12h a 17 h\n" +
                        "Escola Anna Ravell", 41.36214, 2.16287, R.drawable.theater);
        setMarker("Pic Nic Cultural - Escenari\n",
                "Concert Joan Trenchs 11-12h\n" +
                        "Rock and roll infantil amb The Git and Jalis 12 - 13h\n" +
                        "Karaokeband 13 -15h \n" +
                        "Màgia amb el Borja Pérez 15 - 16 h\n" +
                        "Concert Joan Trenchs 16- 18h\n", 41.36284, 2.16297, R.drawable.theater);
        setMarker("Pic Nic Cultural - Jocs cooperatius\n",
                "D'11 a 16.30h\n" +
                        "Escola Ramon Fuster\n", 41.36271, 2.16317, R.drawable.theater);
        setMarker("Dinamització Benvinguda al Castell + S de solidaritat\n",
                "11 a 17.45h\n" +
                        "Institut Jaume Balmes\n", 41.3603, 2.16195, R.drawable.theater);
        setMarker("Dinamització Coral The New Zombies\n",
                "12 a 13.30h\n", 41.36214, 2.15723, R.drawable.theater);
        setMarker("Dinamització Batucada\n",
                "9.30 a 11.15\n" +
                        "Batucada Sant Boi\n", 41.34162, 2.03241, R.drawable.theater);
        setMarker("Dinamització Nordic Walking",
                "8.30 i 9.30h\n" +
                        "Fundació Caminoterapia", 41.35306, 2.03576, R.drawable.theater);
        setMarker("Dinamització Musicoteràpia",
                "10 a 12.15h\n" +
                        "Tallers de musicoteràpia del PSSJD", 41.34735, 2.04301, R.drawable.theater);
        setMarker("Dinamització musical Acordionista\n",
                "Toni Rosselló\n" +
                        "10 a 12.30h", 41.34744, 2.05302, R.drawable.theater);
        setMarker("Dinamització Escola Municipal de Música EMMCA\n",
                "11 a 14h\n", 41.34875, 2.10971, R.drawable.theater);


        /*setMarker("Benvinguts al Parc Natural de Collserola", "", 41.42153, 2.13645,
                R.drawable.museu);*/
        setMarker("CaixaFòrum", "Entrada lliure de 10:00 a 14:30 h.", 41.371304, 2.149712,
                R.drawable.museu);
        setMarker("Poble Espanyol", "Entrada lliure de 10:00 a 14:30 h", 41.369194, 2.146665,
                R.drawable.museu);
        setMarker("Museu Nacional d'Art de Catalunya", "Entrada lliure de 10:00 a 14:30 h",
                41.368992, 2.153224, R.drawable.museu);
        setMarker("Fundació Joan Miró", "Entrada lliure de 10 a 14:30 h", 41.368367, 2.159964,
                R.drawable.museu);
        setMarker("Museu Olímpic i de l'Esport", "Entrada lliure de 10:30 a 14:30 h",
                41.366142, 2.157326, R.drawable.museu);
        setMarker("Jardí Botànic", "Entrada lliure de 10:00 a 16:30 h", 41.361957, 2.162834, R.drawable.museu);
        /*setMarker("Castell de Montjuïc", "Visita gratuïta de 15 - 17:15 h.",
            41.36402, 2.16677, R.drawable.museu);*/
        setMarker("Museu de Sant Boi", "", 41.347, 2.04594, R.drawable.museu);
        setMarker("Termes de Sant Boi", "", 41.34739, 2.04649, R.drawable.museu);
        setMarker("Ermita de Bellvitge", "Oberta durant la caminada\n", 41.34756, 2.10983, R.drawable.museu);

        //Sortides

        setMarkerDeparture(40,"Sortida 40 km. Can Cuiàs", "Sortida: 7:00h. Tancament: 7:15 h",
                41.459910, 2.168247, R.drawable.sortida);
        setMarkerDeparture(30,"Sortida 30 km. Parc de les Aigües", "Sortida: 8:00h. Tancament: 9:00h",
                41.412340, 2.166033, R.drawable.sortida);
        setMarkerDeparture(25,"Sortida 25 km. San Baudilio", "Sortida: 9:00h. Tancament: 10:00h",
                41.35309, 2.0356, R.drawable.sortida);
        setMarkerDeparture(20,"Sortida 20 km. Vallvidrera", "Sortida: 9:00h. Tancament: 11:00h",
                41.414073, 2.105199, R.drawable.sortida);
        setMarkerDeparture(15,"Sortida 15 km. Hospital Sant Joan de Déu", "Sortida: 9:00h. Tancament: 10:30h",
                41.383750, 2.101865, R.drawable.sortida);
        setMarkerDeparture(10,"Sortida 10 km. Font Màgica", "Sortida: 10:00h. Tancament: 12:00h",
                41.370668, 2.150492, R.drawable.sortida);

        LatLng pos = new LatLng(41.42188, 2.16504);
        int zoom = 12; //zoom has to be an integer 0-18
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, zoom);

        mMap.moveCamera(update);

        //Hide all markers at the beginning
        hideMarkers();
        hideShowDepartureMarkers(INITIAL_DEPARTURE);

        //Button my location

        // Get the button view
        View locationButton = ((View) viewFragment.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));

        // and next place it, for exemple, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 30);


        //ROUTES
        try {
            layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2018_10, getApplicationContext());
            layerMap.addLayerToMap();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        button_10km = (Button) viewFragment.findViewById(R.id.button_10km);
        actual_button = button_10km;
        button_10km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actual_button.setTextColor(getResources().getColor(R.color.buttonNotPressed));
                actual_button = button_10km;
                actual_button.setTextColor(getResources().getColor(R.color.buttonPressed));
                hideShowDepartureMarkers(10);
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2018_10, getApplicationContext());
                    layerMap.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

        button_15km = (Button) viewFragment.findViewById(R.id.button_15km);
        button_15km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actual_button.setTextColor(getResources().getColor(R.color.buttonNotPressed));
                actual_button = button_15km;
                actual_button.setTextColor(getResources().getColor(R.color.buttonPressed));
                hideShowDepartureMarkers(15);
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2018_15, getApplicationContext());
                    layerMap.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

        button_20km = (Button) viewFragment.findViewById(R.id.button_20km);
        button_20km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actual_button.setTextColor(getResources().getColor(R.color.buttonNotPressed));
                actual_button = button_20km;
                actual_button.setTextColor(getResources().getColor(R.color.buttonPressed));
                hideShowDepartureMarkers(20);
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2018_20, getApplicationContext());
                    layerMap.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

        button_25km = (Button) viewFragment.findViewById(R.id.button_25km);
        button_25km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actual_button.setTextColor(getResources().getColor(R.color.buttonNotPressed));
                actual_button = button_25km;
                actual_button.setTextColor(getResources().getColor(R.color.buttonPressed));
                hideShowDepartureMarkers(25);
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2018_25, getApplicationContext());
                    layerMap.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

        button_30km = (Button) viewFragment.findViewById(R.id.button_30km);
        button_30km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actual_button.setTextColor(getResources().getColor(R.color.buttonNotPressed));
                actual_button = button_30km;
                actual_button.setTextColor(getResources().getColor(R.color.buttonPressed));
                hideShowDepartureMarkers(30);
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2018_30, getApplicationContext());
                    layerMap.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

        button_40km = (Button) viewFragment.findViewById(R.id.button_40km);
        button_40km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actual_button.setTextColor(getResources().getColor(R.color.buttonNotPressed));
                actual_button = button_40km;
                actual_button.setTextColor(getResources().getColor(R.color.buttonPressed));
                hideShowDepartureMarkers(40);
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2018_40, getApplicationContext());
                    layerMap.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        });

        button_hide_markers = (ImageButton) viewFragment.findViewById(R.id.button_show_markers);
        button_hide_markers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markers_are_hidden = !markers_are_hidden;
                if (markers_are_hidden) hideMarkers();
                else showMarkers();
            }
        });
    }

    //Add marker
    public void setMarker(String title, String text, double tal, double lon, int resourceId) {

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(tal, lon))
                .title(title)
                .snippet(text)
                .icon(BitmapDescriptorFactory.fromResource(resourceId));
        Marker marker = mMap.addMarker(markerOptions);
        arrayListMarker.add(marker);
    }

    private void setMarkerDeparture(int km, String title, String text, double tal, double lon, int resourceId){
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(tal, lon))
                .title(title)
                .snippet(text)
                .icon(BitmapDescriptorFactory.fromResource(resourceId));
        Marker marker = mMap.addMarker(markerOptions);
        hashMapMarkerDepartures.put(km,marker);
    }

    private void hideMarkers(){
        for (Marker marker : arrayListMarker){
            marker.setVisible(false);
        }
    }

    private void hideShowDepartureMarkers(int km){
        //Hide all besides the km passed
        for (int kmaux : hashMapMarkerDepartures.keySet()){
            if (kmaux != km) hashMapMarkerDepartures.get(kmaux).setVisible(false);
            else hashMapMarkerDepartures.get(kmaux).setVisible(true);
        }
    }

    private void showMarkers(){
        for (Marker marker : arrayListMarker){
            marker.setVisible(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mapready) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
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
