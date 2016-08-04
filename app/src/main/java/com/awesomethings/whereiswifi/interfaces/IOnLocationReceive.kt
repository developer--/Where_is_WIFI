package com.awesomethings.whereiswifi.interfaces

import com.google.android.gms.maps.model.LatLng

/**
 * Created by Master on 8/4/16.
 */
interface IOnLocationReceive {
    fun onLocationReveive(latLng: LatLng)
}