package com.freekickr.roombascore.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.freekickr.roombascore.database.entities.Game

@Dao
interface GamesDao: BaseDao<Game> {

    @Query("SELECT * FROM games_table ORDER BY startTime DESC LIMIT 1")
    suspend fun getLastGame(): Game?

    @Query("SELECT * FROM games_table ORDER BY id DESC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT * FROM games_table WHERE id = :gameId LIMIT 1")
    suspend fun getGameById(gameId: Long): Game?

}