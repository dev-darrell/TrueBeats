package com.adnahcodes.truebeats.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private var apiService: DeezerService? = null

    fun instantiateRetrofit(): DeezerService {
        if (apiService != null){
            return apiService as DeezerService
        } else {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.deezer.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val apiService = retrofit.create(DeezerService::class.java)
            return apiService as DeezerService
        }
    }
}