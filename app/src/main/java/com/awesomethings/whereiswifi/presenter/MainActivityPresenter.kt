package com.awesomethings.whereiswifi.presenter

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import com.awesomethings.whereiswifi.interfaces.INetworkListener
import com.awesomethings.whereiswifi.interfaces.IOnLocationReceive
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Master on 8/4/16.
 */
class MainActivityPresenter : INetworkListener, LocationListener {

    private lateinit var locationReceive : IOnLocationReceive

    constructor(locationReceive: IOnLocationReceive) {
        this.locationReceive = locationReceive
    }


    override fun onNetworkReceive() {

    }

    override fun onNetworkGone() {
        
    }

    override fun onLocationChanged(p0: Location?) {
        locationReceive.onLocationReveive(LatLng(p0!!.latitude, p0.longitude))
    }

    override fun onProviderDisabled(p0: String?) {

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

    }

    override fun onProviderEnabled(p0: String?) {

    }
}