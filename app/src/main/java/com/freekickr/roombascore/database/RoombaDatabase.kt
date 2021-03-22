package com.freekickr.roombascore.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freekickr.roombascore.database.dao.GameScoresDao
import com.freekickr.roombascore.database.dao.GamesDao
import com.freekickr.roombascore.database.dao.RoundsDao
import com.freekickr.roombascore.database.entities.GameInfo
import com.freekickr.roombascore.database.entities.GameScores
import com.freekickr.roombascore.database.entities.RoundHistory

@Database(entities = [GameInfo::class, GameScores::class, RoundHistory::class], version = 1, exportSchema = false)
abstract class RoombaDatabase: RoomDatabase() {
    abstract val gamesDao: GamesDao
    abstract val roundsDao: RoundsDao
    abstract val gameScoresDao: GameScoresDao
}