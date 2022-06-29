package com.vigneshkumarapps.githubusers

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.vigneshkumarapps.githubusers.api.ApiInterface
import com.vigneshkumarapps.githubusers.api.RetrofitClient
import java.util.concurrent.TimeoutException

class CommonMethods {


    fun serviceMethodUserList(string: String): JsonArray? {
        val apiInterface: ApiInterface? = RetrofitClient().getClient()?.create(ApiInterface::class.java)
        val callback = apiInterface!!.serviceMethod(string)
        var s : JsonArray? = null
        try {
            val response = callback.execute()
            s = response.body()!!
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        catch (te: TimeoutException){
            te.printStackTrace()
        }
        return s
    }

    fun serviceMethodUserDetails(string: String): JsonObject? {
        val apiInterface: ApiInterface? = RetrofitClient().getClient()?.create(ApiInterface::class.java)
        val callback = apiInterface!!.serviceMethodDetail(string)
        var s : JsonObject? = null
        try {
            val response = callback.execute()
            s = response.body()!!
        }
        catch (e: Exception){
            e.printStackTrace()
        }
        catch (te: TimeoutException){
            te.printStackTrace()
        }
        return s
    }

    fun nullHandleString(string: String?): String {
        if(string == null){
            return ""
        }else{
            return string
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun alertDialog(context: Context, msg: String){
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(false)
        alertDialog.setTitle("Alert")
        alertDialog.setMessage(msg)
        alertDialog.setPositiveButton("Ok") { dialog, which -> dialog.dismiss() }
        val alertDialog1 = alertDialog.create()
        alertDialog1.show()
    }
}