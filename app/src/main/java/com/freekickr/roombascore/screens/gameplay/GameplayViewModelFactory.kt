package com.freekickr.roombascore.screens.gameplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameplayViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            GameplayViewModel::class.java -> GameplayViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}