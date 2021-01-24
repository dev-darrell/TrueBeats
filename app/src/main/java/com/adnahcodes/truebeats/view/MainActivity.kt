package com.adnahcodes.truebeats.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.PlaylistRecylerAdapter
import com.adnahcodes.truebeats.databinding.ActivityMainBinding
import com.adnahcodes.truebeats.model.Playlist
import com.adnahcodes.truebeats.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*

class MainActivity : AppCompatActivity(), PlaylistRecylerAdapter.PlaylistClickHandler {
    val mViewModel = MainActivityViewModel()
    lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
//        mBinding.tablayout.selectTab(mBinding.tablayout.getTabAt(1))

        supportActionBar?.hide()

        mViewModel.initializeRetrofit()

        if(verifyConnection(this)){
            mViewModel.getPlaylists()
        } else {
            mBinding.testTv.text = getString(R.string.verify_internet)
        }


        mViewModel.requestSucceeded.observe(this, Observer {
            if(it){
                 mViewModel.responseLiveData.observe(this@MainActivity, Observer {response ->
                     if (response.isSuccessful){

                         val adapter = response.body()?.data?.let { PlaylistRecylerAdapter(it, this@MainActivity) }

                         mBinding.playlistRecycler.setHasFixedSize(true)
                         mBinding.playlistRecycler.layoutManager = LinearLayoutManager(MainActivity())
                         mBinding.playlistRecycler.adapter = adapter
                     }
                     else {
                         Toast.makeText(MainActivity(), getString(R.string.api_request_error), Toast.LENGTH_LONG).show()
                     }
                 })
            } else {
                mBinding.testTv.text = getString(R.string.api_call_failure)
//            Toast.makeText(, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPlaylistItemClick(playlist: Playlist) {
        val args = Bundle()
        args.putString(PLAYLIST_TITLE, playlist.title)
        args.putString(PLAYLIST_DATE, playlist.dateOfCreation)
        args.putLong(PLAYLIST_ID, playlist.id)
        args.putInt(TRACK_COUNT, playlist.trackCount)

        val trackListFragment = Fragment()
        trackListFragment.arguments = args

        supportFragmentManager.beginTransaction().add(trackListFragment, TRACK_LIST_FRAGMENT)
            .commit()
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

    companion object {
        const val TAG = "MainActivity"
        const val TRACK_LIST_FRAGMENT = "track_list_fragment"
        const val PLAYLIST_ID = "com.adnahcodes.truebeats.view.playlistId"
        const val PLAYLIST_TITLE = "com.adnahcodes.truebeats.view.playlistTitle"
        const val PLAYLIST_DATE = "com.adnahcodes.truebeats.view.playlistDate"
        const val TRACK_COUNT = "com.adnahcodes.truebeats.view.trackCount"
    }
}