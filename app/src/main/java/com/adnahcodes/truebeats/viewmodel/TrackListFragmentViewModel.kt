package com.adnahcodes.truebeats.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.adnahcodes.truebeats.data.RepositoryImplementation
import com.adnahcodes.truebeats.data.database.RoomDB
import com.adnahcodes.truebeats.model.Track
import com.adnahcodes.truebeats.view.TrackListFragment.Companion.TAG

class TrackListFragmentViewModel : ViewModel() {

    private var trackListFromApi: List<Track>? = null
    private lateinit var trackListFromDB: LiveData<List<Track>>
    private lateinit var repository: RepositoryImplementation


    fun getTracks(playlistId: Long, context: Context) {
        repository = RepositoryImplementation(RoomDB.getDbInstance(context))
        trackListFromApi = repository.getTracksFromApi(playlistId)
        if (trackListFromApi != null){
            saveTracksToDb()
        } else {
            Log.e(TAG, "getTracks(ViewModel): Error getting track list from API, see earlier log.")
        }
    }

    private fun saveTracksToDb() {
        repository.insertTrackstoRoom(trackListFromApi!!)
    }

    private fun getTracksFromDb(): LiveData<List<Track>> {
        trackListFromDB = repository.getTracksFromRoom()
        return trackListFromDB
    }
}