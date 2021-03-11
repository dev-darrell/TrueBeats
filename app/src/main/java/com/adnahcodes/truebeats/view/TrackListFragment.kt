package com.adnahcodes.truebeats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.adnahcodes.truebeats.adapter.TrackRecyclerAdapter
import com.adnahcodes.truebeats.databinding.FragmentTrackListBinding
import com.adnahcodes.truebeats.model.Track
import com.adnahcodes.truebeats.viewmodel.TrackListFragmentViewModel

class TrackListFragment : Fragment(), TrackRecyclerAdapter.MyViewHolder.TrackClickHandler {

//    FIXME: TrackListFragment becomes unresponsive immediately after showing list of tracks

    lateinit var mBinding: FragmentTrackListBinding
    val args: TrackListFragmentArgs by navArgs()
    private var trackCount: Int = 0
    private var playlistId: Long = 0
    private lateinit var adapter: TrackRecyclerAdapter
    private lateinit var allTracks: Array<Track>
    private lateinit var allTracksUrls: Array<String>
    private val viewmodel: TrackListFragmentViewModel = TrackListFragmentViewModel()

//    TODO: Migrate this fragment to access tracks from the Room db through its viewmodel class.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playlistId = args.playlistId
        trackCount = args.trackCount

        viewmodel.loadTracksFromApi(playlistId, requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentTrackListBinding.inflate(inflater,
            container, false)

//        val mViewModel = ViewModelProvider(this).get(TrackListFragmentViewModel::class.java)
        getTracks()
        return mBinding.root;
    }


    private fun getTracks() {
        val tracks = viewmodel.getTracksFromDb()
        tracks.observe(viewLifecycleOwner, Observer { trackList ->
            allTracks = trackList.toTypedArray()
            adapter = TrackRecyclerAdapter(allTracks, this)
            mBinding.trackRecyclerView.adapter = adapter
        })
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
//        allTracksUrls = allTracks.map(Track::previewUrl).toTypedArray()

        mBinding.trackRecyclerView.setHasFixedSize(true)
        mBinding.trackRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun loadTracks() {
//        viewmodel.loadTracksFromApi(playlistId, requireContext().applicationContext)
//        viewmodel.getTracksFromDb()

    }

    override fun onTrackItemClick(track: Track, position: Int) {
            val directions = TrackListFragmentDirections.actionTrackListToTrackPlayerFragment(position, allTracks)
            Navigation.findNavController(mBinding.root).navigate(directions)
    }

    companion object {
        const val TAG = "TrackList"
    }
}