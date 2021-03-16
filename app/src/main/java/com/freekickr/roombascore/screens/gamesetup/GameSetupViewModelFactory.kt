package com.freekickr.roombascore.screens.gamesetup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameSetupViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            GameSetupViewModel::class.java -> GameSetupViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}