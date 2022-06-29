package com.vigneshkumarapps.githubusers.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    var retrofit : Retrofit? = null

    fun getClient() : Retrofit?{
        if (retrofit == null){
            var interceptor = HttpLoggingInterceptor ()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)


            val client: OkHttpClient = OkHttpClient.Builder()

                .callTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                //.addInterceptor(interceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
        return retrofit
    }
}