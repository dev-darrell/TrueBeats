package com.adnahcodes.truebeats.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adnahcodes.truebeats.model.Track

//  TODO: Create another DB table to allow for caching playlists.
@Database(entities = arrayOf(Track::class), version = 1, exportSchema = false)
abstract class RoomDB: RoomDatabase() {
    abstract fun TrackDao(): TrackDao

    companion object {
        fun getDbInstance(context: Context): RoomDB {
            val db = Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java,
                "track-cache-database"
            ).build()
            return db
        }
    }
}