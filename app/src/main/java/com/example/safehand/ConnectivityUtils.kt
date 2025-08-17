package com.example.safehand

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

object ConnectivityUtils {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun hasInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            @Suppress("DEPRECATION")
            return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }


    fun hasSimReady(context: Context): Boolean {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.simState == TelephonyManager.SIM_STATE_READY
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun hasInternetOrSMS(context: Context): Boolean {
        return hasInternet(context) || hasSimReady(context)
    }
}

class ConnectivityChecker @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun hasInternet(): Boolean {
        return ConnectivityUtils.hasInternet(context)
    }

    fun hasSimReady(): Boolean {
        return ConnectivityUtils.hasSimReady(context)
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun hasInternetOrSMS(): Boolean {
        return ConnectivityUtils.hasInternetOrSMS(context)
    }

}