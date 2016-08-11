package com.awesomethings.whereiswifi.model

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.util.*

/**
 * Created by Master on 8/4/16.
 */
@IgnoreExtraProperties
@Table(name = "NetworkLocationModel")
class NetworkLocationModel : Model {
    @Column(name = "latitude")
    var latitude : Double = 0.0
    @Column(name = "longitude")
    var longitude : Double = 0.0

    constructor() : super()

    constructor(latLng: LatLng) : super(){
        this.latitude = latLng.latitude
        this.longitude = latLng.longitude
    }

    fun getLatLng() : LatLng{
        return LatLng(latitude,longitude)
    }


    @Exclude
    fun toMap() : Map<String, String>{
        val result = HashMap<String, String>()
//        result.put(getLatLng().toString(), getLatLng().toString())
        return result
    }
}