package com.adnahcodes.truebeats.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.adnahcodes.truebeats.data.api.RetrofitHelper
import com.adnahcodes.truebeats.data.database.RoomDB
import com.adnahcodes.truebeats.model.ListOfTracks
import com.adnahcodes.truebeats.model.Track
import com.adnahcodes.truebeats.view.TrackListFragment
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImplementation(db: RoomDB): Repository {
    private var trackDao = db.TrackDao()

    override suspend fun getTracksFromRoom(): LiveData<List<Track>> {
        val dbTrackLiveData = trackDao.getTracks()
        return dbTrackLiveData
    }

    override suspend fun insertTrackstoRoom(trackList: List<Track>) {
        val oldTracks = getTracksFromRoom().value
        val list1: MutableSet<Track>? = oldTracks?.toHashSet()
        val list2: MutableSet<Track> = trackList.toHashSet()
        coroutineScope {
            launch {
                if (list1?.containsAll(list2) == true) {
                    return@launch
                } else {
                    deleteRoomTracks(trackList)

                    insertTrackstoRoom(trackList)
                }
            }
        }
    }

    override suspend fun updateRoomTracks(trackList: List<Track>) {
        trackDao.updateTracks(trackList)
    }

    override suspend fun deleteRoomTracks(trackList: List<Track>) {
        trackDao.deleteTracks(trackList)
    }

    override fun getTracksFromApi(playlistId: Long, onSuccess: (trackList: List<Track>?) -> Unit) {
        val apiService = RetrofitHelper.instantiateRetrofit()
        var trackList: List<Track>?
        val apiCall = apiService.getPlaylistTracks(playlistId)

        apiCall.enqueue(object : Callback<ListOfTracks> {
            override fun onResponse(call: Call<ListOfTracks>, response: Response<ListOfTracks>) {
                if (response.isSuccessful) {
                    trackList = response.body()?.data
                    onSuccess(trackList)
                }
            }

            override fun onFailure(call: Call<ListOfTracks>, t: Throwable) {
                Log.e(
                    TrackListFragment.TAG, "onFailure: ${call.request().url()} and ${t.toString()}")
                onSuccess(null)
            }
        })
    }
}