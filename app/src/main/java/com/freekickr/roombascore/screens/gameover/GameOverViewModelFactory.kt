package com.freekickr.roombascore.screens.gameover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameOverViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            GameOverViewModel::class.java -> GameOverViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}