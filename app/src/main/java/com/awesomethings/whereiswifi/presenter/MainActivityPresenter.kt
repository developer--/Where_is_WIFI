package com.awesomethings.whereiswifi.presenter

import android.app.Activity
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import com.awesomethings.whereiswifi.app.permission.MyPermission
import com.awesomethings.whereiswifi.interfaces.IOnLocationReceive
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Master on 8/4/16.
 */
class MainActivityPresenter : LocationListener {

    private lateinit var locationReceive : IOnLocationReceive
    private lateinit var locationManager : LocationManager
    constructor(locationReceive: IOnLocationReceive) {
        this.locationReceive = locationReceive
    }

    fun startLocationListener(locationManager : LocationManager, activity : Activity){
        if (MyPermission().checkCoarseLocationPermission(activity) && MyPermission().checkCoarseLocationPermission(activity)) {
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this)
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this)
        } else {
            MyPermission().requestLocationPermission(activity)
        }
    }

    override fun onLocationChanged(p0: Location?) {
        locationReceive.onLocationReceive(LatLng(p0!!.latitude, p0.longitude))
    }

    override fun onProviderDisabled(p0: String?) {

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }
}