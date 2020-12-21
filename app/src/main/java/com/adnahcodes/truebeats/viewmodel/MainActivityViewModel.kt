package com.adnahcodes.truebeats.viewmodel

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel : ViewModel() {

    fun initializeRetrofit(): Retrofit {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.deezer.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

}