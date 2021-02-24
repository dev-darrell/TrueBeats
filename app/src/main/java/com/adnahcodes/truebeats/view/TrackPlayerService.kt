package com.adnahcodes.truebeats.view

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer


class TrackPlayerService : Service() {

    private lateinit var allTracksUrls: Array<String>
    private var selectedTrackPosition = 0
    private lateinit var exoPlayer: SimpleExoPlayer

    override fun onBind(intent: Intent?): IBinder {
        return TrackPlayerServiceBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            allTracksUrls = it.getStringArrayExtra(TrackPlayerFragment.ALL_TRACK_URLS) as Array<String>
            selectedTrackPosition = it.getIntExtra(TrackPlayerFragment.TRACK_URL, 0)

            setupMedia(allTracksUrls[selectedTrackPosition])
        }
        return START_NOT_STICKY
    }

    private fun setupMedia(selectedTrackUrl: String) {
        val mediaItem = MediaItem.fromUri(selectedTrackUrl)
        exoPlayer.playWhenReady = true
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        addNextTracks()
    }

    private fun addNextTracks() {
        for (i in selectedTrackPosition .. allTracksUrls.size -1){
            exoPlayer.addMediaItem(MediaItem.fromUri(allTracksUrls[i]))
        }
    }


    override fun onCreate() {
        super.onCreate()

        exoPlayer = SimpleExoPlayer.Builder(this).build()
    }


    inner class TrackPlayerServiceBinder : Binder() {

        fun getExoPlayerInstance() = exoPlayer
    }

    override fun onDestroy() {
        exoPlayer.release()
        super.onDestroy()
    }
}
