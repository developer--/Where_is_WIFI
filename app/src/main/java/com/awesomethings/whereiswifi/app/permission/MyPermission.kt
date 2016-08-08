package com.awesomethings.whereiswifi.app.permission

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

class MyPermission {
    private val mActivity: Activity? = null
    private lateinit var permission : MyPermission

    fun checkCoarseLocationPermission(mActivity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        return permissionCheck == GRANTED
    }

    fun checkFineLocationPermission(mActivity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
        return permissionCheck == GRANTED
    }


    fun checkCameraPermission(mActivity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.CAMERA)
        return permissionCheck == GRANTED
    }

    fun checkReadStoragePermission(mActivity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        return permissionCheck == GRANTED
    }

    fun checkWriteStoragePermission(mActivity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return permissionCheck == GRANTED
    }


    fun requestCameraPermission(mActivity: Activity) {
        ActivityCompat.requestPermissions(mActivity,
                arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_CAMERA)
    }

    fun requestReadAndWritePermission(mActivity: Activity) {
        ActivityCompat.requestPermissions(mActivity,
                arrayOf<String>(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_CAMERA)
    }


    fun requestLocationPermission(mActivity: Activity) {
        ActivityCompat.requestPermissions(mActivity,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION)
    }


    fun checkNetworkStatePermission(mActivity: Activity): Boolean {
        val permissionCheck = ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_NETWORK_STATE)
        return permissionCheck == GRANTED
    }

    companion object {
        private var permissions: MyPermission? = null


        private val GRANTED = PackageManager.PERMISSION_GRANTED

        fun checkResults(grantedResults: IntArray): Boolean {
            var granted = true
            for (p in grantedResults) {
                if (p != PackageManager.PERMISSION_GRANTED) {
                    granted = false
                    break
                }
            }
            return granted
        }

        val MY_PERMISSIONS_REQUEST_LOCATION = 1
        val MY_PERMISSIONS_REQUEST_STORAGE = 2
        val MY_PERMISSIONS_REQUEST_CAMERA = 3
    }
}