package com.eibrahim.chatbot.vsr

import com.eibrahim.chatbot.core.CONST_VALS
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiVsrClient {
    private const val BASE_URL =  "http://"+ CONST_VALS.IP_CONFIG +  ":8000/"

    val vsrApiService: VsrApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
        retrofit.create(VsrApiService::class.java)
    }
}
