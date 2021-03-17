package com.freekickr.roombascore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freekickr.roombascore.database.entities.Test

@Database(entities = [Test::class], version = 1, exportSchema = false)
abstract class RoombaDatabase: RoomDatabase() {
    abstract val databaseDao: RoombaDatabaseDao
}