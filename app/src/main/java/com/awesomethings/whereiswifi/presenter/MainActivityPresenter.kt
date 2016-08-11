package com.awesomethings.whereiswifi.presenter

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.awesomethings.whereiswifi.app.permission.MyPermission
import com.awesomethings.whereiswifi.database.DBManager
import com.awesomethings.whereiswifi.extensions.toMap
import com.awesomethings.whereiswifi.interfaces.IOnLocationReceive
import com.awesomethings.whereiswifi.model.NetworkLocationModel
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import java.util.*

/**
 * Created by Jemo on 8/4/16.
 */
class MainActivityPresenter : LocationListener {

    val firebaseDB = FirebaseDatabase.getInstance().getReference("wifi")

    lateinit var locationManager : LocationManager
    lateinit var listOfMarkers : ArrayList<NetworkLocationModel>
    lateinit var listOfLatLng : ArrayList<LatLng>
    lateinit var map : Map<String,LatLng>
    lateinit var dbManager : DBManager
    constructor(locationReceive: IOnLocationReceive, activity : Activity) {
        this.locationReceive = locationReceive
        this.listOfMarkers = ArrayList<NetworkLocationModel>()
        this.listOfLatLng = ArrayList<LatLng>()
        this.map = HashMap<String, LatLng>()
        this.dbManager = DBManager()
        this.locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private lateinit var locationReceive : IOnLocationReceive

    fun startLocationListener(activity : Activity){
        if (MyPermission().checkCoarseLocationPermission(activity) && MyPermission().checkCoarseLocationPermission(activity)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, this)
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, this)
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

    fun getCoordinates() : List<LatLng> {
        val list = dbManager.getCoordinates()
        val coords = mutableListOf<LatLng>()
        for (i : NetworkLocationModel in list){
            coords.add(i.getLatLng())
        }
        return coords
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

    fun saveValues(list : ArrayList<NetworkLocationModel>) {
        firebaseDB.setValue(list)
        for (i : NetworkLocationModel in list){
            firebaseDB.updateChildren(i.toMap())
//            (map as HashMap<String,LatLng>).put(i.toString(),i)
        }
    }
    fun pushChanges(){
//        firebaseDB.updateChildren(map.toMap)
    }

}