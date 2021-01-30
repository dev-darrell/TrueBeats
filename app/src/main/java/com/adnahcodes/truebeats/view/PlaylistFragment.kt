package com.adnahcodes.truebeats.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.PlaylistRecyclerViewAdapter
import com.adnahcodes.truebeats.databinding.PlaylistFragmentBinding
import com.adnahcodes.truebeats.model.Playlist
import com.adnahcodes.truebeats.viewmodel.PlaylistFragmentViewModel

class PlaylistFragment : Fragment(), PlaylistRecyclerViewAdapter.PlaylistClickHandler {
    val mViewModel = PlaylistFragmentViewModel()
    lateinit var mBinding : PlaylistFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = PlaylistFragmentBinding.inflate(inflater, container, false)

        mViewModel.initializeRetrofit()

        if(context?.let { verifyConnection(it) } == true){
            mViewModel.getPlaylists()
        } else {
            mBinding.testTv.text = getString(R.string.verify_internet)
        }

        mViewModel.requestSucceeded.observe(viewLifecycleOwner, Observer {
            if(it){
                mViewModel.responseLiveData.observe(viewLifecycleOwner, Observer {response ->
                    if (response.isSuccessful){

                        val adapter = response.body()?.data?.let { PlaylistRecyclerViewAdapter(it, this) }

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
                Toast.makeText(context, mViewModel.errorMessage, Toast.LENGTH_LONG).show()
            }
        })

        return mBinding.root
    }

    override fun onPlaylistItemClick(playlist: Playlist) {
//        val args = Bundle()
//        args.putLong(MainActivity.PLAYLIST_ID, playlist.id)
//        args.putInt(MainActivity.TRACK_COUNT, playlist.trackCount)
//
//        val trackListFragment = Fragment()
//        trackListFragment.arguments = args

//        childFragmentManager.beginTransaction().add(trackListFragment,
//            MainActivity.TRACK_LIST_FRAGMENT)
//            .commit()

        val directions = PlaylistFragmentDirections.actionPlaylistFragmentToTrackList(playlist.id,
            playlist.trackCount)
        Navigation.findNavController(mBinding.root).navigate(directions)
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
}