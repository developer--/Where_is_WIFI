package com.awesomethings.whereiswifi;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.awesomethings.whereiswifi.app.permission.MyPermission;

/**
 * Created by Jemo on 8/8/16.
 */

public class Test {
    void test(Activity context){
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (new MyPermission().checkCoarseLocationPermission(context) && new MyPermission().checkCoarseLocationPermission(context)) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {

                }

                @Override
                public void onProviderEnabled(String s) {

                }

                @Override
                public void onProviderDisabled(String s) {

                }
            });
        } else {
            new MyPermission().requestLocationPermission(context);
        }
    }
}
