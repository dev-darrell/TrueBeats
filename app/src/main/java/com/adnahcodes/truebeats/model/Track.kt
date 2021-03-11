package com.adnahcodes.truebeats.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Track(
    @SerializedName("id") @PrimaryKey(autoGenerate = false)
    val trackId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("readable") val isTrackReadable: Boolean,
    @SerializedName("preview") val previewUrl: String,
    @SerializedName("artist") @Embedded
    val artist: Artist,
    @SerializedName("album") @Embedded
    val album: Album
): Parcelable {

    @Parcelize
    data class Artist(
        @SerializedName("id") val artistId: Int,
        @SerializedName("name") val artistName: String,
        @SerializedName("picture_medium") val artistPicture: String
    ) : Parcelable

    @Parcelize
    data class Album(
        @SerializedName("id") val albumId: Int,
        @SerializedName("title") val albumName: String,
        @SerializedName("cover_big") val albumPicture: String
    ): Parcelable
}