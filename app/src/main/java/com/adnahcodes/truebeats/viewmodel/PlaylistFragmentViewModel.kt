package com.adnahcodes.truebeats.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adnahcodes.truebeats.data.api.DeezerService
import com.adnahcodes.truebeats.data.api.RetrofitHelper
import com.adnahcodes.truebeats.model.ListOfPlaylists
import com.adnahcodes.truebeats.view.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistFragmentViewModel : ViewModel() {

    private lateinit var apiService: DeezerService
    private val _responseLiveData: MutableLiveData<Response<ListOfPlaylists>> = MutableLiveData()
    val responseLiveData: LiveData<Response<ListOfPlaylists>> = _responseLiveData
    private val _requestSucceeded: MutableLiveData<Boolean> = MutableLiveData()
    val requestSucceeded: LiveData<Boolean> = _requestSucceeded
    var errorMessage : String? = null

    fun initializeRetrofit() {
        apiService = RetrofitHelper.instantiateRetrofit()
    }

//    TODO: Move data access method from viewmodel to repository class

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
                errorMessage = t.message
            }
        })
    }


}