package com.freekickr.roombascore.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.freekickr.roombascore.database.entities.Round

@Dao
interface RoundsDao: BaseDao<Round> {

    @Query("SELECT * FROM rounds_table WHERE gameId = :gameId ORDER BY round ASC")
    suspend fun getAllRoundsForGame(gameId: Long): List<Round>

    @Query("SELECT * FROM rounds_table WHERE gameId = :gameId ORDER BY round DESC LIMIT 1")
    suspend fun getLastRoundForGame(gameId: Long): Round?

    @Query("SELECT * FROM rounds_table WHERE id = :roundId LIMIT 1")
    suspend fun getRoundById(roundId: Long): Round?
}