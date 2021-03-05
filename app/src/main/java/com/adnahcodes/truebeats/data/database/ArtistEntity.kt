package com.adnahcodes.truebeats.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtistEntity(
    @PrimaryKey(autoGenerate = false) val artistId: Int,
    val artistName: String,
    val artistPicture: String)
