package com.adnahcodes.truebeats.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.adnahcodes.truebeats.model.Track

@Dao
interface TrackDao {

    @Insert
    fun insertTracks(trackList: List<Track>)

    @Update
    fun updateTracks(trackList: List<Track>)

    @Delete
    fun deleteTracks(trackList: List<Track>)

    @Query("SELECT * from Track")
    fun getTracks() : LiveData<List<Track>>
}