package hr.algebra.countryinfo.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import hr.algebra.countryinfo.model.Country

private const val DB_NAME = "countries.db"
private const val DB_VERSION = 1
private const val COUNTRIES = "countries"

private const val DROP = "drop table $COUNTRIES"
private val CREATE = "create table $COUNTRIES(" +
        "${Country::_id.name} integer primary key autoincrement," +
        "${Country::name.name} text not null," +
        "${Country::capital.name} text not null," +
        "${Country::region.name} text not null," +
        "${Country::subregion.name} text not null," +
        "${Country::population.name} integer not null," +
        "${Country::area.name} double not null," +
        "${Country::numericCode.name} integer not null," +
        "${Country::flag.name} text not null," +
        "${Country::cioc.name} text not null," +
        "${Country::independent.name} integer not null," +
        "${Country::read.name} integer not null" +
        ")"

class CountrySqlHelper(
    context: Context?)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), CountryRepository {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?)
        = writableDatabase.delete(COUNTRIES,selection,selectionArgs)

    override fun update(
        values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = writableDatabase.update(COUNTRIES,values,selection,selectionArgs)

    override fun insert(values: ContentValues?)
        = writableDatabase.insert(COUNTRIES,null,values)

    override fun query(
        projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ) = readableDatabase.query(
        COUNTRIES,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder)

}