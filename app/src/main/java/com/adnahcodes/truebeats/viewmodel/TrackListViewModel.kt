package com.adnahcodes.truebeats.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adnahcodes.truebeats.data.DeezerService
import com.adnahcodes.truebeats.data.RetrofitHelper
import com.adnahcodes.truebeats.model.ListOfTracks
import com.adnahcodes.truebeats.view.MainActivity
import com.adnahcodes.truebeats.view.TrackList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class TrackListViewModel : ViewModel() {

    private lateinit var apiService: DeezerService
    private val _responseLiveData: MutableLiveData<Response<ListOfTracks>> = MutableLiveData()
    val responseLiveData: LiveData<Response<ListOfTracks>> = _responseLiveData
    private val _requestSucceeded: MutableLiveData<Boolean> = MutableLiveData()
    val requestSucceeded: LiveData<Boolean> = _requestSucceeded

    fun initializeRetrofit() {
        val retrofit: Retrofit = RetrofitHelper.instantiateRetrofit()

        apiService = retrofit.create(DeezerService::class.java)
    }

    fun getTracks(playlistId: Long) {
        val apiCall = apiService.getPlaylistTracks(playlistId)

        apiCall.enqueue(object: Callback<ListOfTracks> {
            override fun onResponse(call: Call<ListOfTracks>, response: Response<ListOfTracks>) {
                _requestSucceeded.value = true
                _responseLiveData.value = response
            }

            override fun onFailure(call: Call<ListOfTracks>, t: Throwable) {
                Log.e(TrackList.TAG , "onFailure: ${call.request().url()} and ${t.toString()}")
                _requestSucceeded.value = false
            }
        })
    }
}