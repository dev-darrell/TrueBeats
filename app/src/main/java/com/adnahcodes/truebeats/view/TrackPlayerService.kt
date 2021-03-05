package com.adnahcodes.truebeats.view

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager


class TrackPlayerService : Service() {

    private lateinit var allTracksUrls: Array<String>
    private var selectedTrackPosition = 0
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var currentPlaylist: ArrayList<String>


//    TODO: Improve player so it can receive commands from a bluetooth headset or from device gestures.

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
        addNextTracks(selectedTrackUrl)
    }

//    TODO: Get list of tracks from Room db and add them to the playlist from there.

    private fun addNextTracks(selectedTrackUrl: String) {
        for (i in allTracksUrls){
            if (i != selectedTrackUrl){
                exoPlayer.addMediaItem(MediaItem.fromUri(i))
            } else {
                return
            }
            currentPlaylist.add(i)
        }
    }


    override fun onCreate() {
        super.onCreate()

        val playerNotificationManager = PlayerNotificationManager(this, CHANNEL_ID, NOTIFICATION_ID, DescriptionAdapter())
        exoPlayer = SimpleExoPlayer.Builder(this).build()
    }

//   TODO: Maintain access to list of tracks in current exoplayer playlist and use the current window index
//    in relation with that to get the title and additional info needed for the notification.

    private inner class DescriptionAdapter : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
//            val window = player.currentWindowIndex
//            currentPlaylist.get(window)
//            return getTitle(window)
            TODO("Not yet implemented")
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            TODO("Not yet implemented")
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            TODO("Not yet implemented")
        }

        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            TODO("Not yet implemented")
        }

    }


    inner class TrackPlayerServiceBinder : Binder() {

        fun getExoPlayerInstance() = exoPlayer
    }

    override fun onDestroy() {
        exoPlayer.release()
        super.onDestroy()
    }

    companion object {
        private const val CHANNEL_ID = "Media Playback Notification"
        private const val NOTIFICATION_ID = 1
    }
}
