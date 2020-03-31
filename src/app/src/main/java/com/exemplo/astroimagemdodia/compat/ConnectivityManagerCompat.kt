@file:Suppress("DEPRECATION")

package com.exemplo.astroimagemdodia.compat

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class ConnectivityManagerCompat() {
    companion object {
        fun isConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.activeNetwork?.let {
                    val capabilities = connectivityManager.getNetworkCapabilities(it)
                    return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
                }
            } else {
                return connectivityManager.activeNetworkInfo?.isConnected == true
            }

            return false
        }
    }
}