package com.adnahcodes.truebeats.data

import androidx.lifecycle.LiveData
import com.adnahcodes.truebeats.model.Track

interface Repository {

    suspend fun getTracksFromRoom(): LiveData<List<Track>>

    suspend fun insertTrackstoRoom(trackList: List<Track>)

    suspend fun updateRoomTracks(trackList: List<Track>)

    suspend fun deleteRoomTracks(trackList: List<Track>)

    fun getTracksFromApi(playlistId: Long, onSuccess: (trackList: List<Track>?) -> Unit)
}