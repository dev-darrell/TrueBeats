package com.adnahcodes.truebeats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.PlaylistRecylerAdapter
import com.adnahcodes.truebeats.databinding.FragmentTrackListBinding
import com.adnahcodes.truebeats.viewmodel.TrackListViewModel

class TrackList : Fragment() {

    private var trackCount: Int = 0
    private var playlistId: Long = 0
    private lateinit var playlistTitle: String
    private lateinit var playlistDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mBinding: FragmentTrackListBinding = FragmentTrackListBinding.inflate(inflater,
            container, false)

        val mViewModel = ViewModelProvider(this).get(TrackListViewModel::class.java)
        val args: Bundle? = this.arguments
        args?.let {
            it.apply {
                playlistId = getLong(MainActivity.PLAYLIST_ID)
                trackCount = getInt(MainActivity.TRACK_COUNT)
                playlistTitle = getString(MainActivity.PLAYLIST_TITLE).toString()
                playlistDate = getString(MainActivity.PLAYLIST_DATE).toString()
            }
        }

        mViewModel.initializeRetrofit()
        mViewModel.getTracks(playlistId)

        mViewModel.requestSucceeded.observe(viewLifecycleOwner, Observer {
            mViewModel.requestSucceeded.observe(viewLifecycleOwner, Observer {
                if(it){
                    mViewModel.responseLiveData.observe(viewLifecycleOwner, Observer {response ->
                        if (response.isSuccessful){

//                            val adapter = response.body()?.data?.let { PlaylistRecylerAdapter(it, this@MainActivity) }
//
//                            mBinding.playlistRecycler.setHasFixedSize(true)
//                            mBinding.playlistRecycler.layoutManager = LinearLayoutManager(MainActivity())
//                            mBinding.playlistRecycler.adapter = adapter
                        }
                        else {
                            Toast.makeText(MainActivity(), getString(R.string.api_request_error), Toast.LENGTH_LONG).show()
                        }
                    })
                } else {
//                    mBinding.testTv.text = getString(R.string.api_call_failure)
//            Toast.makeText(, t.message, Toast.LENGTH_LONG).show()
                }
            })
        })
        return mBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        const val TAG = "TrackList"
    }
}