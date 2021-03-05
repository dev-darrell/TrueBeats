package com.adnahcodes.truebeats.data

import androidx.lifecycle.LiveData
import com.adnahcodes.truebeats.model.Track

interface Repository {

    fun getTracksFromRoom(): LiveData<List<Track>>

    fun insertTrackstoRoom(trackList: List<Track>)

    fun updateRoomTracks(trackList: List<Track>)

    fun getTracksFromApi(playlistId: Long): List<Track>?
}