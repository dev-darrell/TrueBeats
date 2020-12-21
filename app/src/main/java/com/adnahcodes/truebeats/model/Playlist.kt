package com.adnahcodes.truebeats.model

import com.google.gson.annotations.SerializedName

class Playlist (@SerializedName("id") val id: Long,
                @SerializedName("title") val title: String,
                @SerializedName("nb_tracks") val trackCount: Int,
                @SerializedName("link") val linkToPlaylist: String,
                @SerializedName("picture_medium") val picture: String,
                @SerializedName("tracklist") val linkToTracks: String,
                @SerializedName("creation_date") val dateOfCreation: String,
){
}