package com.freekickr.roombascore.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games_table")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name1: String = "name1",
    val name2: String = "name2",
    val name3: String? = null,
    val name4: String? = null,
    val name5: String? = null,
    val name6: String? = null,
    val name7: String? = null,
    val name8: String? = null,
    val startTime: Long = System.currentTimeMillis(),
    var finished: Boolean = false
)