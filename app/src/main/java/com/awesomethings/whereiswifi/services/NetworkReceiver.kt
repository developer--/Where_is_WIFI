package com.awesomethings.whereiswifi.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.awesomethings.whereiswifi.interfaces.INetworkListener

/**
 * Created by Master on 8/4/16.
 */
class NetworkReceiver : BroadcastReceiver {
    private lateinit var networkListener : INetworkListener

    constructor(networkListener : INetworkListener) {
        this.networkListener = networkListener
    }

    constructor()

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            if (intent?.extras != null) {
                val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val ni = connectivityManager.activeNetworkInfo
                if (ni != null && ni.state === NetworkInfo.State.CONNECTED) {
                    networkListener.onNetworkReceive()
                } else if (intent?.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, java.lang.Boolean.FALSE)!!) {
                    networkListener.onNetworkGone()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}