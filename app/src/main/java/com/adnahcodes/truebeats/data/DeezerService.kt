package com.adnahcodes.truebeats.data

import com.adnahcodes.truebeats.model.ListOfPlaylists
import com.adnahcodes.truebeats.model.ListOfTracks
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerService {

    @GET("chart/0/playlists")
    fun getPlaylists(): Call<ListOfPlaylists>

    @GET("playlist/{id}/tracks")
    fun getPlaylistTracks(@Path("id") id: Long): Call<ListOfTracks>
}