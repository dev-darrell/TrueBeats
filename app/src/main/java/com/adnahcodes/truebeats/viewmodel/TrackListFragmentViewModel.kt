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
    private var tracksGottenFromApi: Boolean = false


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
            tracksGottenFromApi = true
        } else {
            Log.e(TrackListFragment.TAG,"getTracks(ViewModel): Error getting track list from API, see earlier log.") }
    }

    private fun saveTracksToDb() {
        GlobalScope.launch {
                trackListFromApi?.let { repository.insertTrackstoRoom(it) }
        }
    }

//    FIXME: Change the app's structure for accessing data such that getTracksFromDb() is only
//     called in code after it's proved verifiably that the api call has returned tracks.

    fun getTracksFromDb(): LiveData<List<Track>> {
//        Check that tracks have been gotten before trying to retrieve them from Room. This avoids
//        an error where the observed tracklist in TrackListFragment is empty.
//        Note: Making the thread sleep & calling this method recursively without the boolean changing currently causes an ANR.

        if (tracksGottenFromApi) {
            mutableDbTrackList.value = repository.getTracksFromRoom().value
        } else {
            Thread.sleep(500)
            getTracksFromDb()
        }
        return trackListFromDB
    }
}