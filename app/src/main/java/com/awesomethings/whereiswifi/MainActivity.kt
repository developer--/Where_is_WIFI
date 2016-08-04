package com.awesomethings.whereiswifi

import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.awesomethings.whereiswifi.services.NetworkListener

class MainActivity : AppCompatActivity() {
    private val BROADCAST = "android.net.conn.CONNECTIVITY_CHANGE"
    private lateinit var networkListener : NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startNetworkListenerBroadcast()
    }

    /**
     * ოფლაინში ატვირთვის სერვისის გააქტიურება
     */
    private fun startNetworkListenerBroadcast() {
        networkListener = NetworkListener()
        val intentFilter = IntentFilter(BROADCAST)
        registerReceiver(networkListener, intentFilter)
    }

}


