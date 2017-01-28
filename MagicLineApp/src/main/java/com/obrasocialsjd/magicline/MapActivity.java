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
import android.view.View;
import android.widget.Button;
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
import com.google.maps.android.kml.KmlContainer;
import com.google.maps.android.kml.KmlLayer;
import com.google.maps.android.kml.KmlPlacemark;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Iterator;

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
    private Button button_30km;
    private Button button_40km;
    private Button actual_button;

    private KmlLayer layerMap;

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
        setMarker("Poble Espanyol", "Entrada lliure de 10.00 a 15.00 h", 41.36914, 2.14704, R.drawable.museu);
        setMarker("Museu Nacional d'Art de Catalunya", "Entrada lliure de 10 a 14.00 h", 41.36893, 2.15323, R.drawable.museu);
        setMarker("Fundació Miró", "Entrada lliure de 10 a 13.45 h", 41.36818, 2.16011, R.drawable.museu);
        setMarker("Museu Olímpic i de l'Esport", "Entrada lliure de 10.30 a 14.00 h", 41.36635, 2.15711, R.drawable.museu);
        setMarker("Benvinguts al Jardí Botànic", "Entrada lliure de 10.30 a 16.45 h", 41.36282, 2.15724, R.drawable.museu);
        setMarker("Castell de Montjuïc", "Visita gratuïta de 15 - 17:15 h.", 41.36402, 2.16677, R.drawable.museu);

        //Sortides
        /*
        setMarker("Sortida 42 km. Can Cuiàs", "Sortides de 7:00 - 7:30 h. Tancament: 7:50 h", 41.46001, 2.16822, R.drawable.sortida);
        setMarker("Sortida 30 km. Parc de les Aigües", " Sortides: 8:00 - 9:00 h. Tancament: 9:30 h", 41.4124, 2.16605, R.drawable.sortida);
        setMarker("Sortida 20 km. Vallvidrera", "sortides: 9:00 - 11:00 h. Tancament: 11:20 h", 41.41419, 2.10518, R.drawable.sortida);
        setMarker("Sortida 10 km. Font de Montjuïc", "sortida: 10:00 - 12:00 h. Tancament: 12:20 h", 41.370, 2.150, R.drawable.sortida);
        */


        LatLng pos = new LatLng(41.42188, 2.16504);
        int zoom = 12; //zoom has to be an integer 0-18
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, zoom);

        mMap.moveCamera(update);

        //ROUTES
        try {
            layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2017_10, getApplicationContext());
            for (KmlContainer container : layerMap.getContainers()) {
                for(Object object : container.getProperties()){
                    System.out.println(object.toString());
                    System.out.println(container.getProperty(object.toString()));
                }

                for (KmlPlacemark kmlPlacemark : container.getPlacemarks()){
                    System.out.println("Placemark: "+kmlPlacemark.toString());
                    for(Object object : kmlPlacemark.getProperties()){
                        System.out.println(object.toString());
                        System.out.println(kmlPlacemark.getProperty(object.toString()));
                    }
                }
            }

            for(KmlPlacemark kmlPlacemark : layerMap.getPlacemarks()){
                System.out.println(kmlPlacemark.toString());
                for(Object object : kmlPlacemark.getProperties()){
                    System.out.println(object.toString());
                    System.out.println(kmlPlacemark.getProperty(object.toString()));
                }
            }

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
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2017_10, getApplicationContext());
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
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2017_15, getApplicationContext());
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
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2017_20, getApplicationContext());
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
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2017_30, getApplicationContext());
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
                layerMap.removeLayerFromMap();
                try {
                    layerMap = new KmlLayer(mMap, R.raw.ml_barcelona_2017_40, getApplicationContext());
                    layerMap.addLayerToMap();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
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
