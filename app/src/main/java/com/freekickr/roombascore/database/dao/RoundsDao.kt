package com.freekickr.roombascore.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.freekickr.roombascore.database.entities.RoundHistory

@Dao
interface RoundsDao: BaseDao<RoundHistory> {

    @Query("SELECT * FROM rounds_table WHERE gameId = :gameId ORDER BY round ASC")
    suspend fun getAllRoundsForGame(gameId: Long): List<RoundHistory>

    @Query("SELECT * FROM rounds_table WHERE gameId = :gameId ORDER BY round DESC LIMIT 1")
    suspend fun getLastRoundForGame(gameId: Long): RoundHistory?

    @Query("SELECT * FROM rounds_table WHERE id = :roundId LIMIT 1")
    suspend fun getRoundById(roundId: Long): RoundHistory?
}