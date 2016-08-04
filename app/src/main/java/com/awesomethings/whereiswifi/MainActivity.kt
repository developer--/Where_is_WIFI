package com.awesomethings.whereiswifi

import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.awesomethings.whereiswifi.interfaces.INetworkListener
import com.awesomethings.whereiswifi.services.NetworkReceiver
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , INetworkListener{

    private val BROADCAST = "android.net.conn.CONNECTIVITY_CHANGE"
    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNetworkListenerBroadcast()
    }

    /**
     * ოფლაინში ატვირთვის სერვისის გააქტიურება
     */
    private fun startNetworkListenerBroadcast() {
        networkReceiver = NetworkReceiver(this)
        val intentFilter = IntentFilter(BROADCAST)
        registerReceiver(networkReceiver, intentFilter)
    }

    override fun onNetworkReceive() {
        networkStateTextView_ID.text = "network receive"
    }

    override fun onNetworkGone() {
        networkStateTextView_ID.text = "network gone"
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkReceiver)
    }
}
