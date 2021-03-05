package com.adnahcodes.truebeats.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlbumEntity(
    @PrimaryKey(autoGenerate = false) val albumId: Int,
    val albumName: String,
    val albumPicture: String) {
}