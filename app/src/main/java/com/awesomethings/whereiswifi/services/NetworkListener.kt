package com.awesomethings.whereiswifi.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

/**
 * Created by Master on 8/4/16.
 */
class NetworkListener : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            if (intent?.extras != null) {
                val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val ni = connectivityManager.activeNetworkInfo

                if (ni != null && ni.state === NetworkInfo.State.CONNECTED) {
                    Log.e("MyNetworkReceiver", "wifi is avialable")
                } else if (intent?.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, java.lang.Boolean.FALSE)!!) {
                    Log.e("MyNetworkReceiver", "There's no network connectivity")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}