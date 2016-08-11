package com.awesomethings.whereiswifi.view

import android.content.IntentFilter
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
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import org.jetbrains.anko.act
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() , INetworkListener, IOnLocationReceive {

    private val BROADCAST = "android.net.conn.CONNECTIVITY_CHANGE"
    private lateinit var mGoogleMap : GoogleMap
    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var presenter : MainActivityPresenter
    private lateinit var heatMapProvider : HeatmapTileProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainActivityPresenter(this,this)

//        presenter.firebaseDB.addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(p0: DataSnapshot?) {
//                presenter.dbManager.deleteAllValues()
//                println(p0?.children.toString())
//                for (snapshot : DataSnapshot in p0!!.children){
//                    val json = JSONObject(snapshot.value.toString())
//                    val latitude = json.getDouble("latitude")
//                    val longitude = json.getDouble("longitude")
//                    val latLng = LatLng(latitude,longitude)
//                    val model = NetworkLocationModel(latLng)
//                    presenter.listOfMarkers.add(model)
//                }
//                presenter.dbManager.insert(presenter.listOfMarkers)
//            }
//
//            override fun onCancelled(p0: DatabaseError?) {
//
//            }
//        })
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
            mGoogleMap.isMyLocationEnabled = true
            if(presenter.getCoordinates().size > 0) {
                heatMapProvider = HeatmapTileProvider.Builder().data(presenter.getCoordinates()).build()
                mGoogleMap.addTileOverlay(TileOverlayOptions().tileProvider(heatMapProvider))
            }
            else {
                heatMapProvider = HeatmapTileProvider.Builder().weightedData(mutableListOf(WeightedLatLng(LatLng(0.0,0.0)))).build()
            }
        }
    }

    override fun onLocationReceive(latLng: LatLng) {
        val locationModel = NetworkLocationModel(latLng)
        presenter.listOfLatLng.add(latLng)
        locationModel.save()
        presenter.saveValues(presenter.dbManager.getCoordinates() as ArrayList<NetworkLocationModel>)

        heatMapProvider.setData(presenter.listOfLatLng)

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
    }
}
