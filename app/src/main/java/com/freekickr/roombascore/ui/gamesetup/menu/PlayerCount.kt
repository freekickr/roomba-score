package com.freekickr.roombascore.ui.gamesetup.menu

enum class PlayerCount(val title: String, val value: Int) {
    TWO_PLAYERS("2 игрока", 2),
    THREE_PLAYERS("3 игрока", 3),
    FOUR_PLAYERS("4 игрока", 4),
    FIVE_PLAYERS("5 игроков", 5),
    SIX_PLAYERS("6 игроков", 6),
    SEVEN_PLAYERS("7 игроков", 7),
    EIGHT_PLAYERS("8 игроков", 8),
}

data class PlayerSettings(
    val title: String,
    val value: Int,
    var name: String?
)