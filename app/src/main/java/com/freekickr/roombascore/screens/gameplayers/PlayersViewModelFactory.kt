package com.freekickr.roombascore.screens.gameplayers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlayersViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            PlayersViewModel::class.java -> PlayersViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}