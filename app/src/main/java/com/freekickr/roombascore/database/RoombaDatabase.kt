package com.freekickr.roombascore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freekickr.roombascore.database.dao.GamesDao
import com.freekickr.roombascore.database.dao.RoundsDao
import com.freekickr.roombascore.database.entities.Game
import com.freekickr.roombascore.database.entities.Round

@Database(entities = [Game::class, Round::class], version = 1, exportSchema = false)
abstract class RoombaDatabase: RoomDatabase() {
    abstract val gamesDao: GamesDao
    abstract val roundsDao: RoundsDao
}