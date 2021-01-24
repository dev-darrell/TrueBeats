package com.adnahcodes.truebeats.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Playlist (@SerializedName("id") val id: Long,
                @SerializedName("title") val title: String,
                @SerializedName("nb_tracks") val trackCount: Int,
                @SerializedName("link") val linkToPlaylist: String,
                @SerializedName("picture_medium") val picture: String,
                @SerializedName("tracklist") val linkToTracks: String,
                @SerializedName("creation_date") val dateOfCreation: String,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeInt(trackCount)
        parcel.writeString(linkToPlaylist)
        parcel.writeString(picture)
        parcel.writeString(linkToTracks)
        parcel.writeString(dateOfCreation)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Playlist> {
        override fun createFromParcel(parcel: Parcel): Playlist {
            return Playlist(parcel)
        }

        override fun newArray(size: Int): Array<Playlist?> {
            return arrayOfNulls(size)
        }
    }
}