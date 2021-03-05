package com.adnahcodes.truebeats.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.adnahcodes.truebeats.data.api.RetrofitHelper
import com.adnahcodes.truebeats.data.database.RoomDB
import com.adnahcodes.truebeats.model.ListOfTracks
import com.adnahcodes.truebeats.model.Track
import com.adnahcodes.truebeats.view.TrackListFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImplementation(db: RoomDB): Repository {
    private var trackDao = db.TrackDao()

    override fun getTracksFromRoom(): LiveData<List<Track>> {
        TODO("Not yet implemented")
    }

    override fun insertTrackstoRoom(trackList: List<Track>) {
        TODO("Not yet implemented")
    }

    override fun updateRoomTracks(trackList: List<Track>) {
        TODO("Not yet implemented")
    }

    override fun getTracksFromApi(playlistId: Long): List<Track>? {
        val apiService = RetrofitHelper.instantiateRetrofit()
        var trackList: List<Track>? = null

        val apiCall = apiService.getPlaylistTracks(playlistId)

        apiCall.enqueue(object : Callback<ListOfTracks> {
            override fun onResponse(call: Call<ListOfTracks>, response: Response<ListOfTracks>) {
                if (response.isSuccessful) {
                    trackList = response.body()?.data
                }
            }

            override fun onFailure(call: Call<ListOfTracks>, t: Throwable) {
                Log.e(TrackListFragment.TAG, "onFailure: ${call.request().url()} and ${t.toString()}"
                )
            }
        })
        return trackList
    }
}