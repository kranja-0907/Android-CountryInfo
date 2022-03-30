package hr.algebra.countryinfo.dao

import android.content.Context

fun getCountryRepository(context: Context?) = CountrySqlHelper(context)