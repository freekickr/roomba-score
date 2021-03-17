package com.freekickr.roombascore.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Test(
    @PrimaryKey(autoGenerate = true)
    val _id: Long = 0L
)