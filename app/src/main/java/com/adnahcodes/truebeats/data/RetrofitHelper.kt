package com.adnahcodes.truebeats.data

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private var retrofit: Retrofit? = null

    fun instantiateRetrofit(): Retrofit {
        if (retrofit != null){
            return retrofit as Retrofit
        } else {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.deezer.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit as Retrofit
        }
    }
}