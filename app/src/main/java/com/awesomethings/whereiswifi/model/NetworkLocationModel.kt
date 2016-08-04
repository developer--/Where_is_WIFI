package com.awesomethings.whereiswifi.model

import com.activeandroid.Model
import com.activeandroid.annotation.Table
import com.google.android.gms.maps.model.LatLng

/**
 * Created by Master on 8/4/16.
 */
@Table(name = "NetworkLocationModel")
class NetworkLocationModel : Model() {
    val latitude : Double = 0.0
    val longitude : Double = 0.0

    fun getLatLng() : LatLng{
        return LatLng(latitude,longitude)
    }
}