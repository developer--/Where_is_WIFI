package com.awesomethings.whereiswifi.view

import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.awesomethings.whereiswifi.R
import com.awesomethings.whereiswifi.app.MyApplication
import com.awesomethings.whereiswifi.app.permission.MyPermission
import com.awesomethings.whereiswifi.interfaces.INetworkListener
import com.awesomethings.whereiswifi.interfaces.IOnLocationReceive
import com.awesomethings.whereiswifi.presenter.MainActivityPresenter
import com.awesomethings.whereiswifi.services.NetworkReceiver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.act

class MainActivity : AppCompatActivity() , INetworkListener, IOnLocationReceive {

    private val BROADCAST = "android.net.conn.CONNECTIVITY_CHANGE"

    private lateinit var mGoogleMap : GoogleMap

    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var presenter : MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.graph.inject(this)
        presenter = MainActivityPresenter(this)
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

    private fun setCameraPosition(latLng: LatLng, zoom: Float) {
        val cameraPosition = CameraPosition.Builder().target(latLng).zoom(zoom).build()
        mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onResume() {
        super.onResume()
        Log.e("location_demo","register reciver")
        startNetworkListenerBroadcast()
        getMapFragment().getMapAsync { googleMap ->
            mGoogleMap = googleMap
            if (MyPermission().checkCoarseLocationPermission(act) && MyPermission().checkCoarseLocationPermission(act)) {
                mGoogleMap.isMyLocationEnabled = true
                setCameraPosition(LatLng(42.014025, 43.876297), 13f)
            } else {
                MyPermission().requestLocationPermission(act)
            }
        }
    }


    override fun onLocationReceive(latLng: LatLng) {
        val markerOptions = MarkerOptions()
                .position(latLng)
        Log.e("location_demo","onLocationReceive")
        mGoogleMap.addMarker(markerOptions)
    }

    override fun onNetworkReceive() {
        Log.e("location_demo","onNetworkReceive")
        presenter.startLocationListener(this)
//        networkStateTextView_ID.text = "network receive"
        println("onNetworkReceive")
    }

    override fun onNetworkGone() {
//        presenter.stopLocationUpdates(this)
        Log.e("location_demo","onNetworkGone")
    }

    override fun onStop() {
        super.onStop()
        Log.e("location_demo","unregisterReceiver")
        unregisterReceiver(networkReceiver)
    }
}
