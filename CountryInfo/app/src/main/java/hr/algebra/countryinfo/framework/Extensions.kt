package hr.algebra.countryinfo.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.getSystemService
import androidx.preference.PreferenceManager
import hr.algebra.countryinfo.COUNTRY_PROVIDER_URI
import hr.algebra.countryinfo.HostActivity
import hr.algebra.countryinfo.model.Country

fun View.startAnimation(animationId: Int)
    = startAnimation(AnimationUtils.loadAnimation(context, animationId))


inline fun<reified T : Activity> Context.startActivity()
        = startActivity(Intent(this, T::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
})

inline fun<reified  T: BroadcastReceiver> Context.sendBroadcast()
    =  sendBroadcast(Intent(this,T::class.java))



fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key,false)

fun Context.setBooleanPreference(key: String, value: Boolean) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit()
        .putBoolean(key,value)
        .apply()


fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let{ network ->
        connectivityManager.getNetworkCapabilities(network)?.let{ networkCapabilities ->
            return  networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }
    }
    return false
}

fun callDelayed(delay: Long, function: Runnable){
    Handler(Looper.getMainLooper()).postDelayed(
        function,
        delay
    )
}

fun Context.fetchCountries() : MutableList<Country> {
    val countries = mutableListOf<Country>()
    val cursor = contentResolver?.query(COUNTRY_PROVIDER_URI,
    null,
    null,
    null,
    null)

    while (cursor != null && cursor.moveToNext()) {
        countries.add(Country(
            cursor.getLong(cursor.getColumnIndexOrThrow(Country::_id.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::name.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::capital.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::region.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::subregion.name)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Country::population.name)),
            cursor.getDouble(cursor.getColumnIndexOrThrow(Country::area.name)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Country::numericCode.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::flag.name)),
            cursor.getString(cursor.getColumnIndexOrThrow(Country::cioc.name)),
            cursor.getInt(cursor.getColumnIndexOrThrow(Country::independent.name)) == 1,
            cursor.getInt(cursor.getColumnIndexOrThrow(Country::read.name)) == 1,
        ))
    }

    return countries
}


