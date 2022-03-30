package hr.algebra.countryinfo.factory

import okio.Timeout
import java.net.HttpURLConnection
import java.net.URL

private const val TIMEOUT = 10000
private const val METHOD = "GET"
private const val USER_AGENT = "User-Agent"
private const val MOZZILA = "Mozilla/5.0"

fun createGetHttpUrlConnection(flag: String): HttpURLConnection {
    val url = URL(flag)
    return (url.openConnection() as HttpURLConnection).apply {
        connectTimeout = TIMEOUT
        readTimeout = TIMEOUT
        requestMethod = METHOD
        addRequestProperty(USER_AGENT, MOZZILA)
    }
}
