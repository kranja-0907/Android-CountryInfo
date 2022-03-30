package hr.algebra.countryinfo.handler

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import hr.algebra.countryinfo.factory.createGetHttpUrlConnection
import java.io.File
import java.lang.Exception
import java.net.HttpURLConnection
import java.nio.file.Files
import java.nio.file.Paths


fun downloadImageAndStore(context: Context, flag: String, fileName: String): String? {
    //"https://upload.wikimedia.org/wikipedia/commons/5/5c/Flag_of_the_Taliban.svg
    val ext = flag.substring(flag.lastIndexOf(".")) //.svg
    val file: File = createFile(context,fileName,ext)
    try {

        val con: HttpURLConnection = createGetHttpUrlConnection(flag)
        Files.copy(con.inputStream, Paths.get(file.toURI()))
        return  file.absolutePath

    }catch (e: Exception){
        Log.e("DOWNLOAD IMAGE", e.message, e)
    }
    return null
}


fun createFile(context: Context, fileName: String, ext: String): File {
    val dir = context.applicationContext.getExternalFilesDir(null)
    val file = File(dir, File.separator + fileName + ext)
    if (file.exists()){
        file.delete()
    }
    return file
}
