package com.adnahcodes.truebeats.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.PlaylistRecylerAdapter
import com.adnahcodes.truebeats.data.DeezerService
import com.adnahcodes.truebeats.databinding.ActivityMainBinding
import com.adnahcodes.truebeats.model.ListOfPlaylists
import com.adnahcodes.truebeats.viewmodel.MainActivityViewModel
import retrofit2.*

class MainActivity : AppCompatActivity() {
    val mViewModel = MainActivityViewModel()
    lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
//        mBinding.tablayout.selectTab(mBinding.tablayout.getTabAt(1))

        val retrofit: Retrofit = mViewModel.initializeRetrofit()
        val apiService: DeezerService = retrofit.create(DeezerService::class.java)

        if(verifyConnection(this)){
            getPlaylists(apiService)
        } else {
            mBinding.testTv.text = getString(R.string.verify_internet)
        }
}

    private fun verifyConnection(context: Context): Boolean {
//        TODO: Review code to move away from using deprecated NetworkInfo class.

        val connectivity: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivity.activeNetworkInfo
        var result = false
        if (activeNetwork != null){
            result = activeNetwork.isConnectedOrConnecting
        }
        return result
    }

    private fun getPlaylists(apiService: DeezerService) {
    val apiCall = apiService.getPlaylists()

    apiCall.enqueue(object: Callback<ListOfPlaylists> {
        override fun onResponse(call: Call<ListOfPlaylists>, response: Response<ListOfPlaylists>) {
            if (response.isSuccessful){

                val adapter = response.body()?.data?.let { PlaylistRecylerAdapter(it) }

                mBinding.playlistRecycler.setHasFixedSize(true)
                mBinding.playlistRecycler.layoutManager = LinearLayoutManager(MainActivity())
                mBinding.playlistRecycler.adapter = adapter
            }
            else {
                Toast.makeText(MainActivity(), getString(R.string.api_request_error), Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<ListOfPlaylists>, t: Throwable) {
            Log.e(Companion.TAG, "onFailure: ${call.request().url()} and ${t.toString()}")
            mBinding.testTv.text = "Error!!"
//            Toast.makeText(, t.message, Toast.LENGTH_LONG).show()
        }
    })
}

    companion object {
        private const val TAG = "MainActivity"
    }
}