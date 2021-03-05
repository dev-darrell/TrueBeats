package com.adnahcodes.truebeats.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    @PrimaryKey(autoGenerate = false) val trackId: Int,
    val title: String,
    val isReadable: Boolean,
    val previewUrl: String,
    @Embedded val artist: ArtistEntity,
    @Embedded val album: AlbumEntity)
