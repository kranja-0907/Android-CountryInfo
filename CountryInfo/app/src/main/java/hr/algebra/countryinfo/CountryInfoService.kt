package hr.algebra.countryinfo

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import hr.algebra.countryinfo.api.CountryFetcher
import hr.algebra.countryinfo.framework.sendBroadcast
import hr.algebra.countryinfo.framework.setBooleanPreference

const val JOB_ID = 1

class CountryInfoService : JobIntentService() {
    //background method
    override fun onHandleWork(intent: Intent) {

        CountryFetcher(this).fetchItems()
    }

    companion object{
        fun equeue(context: Context, intent: Intent){
            enqueueWork(context, CountryInfoService::class.java, JOB_ID, intent)
        }
    }

}