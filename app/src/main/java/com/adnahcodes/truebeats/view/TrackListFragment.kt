package com.adnahcodes.truebeats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.TrackRecyclerAdapter
import com.adnahcodes.truebeats.databinding.FragmentTrackListBinding
import com.adnahcodes.truebeats.model.Track
import com.adnahcodes.truebeats.viewmodel.TrackListFragmentViewModel
import java.util.*

class TrackListFragment : Fragment(), TrackRecyclerAdapter.MyViewHolder.TrackClickHandler {

    lateinit var mBinding: FragmentTrackListBinding
    val args: TrackListFragmentArgs by navArgs()
    private var trackCount: Int = 0
    private var playlistId: Long = 0
    private lateinit var adapter: TrackRecyclerAdapter
    private lateinit var allTracksUrls: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTrackListBinding.inflate(inflater,
            container, false)

        playlistId = args.playlistId
        trackCount = args.trackCount

        val mViewModel = ViewModelProvider(this).get(TrackListFragmentViewModel::class.java)
        mViewModel.initializeRetrofit()
        mViewModel.getTracks(playlistId)

        mViewModel.requestSucceeded.observe(viewLifecycleOwner, Observer { requestSucceeded ->
            if (requestSucceeded) {
                mViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->
                    if (response.isSuccessful) {

//      TODO: Restructure app to only pass tracks Url list between classes and get full track info from that
                        response.body()?.data?.apply {
                            adapter = TrackRecyclerAdapter(this, this@TrackListFragment)
                            allTracksUrls = this.map(Track::previewUrl).toTypedArray()
                        }

                        mBinding.trackRecyclerView.setHasFixedSize(true)
                        mBinding.trackRecyclerView.layoutManager = LinearLayoutManager(context)
                        mBinding.trackRecyclerView.adapter = adapter
                    } else {
                        Toast.makeText(context, getString(R.string.api_request_error), Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(context, mViewModel.errorMessage, Toast.LENGTH_LONG).show()
            }
        })
        return mBinding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onTrackItemClick(track: Track, position: Int) {
        if (track.isTrackReadable){
            val directions = TrackListFragmentDirections.actionTrackListToTrackPlayerFragment(allTracksUrls, position)
            Navigation.findNavController(mBinding.root).navigate(directions)
        }
    }

    companion object {
        const val TAG = "TrackList"
    }
}