package com.freekickr.roombascore.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.freekickr.roombascore.database.entities.GameScores

@Dao
interface GameScoresDao: BaseDao<GameScores> {

    @Query("SELECT * FROM rounds_collector_table WHERE id = :id LIMIT 1")
    suspend fun getGameScoresById(id: Long): GameScores?

    @Query("SELECT * FROM rounds_collector_table WHERE id = :gameId LIMIT 1")
    fun getGameScoresByGameId(gameId: Long): LiveData<GameScores>

    @Query("SELECT * FROM rounds_collector_table WHERE id = :gameId LIMIT 1")
    suspend fun getGameScoresByGameIdSimple(gameId: Long): GameScores?

    @Query("SELECT roundCount FROM rounds_collector_table WHERE gameId = :gameId LIMIT 1")
    suspend fun getRoundByGameId(gameId: Long): Int
}