package hr.algebra.countryinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.algebra.countryinfo.framework.startActivity

class CountryInfoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startActivity<HostActivity>()
    }
}