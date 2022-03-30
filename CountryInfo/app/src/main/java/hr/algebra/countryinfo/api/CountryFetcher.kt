package hr.algebra.countryinfo.api

import android.content.ClipData
import android.content.ContentValues
import android.content.Context
import android.util.Log
import hr.algebra.countryinfo.COUNTRY_PROVIDER_URI
import hr.algebra.countryinfo.CountryInfoReceiver
import hr.algebra.countryinfo.DATA_IMPORTED
import hr.algebra.countryinfo.framework.sendBroadcast
import hr.algebra.countryinfo.framework.setBooleanPreference
import hr.algebra.countryinfo.handler.downloadImageAndStore
import hr.algebra.countryinfo.model.Country
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountryFetcher(private val context: Context) {

    private lateinit var countryApi: CountryApi
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        countryApi = retrofit.create(CountryApi::class.java)
    }

    fun fetchItems(){
        val request = countryApi.fetchCountries()

        request.enqueue(object: Callback<List<CountryItem>>{
            override fun onResponse(
                call: Call<List<CountryItem>>,
                response: Response<List<CountryItem>>
            ) {
                //ako su podaci dosli uhvati countrye i pretvori u nase iteme
                response.body()?.let {
                    populateItems(it)
                }
            }

            override fun onFailure(call: Call<List<CountryItem>>, t: Throwable) {
                Log.d(javaClass.name,t.message,t)
            }

        })
    }

    private fun populateItems(countryItems: List<CountryItem>) {
        GlobalScope.launch {

            countryItems.forEach {
                val flag = downloadImageAndStore(
                    context,
                    it.flag,
                    it.name.replace(" ", "_")
                )
                val values = ContentValues().apply{
                    put(Country::name.name,it.name)
                    put(Country::capital.name,it.capital)
                    put(Country::region.name,it.region)
                    put(Country::subregion.name,it.subregion)
                    put(Country::population.name,it.population)
                    put(Country::area.name,it.area)
                    put(Country::numericCode.name,it.numericCode)
                    put(Country::flag.name,flag ?: "")
                    put(Country::cioc.name,it.cioc)
                    put(Country::independent.name,it.independent)
                    put(Country::read.name,false)
                }
                context.contentResolver.insert(COUNTRY_PROVIDER_URI, values)

            }
            context.setBooleanPreference(DATA_IMPORTED,true)
            context.sendBroadcast<CountryInfoReceiver>()
        }
    }
}