package com.freekickr.roombascore.ui.gameplay

data class Player(
    val playerName: String,
    val score: Int,
    var newPoints: Int = 0,
    val inGame: Boolean = score < 100
)