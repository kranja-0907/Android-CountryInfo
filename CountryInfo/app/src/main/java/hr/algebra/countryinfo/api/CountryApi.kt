package hr.algebra.countryinfo.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://restcountries.com/v2/"

interface CountryApi {
    @GET("all?fields=name,capital,region,subregion,population,area,numericCode,cioc,independent,flag")
    fun fetchCountries() : Call<List<CountryItem>>
}