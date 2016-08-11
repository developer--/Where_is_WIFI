package com.awesomethings.whereiswifi.presenter

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.awesomethings.whereiswifi.app.permission.MyPermission
import com.awesomethings.whereiswifi.interfaces.IOnLocationReceive
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Jemo on 8/4/16.
 */
class MainActivityPresenter : LocationListener {

    lateinit var locationManager : LocationManager
    private lateinit var locationReceive : IOnLocationReceive
    constructor(locationReceive: IOnLocationReceive, activity : Activity) {
        this.locationReceive = locationReceive
        this.locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun startLocationListener(activity : Activity){
        if (MyPermission().checkCoarseLocationPermission(activity) && MyPermission().checkCoarseLocationPermission(activity)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
        } else {
            MyPermission().requestLocationPermission(activity)
        }
    }


    fun stopLocationUpdates(activity : Activity){
        if (MyPermission().checkCoarseLocationPermission(activity) && MyPermission().checkCoarseLocationPermission(activity)) {
            locationManager.removeUpdates(this)
        } else {
            MyPermission().requestLocationPermission(activity)
        }
    }


    override fun onLocationChanged(p0: Location?) {
        Log.e("location_demo","onLocationChanged")
        locationReceive.onLocationReceive(LatLng(p0!!.latitude, p0.longitude))
    }

    override fun onProviderDisabled(p0: String?) {
        Log.e("location_demo","onProviderDisabled")
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.e("location_demo","onStatusChanged")
    }

    override fun onProviderEnabled(p0: String?) {
        Log.e("location_demo","onProviderEnabled")
    }
}