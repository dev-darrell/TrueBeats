package com.adnahcodes.truebeats.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.adnahcodes.truebeats.databinding.FragmentTrackPlayerBinding
import com.google.android.exoplayer2.SimpleExoPlayer


class TrackPlayerFragment : Fragment() {

    lateinit var binding: FragmentTrackPlayerBinding
    private val args: TrackPlayerFragmentArgs by navArgs()
    private lateinit var allTrackUrls: Array<String>
    private var currentTrackPosition: Int = 0

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if(service is TrackPlayerService.TrackPlayerServiceBinder) {
                binding.exoplayerControlView.player = service.getExoPlayerInstance()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

//    TODO: Implement moving from one song to the next
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    // Inflate the layout for this fragment
        binding = FragmentTrackPlayerBinding.inflate(inflater, container, false)

        allTrackUrls = args.listOfTracksUrls
        currentTrackPosition = args.trackPosition

        val serviceIntent = Intent(context, TrackPlayerService::class.java)
        serviceIntent.putExtra(ALL_TRACK_URLS, allTrackUrls)
        serviceIntent.putExtra(TRACK_URL, currentTrackPosition)

        context?.startService(serviceIntent)
        context?.bindService(Intent(context, TrackPlayerService::class.java), connection, 0)

        return binding.root
    }

    companion object {
        const val TRACK_URL = "com.adnahcodes.truebeats.view.TrackPlayerFragment.trackUrl"
        const val ALL_TRACK_URLS = "com.adnahcodes.truebeats.view.TrackPlayerFragment.allTrackUrls"
    }
}