package com.adnahcodes.truebeats.view

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.content.Context
import android.media.browse.MediaBrowser
import android.os.IBinder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer


class TrackPlayerService : Service() {

    private var previewUrl: String? = ""
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        previewUrl = intent?.getStringExtra(TrackPlayerFragment.PREVIEW_URL)
        startPlayer()
        return START_NOT_STICKY
    }

    private fun startPlayer() {
//        val player = SimpleExoPlayer.Builder(this).build()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}