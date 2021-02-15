package com.adnahcodes.truebeats.view

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.navArgs
import com.adnahcodes.truebeats.R
import com.adnahcodes.truebeats.databinding.FragmentTrackPlayerBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer


class TrackPlayerFragment : Fragment() {

    lateinit var binding: FragmentTrackPlayerBinding
    val args: TrackPlayerFragmentArgs by navArgs()
    var previewUrl: String = ""
    lateinit var player: SimpleExoPlayer

//    TODO: Move music playing functionality to service and implement moving from one song to the next
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrackPlayerBinding.inflate(inflater, container, false)

        previewUrl = args.trackPreviewUrl

//        val serviceIntent = Intent(context, TrackPlayerService::class.java)
//        serviceIntent.putExtra(PREVIEW_URL, previewUrl)
//        context?.startService(serviceIntent)

        startPlayer()
        return binding.root
    }

    private fun startPlayer() {
        player = SimpleExoPlayer.Builder(this.requireActivity()).build()
        binding.exoplayerControlView.player = player

        val mediaItem = MediaItem.fromUri(previewUrl)
        player.setMediaItem(mediaItem)
        player.prepare()

        player.play()
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }

    companion object {
        const val PREVIEW_URL = "com.adnahcodes.truebeats.view.TrackPlayerFragment.previewUrl"
    }
}