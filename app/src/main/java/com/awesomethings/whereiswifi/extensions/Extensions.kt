package com.awesomethings.whereiswifi.extensions

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.Exclude
import java.util.*

/**
 * Created by Jemo on 8/11/16.
 */
@Exclude
fun LatLng.toMap() : Map<String, Objects>{
    val result = HashMap<String,Objects>()
//    result.put(this.toString(), this)
    return result
}