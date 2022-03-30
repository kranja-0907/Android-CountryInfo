package hr.algebra.countryinfo.model

data class Country(
    var _id: Long?,
    val name: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val population: Int,
    val area: Double,
    val numericCode: Int,
    val flag: String,
    val cioc: String,
    val independent: Boolean,
    var read: Boolean
)
