package com.adnahcodes.truebeats.model

import com.google.gson.annotations.SerializedName

class ListOfPlaylists(
    @SerializedName("data")
    val data: List<Playlist>,
    @SerializedName("total")
    val totalLength: Int
) {
}