package com.awesomethings.whereiswifi.interfaces

/**
 * Created by Master on 8/4/16.
 */
interface INetworkListener {
    fun onNetworkReceive()
    fun onNetworkGone()
}