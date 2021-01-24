package com.adnahcodes.truebeats.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.PlaylistRecylerAdapter
import com.adnahcodes.truebeats.data.DeezerService
import com.adnahcodes.truebeats.data.RetrofitHelper
import com.adnahcodes.truebeats.model.ListOfPlaylists
import com.adnahcodes.truebeats.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivityViewModel : ViewModel() {

    private lateinit var apiService: DeezerService
    private val _responseLiveData: MutableLiveData<Response<ListOfPlaylists>> = MutableLiveData()
    val responseLiveData: LiveData<Response<ListOfPlaylists>> = _responseLiveData
    private val _requestSucceeded: MutableLiveData<Boolean> = MutableLiveData()
    val requestSucceeded: LiveData<Boolean> = _requestSucceeded

    fun initializeRetrofit() {
        val retrofit: Retrofit = RetrofitHelper.instantiateRetrofit()

        apiService = retrofit.create(DeezerService::class.java)
    }

    fun getPlaylists() {
        val apiCall = apiService.getPlaylists()

        apiCall.enqueue(object: Callback<ListOfPlaylists> {
            override fun onResponse(call: Call<ListOfPlaylists>, response: Response<ListOfPlaylists>) {
                _requestSucceeded.value = true
                _responseLiveData.value = response
            }

            override fun onFailure(call: Call<ListOfPlaylists>, t: Throwable) {
                Log.e(MainActivity.TAG, "onFailure: ${call.request().url()} and ${t.toString()}")
                _requestSucceeded.value = false
            }
        })
    }


}