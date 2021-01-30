package com.adnahcodes.truebeats.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.adapter.TrackRecyclerAdapter
import com.adnahcodes.truebeats.databinding.FragmentTrackListBinding
import com.adnahcodes.truebeats.model.Track
import com.adnahcodes.truebeats.viewmodel.TrackListFragmentViewModel

class TrackListFragment : Fragment(), TrackRecyclerAdapter.MyViewHolder.TrackClickHandler {

    val args: TrackListFragmentArgs by navArgs()
    private var trackCount: Int = 0
    private var playlistId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mBinding: FragmentTrackListBinding = FragmentTrackListBinding.inflate(inflater,
            container, false)

        val mViewModel = ViewModelProvider(this).get(TrackListFragmentViewModel::class.java)

        playlistId = args.playlistId
        trackCount = args.trackCount

        mViewModel.initializeRetrofit()
        mViewModel.getTracks(playlistId)

        mViewModel.requestSucceeded.observe(viewLifecycleOwner, Observer { requestSucceeded ->
            if (requestSucceeded) {
                mViewModel.responseLiveData.observe(viewLifecycleOwner, Observer { response ->
                    if (response.isSuccessful) {

                        val adapter = response.body()?.data?.let {trackList ->
                            TrackRecyclerAdapter(trackList, this)
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

    override fun onTrackItemClick(track: Track) {
        if (track.isTrackReadable){

        }
    }

    companion object {
        const val TAG = "TrackList"
    }
}