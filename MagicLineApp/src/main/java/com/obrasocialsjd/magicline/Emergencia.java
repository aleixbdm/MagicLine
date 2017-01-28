package com.obrasocialsjd.magicline;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Emergencia extends AppCompatActivity {

    private static Activity activity;
    private static PhoneCallListener phoneCallListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        call();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        phoneCallListener = null;
    }

    public void call() {
        try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            int permissionCheck = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                phoneCallListener = new PhoneCallListener();
                TelephonyManager telephonyManager = (TelephonyManager) this
                        .getSystemService(Context.TELEPHONY_SERVICE);
                telephonyManager.listen(phoneCallListener,
                        PhoneStateListener.LISTEN_CALL_STATE);
                callIntent.setData(Uri.parse("tel:112"));
                startActivity(callIntent);
            }
            else {
                Toast.makeText(getBaseContext(), R.string.check_permission_phone, Toast.LENGTH_LONG).show();
            }
        } catch (ActivityNotFoundException e){
            Log.e("Android exception", "Call failed", e);
        }
    }

    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling;

        String LOG_TAG = "Phone Call Listener";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
                isPhoneCalling = false;
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                Log.i(LOG_TAG, "OFFHOOK");
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                Log.i(LOG_TAG, "IDLE");
                if (isPhoneCalling) {
                    Log.i(LOG_TAG, "back to main activity");
                    finish();
                    isPhoneCalling = false;
                }
            }
        }
    }
}
