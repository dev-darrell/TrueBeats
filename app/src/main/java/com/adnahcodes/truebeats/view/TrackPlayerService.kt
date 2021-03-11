package com.adnahcodes.truebeats.view

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import com.adnahcodes.truebeats.model.Track
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerNotificationManager
import com.squareup.picasso.Picasso
import java.util.concurrent.Executor


class TrackPlayerService : Service() {

    private var allTracks: List<Track>? = null
    private var selectedTrackPosition = 0
    private lateinit var exoPlayer: SimpleExoPlayer
    private lateinit var playerNotificationManager: PlayerNotificationManager
    private lateinit var currentPlaylist: ArrayList<String>


//    TODO: Improve player so it can receive commands from a bluetooth headset or from device gestures.

    override fun onBind(intent: Intent?): IBinder {
        return TrackPlayerServiceBinder()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            allTracks = (it.getParcelableArrayExtra(TrackPlayerFragment.ALL_TRACKS) as? Array<*>)
                ?.filterIsInstance<Track>()
            selectedTrackPosition = it.getIntExtra(TrackPlayerFragment.TRACK_URL, 0)

            allTracks
            setupMedia()
        }
        return START_NOT_STICKY
    }

    private fun setupMedia() {
        val selectedTrack = allTracks?.get(selectedTrackPosition)
        val mediaItem = selectedTrack?.let{
            MediaItem.fromUri(selectedTrack.previewUrl) }
        exoPlayer.playWhenReady = true
        exoPlayer.setMediaItem(mediaItem!!)
        exoPlayer.prepare()
        addNextTracks(selectedTrack)
    }

//    TODO: Get list of tracks from Room db and add them to the playlist from there.

    private fun addNextTracks(selectedTrack: Track) {
        for (i in allTracks!!){
            if (i != selectedTrack){
                exoPlayer.addMediaItem(MediaItem.fromUri(i.previewUrl))
            } else {
                return
            }
        }
    }


    override fun onCreate() {
        super.onCreate()

        exoPlayer = SimpleExoPlayer.Builder(this).build()
        playerNotificationManager = PlayerNotificationManager(this, CHANNEL_ID, NOTIFICATION_ID, DescriptionAdapter())
        playerNotificationManager.setPlayer(exoPlayer)
    }

//   TODO: Maintain access to list of tracks in current exoplayer playlist and use the current window index
//    in relation with that to get the title and additional info needed for the notification.

    private inner class DescriptionAdapter : PlayerNotificationManager.MediaDescriptionAdapter {
        override fun getCurrentContentTitle(player: Player): CharSequence {
            val window = player.currentWindowIndex
            return allTracks?.get(window)!!.title
        }

        override fun createCurrentContentIntent(player: Player): PendingIntent? {
            val window = player.currentWindowIndex

            val notifyIntent = Intent(this@TrackPlayerService, TrackPlayerFragment::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            val notifyPendingIntent = PendingIntent.getActivity(this@TrackPlayerService,
                0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            return notifyPendingIntent
        }

        override fun getCurrentContentText(player: Player): CharSequence? {
            val window = player.currentWindowIndex
            return allTracks?.get(window)!!.artist.artistName
        }

//        FIXME: Error from Picasso while creating bitmap for notification
//         - get method must not be called on the main thread.
        override fun getCurrentLargeIcon(
            player: Player,
            callback: PlayerNotificationManager.BitmapCallback
        ): Bitmap? {
            val window = player.currentWindowIndex
            var largeIcon: Bitmap? = null

            val myExecutor = object: Executor {
                override fun execute(command: Runnable?) {
                    Thread(command).start()
                }
            }
            myExecutor.execute(Runnable { largeIcon = Picasso.get()
                .load(allTracks?.get(window)!!.album.albumPicture)
                .get()
            })

            return largeIcon
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
