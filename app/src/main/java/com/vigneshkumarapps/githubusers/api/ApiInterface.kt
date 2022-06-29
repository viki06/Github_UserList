package com.vigneshkumarapps.githubusers.api


import com.google.gson.JsonArray
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiInterface {

    @GET
    fun serviceMethod(@Url url: String) : Call<JsonArray>

    @GET
    fun serviceMethodDetail(@Url url: String) : Call<JsonObject>
}