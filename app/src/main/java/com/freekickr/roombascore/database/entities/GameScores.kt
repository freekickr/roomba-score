package com.freekickr.roombascore.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rounds_collector_table")
data class GameScores(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val gameId: Long,
    var score1: Int,
    var score2: Int,
    var score3: Int? = null,
    var score4: Int? = null,
    var score5: Int? = null,
    var score6: Int? = null,
    var score7: Int? = null,
    var score8: Int? = null,
    var roundCount: Int
)