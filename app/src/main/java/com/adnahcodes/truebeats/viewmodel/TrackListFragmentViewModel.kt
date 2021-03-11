package com.adnahcodes.truebeats.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adnahcodes.truebeats.data.RepositoryImplementation
import com.adnahcodes.truebeats.data.database.RoomDB
import com.adnahcodes.truebeats.model.Track
import com.adnahcodes.truebeats.view.TrackListFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TrackListFragmentViewModel() : ViewModel() {

    private var trackListFromApi: List<Track>? = null
    private val mutableDbTrackList: MutableLiveData<List<Track>> = MutableLiveData()
    var trackListFromDB: LiveData<List<Track>> = mutableDbTrackList
    private lateinit var repository: RepositoryImplementation


    fun loadTracksFromApi(playlistId: Long, context: Context) {
        repository = RepositoryImplementation(RoomDB.getDbInstance(context))
        repository.getTracksFromApi(playlistId) {
            storeTracksFromApi(it)
        }
    }

    private fun storeTracksFromApi(trackList: List<Track>?) {
        trackListFromApi = trackList
        if (!trackListFromApi.isNullOrEmpty()) {
            saveTracksToDb()
        } else {
            Log.e(TrackListFragment.TAG,"getTracks(ViewModel): Error getting track list from API, see earlier log.") }
    }

    private fun saveTracksToDb() {
        GlobalScope.launch {
                trackListFromApi?.let { repository.insertTrackstoRoom(it) }
        }
    }

//    FIXME: Error accessing data from Room on a background thread using coroutines

    fun getTracksFromDb(): LiveData<List<Track>> {
        GlobalScope.launch {
                mutableDbTrackList.value = repository.getTracksFromRoom().value
        }
        return trackListFromDB
    }
}