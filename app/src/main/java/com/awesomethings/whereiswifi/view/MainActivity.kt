package com.awesomethings.whereiswifi.view

import android.content.IntentFilter
import android.graphics.Color
import android.location.Criteria
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.awesomethings.whereiswifi.R
import com.awesomethings.whereiswifi.app.permission.MyPermission
import com.awesomethings.whereiswifi.interfaces.INetworkListener
import com.awesomethings.whereiswifi.interfaces.IOnLocationReceive
import com.awesomethings.whereiswifi.model.NetworkLocationModel
import com.awesomethings.whereiswifi.presenter.MainActivityPresenter
import com.awesomethings.whereiswifi.services.NetworkReceiver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.heatmaps.HeatmapTileProvider
import org.jetbrains.anko.act

class MainActivity : AppCompatActivity() , INetworkListener, IOnLocationReceive {

    private val BROADCAST = "android.net.conn.CONNECTIVITY_CHANGE"
    private lateinit var mGoogleMap : GoogleMap
    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var presenter : MainActivityPresenter
    private lateinit var heatMapProvider : HeatmapTileProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainActivityPresenter(this,this)
        setContentView(R.layout.activity_main)
    }

    /**
     * ოფლაინში ატვირთვის სერვისის გააქტიურება
     */
    private fun startNetworkListenerBroadcast() {
        networkReceiver = NetworkReceiver(this)
        val intentFilter = IntentFilter(BROADCAST)
        registerReceiver(networkReceiver, intentFilter)
    }

    /**
     * ამოვიღოთ მეპის ფრაგმენტი
     * @return SupportMapFragment
     */
    private fun getMapFragment(): SupportMapFragment {
        val manager = supportFragmentManager
        return manager.findFragmentById(R.id.map_fragment_id) as SupportMapFragment
    }

    override fun onResume() {
        super.onResume()
        Log.e("location_demo","register reciver")
        startNetworkListenerBroadcast()
        getMapFragment().getMapAsync { googleMap ->
            mGoogleMap = googleMap
            if (MyPermission().checkCoarseLocationPermission(act) && MyPermission().checkCoarseLocationPermission(act)) {
                mGoogleMap.isMyLocationEnabled = true
                mGoogleMap.setMaxZoomPreference(18f)
                showMyLocation()
            } else {
                MyPermission().requestLocationPermission(act)
            }
        }
    }

    private fun showMyLocation() {
        val criteria = Criteria()
        val location = presenter.locationManager.getLastKnownLocation(presenter.locationManager.getBestProvider(criteria, false))
        if (location != null) {
            val target = LatLng(location.latitude, location.longitude)
            val builder = CameraPosition.Builder()
            builder.zoom(18f)
            builder.target(target)
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()))
        }
    }

    override fun onLocationReceive(latLng: LatLng) {
        presenter.listOfMarkers.add(latLng)
        Log.e("location_demo","onLocationReceive")
        heatMapProvider = HeatmapTileProvider.Builder().data(presenter.listOfMarkers).build()
        heatMapProvider.setRadius(30)
        mGoogleMap.addTileOverlay(TileOverlayOptions().tileProvider(heatMapProvider))
    }

    override fun onNetworkReceive() {
        Log.e("location_demo","onNetworkReceive")
        presenter.startLocationListener(this)
    }

    override fun onNetworkGone() {
        presenter.stopLocationUpdates(this)
        Log.e("location_demo","onNetworkGone")
    }

    override fun onStop() {
        super.onStop()
//        Log.e("location_demo","unregisterReceiver")
//        unregisterReceiver(networkReceiver)
    }
}
