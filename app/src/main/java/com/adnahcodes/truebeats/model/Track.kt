package com.adnahcodes.truebeats.model

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("id") val trackId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("readable") val isTrackReadable: Boolean,
    @SerializedName("preview") val previewUrl: String,
    @SerializedName("artist") val artist: Artist,
    @SerializedName("album") val album: Album
) {

    data class Artist(
        @SerializedName("id") val artistId: Int,
        @SerializedName("name") val artistName: String,
        @SerializedName("picture_medium") val artistPicture: String
    ) {}

    data class Album(
        @SerializedName("id") val albumId: Int,
        @SerializedName("title") val albumName: String,
        @SerializedName("cover_big") val albumPicture: String
    )
}