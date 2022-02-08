package com.solucionestpvpos.themoviesegc.util

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.M)
fun isNetworkAvailable(activity: Activity): Boolean {
    val connectivityManager = activity.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    val activeNetwork = connectivityManager.activeNetwork

    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

    return networkCapabilities != null &&
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

fun exchangeView(viewToVisible: View, viewToGone: View) {
    viewToVisible.visibility = View.VISIBLE
    viewToGone.visibility = View.GONE
}


fun exchangeViewShimmerVisible(viewToVisible: View) {
    viewToVisible.visibility = View.VISIBLE
}




fun exchangeViewShimmerGone( viewToGone: View) {
    viewToGone.visibility = View.GONE
}




fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}