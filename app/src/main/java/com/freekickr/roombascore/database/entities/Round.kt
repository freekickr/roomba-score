package com.freekickr.roombascore.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rounds_table")
data class Round(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val gameId: Long = 0L,
    val round: Int = 0,
    val score1: Int = 0,
    val score2: Int = 0,
    val score3: Int? = null,
    val score4: Int? = null,
    val score5: Int? = null,
    val score6: Int? = null,
    val score7: Int? = null,
    val score8: Int? = null
)