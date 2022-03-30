package hr.algebra.countryinfo.api

import com.google.gson.annotations.SerializedName

data class CountryItem(
    @SerializedName("flag") val flag : String,
    @SerializedName("cioc") val cioc : String,
    @SerializedName("independent") val independent : Boolean,
    @SerializedName("name") val name : String,
    @SerializedName("capital") val capital : String,
    @SerializedName("subregion") val subregion : String,
    @SerializedName("region") val region : String,
    @SerializedName("population") val population : Int,
    @SerializedName("area") val area : Double,
    @SerializedName("numericCode") val numericCode : Int
)
