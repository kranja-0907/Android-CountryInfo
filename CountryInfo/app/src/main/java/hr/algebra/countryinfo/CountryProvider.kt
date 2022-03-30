package hr.algebra.countryinfo

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.countryinfo.dao.CountryRepository
import hr.algebra.countryinfo.dao.getCountryRepository
import hr.algebra.countryinfo.model.Country
import java.lang.IllegalArgumentException

private const val AUTHORITY = "hr.algebra.countryinfo.api.provider"
private const val PATH = "countries"

val COUNTRY_PROVIDER_URI = Uri.parse("content://$AUTHORITY/$PATH")

private const val COUNTRIES = 10
private const val COUNTRY_ID = 20

private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)){
    addURI(AUTHORITY, PATH, COUNTRIES)
    addURI(AUTHORITY, "$PATH/#", COUNTRY_ID)
    this
}


class CountryProvider : ContentProvider() {

    private lateinit var countryRepository: CountryRepository

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)){
            COUNTRIES -> return countryRepository.delete(selection,selectionArgs)
            COUNTRY_ID -> {
                uri.lastPathSegment?.let{
                    return countryRepository.delete("${Country::_id.name}=?", arrayOf(it))
                }
            }
        }
        throw IllegalArgumentException("Wrong uri")
    }

    override fun getType(uri: Uri): String? {
        TODO(
            "Implement this to handle requests for the MIME type of the data" +
                    "at the given URI"
        )
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = countryRepository.insert(values)
        return ContentUris.withAppendedId(COUNTRY_PROVIDER_URI,id)
    }

    override fun onCreate(): Boolean {
        countryRepository = getCountryRepository(context)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = countryRepository.query(projection,selection,selectionArgs,sortOrder)

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)){
            COUNTRIES -> return countryRepository.update(values,selection,selectionArgs)
            COUNTRY_ID -> {
                uri.lastPathSegment?.let{
                    return countryRepository.update(values,"${Country::_id.name}=?", arrayOf(it))
                }
            }
        }
        throw IllegalArgumentException("Wrong uri")
    }
}