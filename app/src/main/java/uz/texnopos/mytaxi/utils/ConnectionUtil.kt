package uz.texnopos.mytaxi.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import uz.texnopos.mytaxi.app.App

fun isConnected(): Boolean = App.instance.isAvailableNetwork()

@SuppressLint("MissingPermission")
private fun Context.isAvailableNetwork(): Boolean {
    var result = false
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        connectivityManager.activeNetwork ?: return false
    } else {
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.isConnected
    }
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    result = when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return result
}

