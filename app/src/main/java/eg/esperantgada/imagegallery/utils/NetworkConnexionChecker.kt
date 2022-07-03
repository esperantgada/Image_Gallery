@file:Suppress("DEPRECATION")

package eg.esperantgada.imagegallery.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_ETHERNET
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities.*
import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE

/**
 * Method that check internet connection availability for the device
 */

 fun isInternetAvailable(context: Context) : Boolean{
    val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapibilities = connectivityManager
            .getNetworkCapabilities(activeNetwork)?: return false

        return when{
            networkCapibilities.hasTransport(TRANSPORT_WIFI) -> true
            networkCapibilities.hasTransport(TRANSPORT_CELLULAR) -> true
            networkCapibilities.hasTransport(TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }else{
        connectivityManager.activeNetworkInfo?.run {
            return when(type){
                TYPE_WIFI -> true
                TYPE_MOBILE -> true
                TYPE_ETHERNET -> true
                else -> false
            }
        }
    }
    return false
}