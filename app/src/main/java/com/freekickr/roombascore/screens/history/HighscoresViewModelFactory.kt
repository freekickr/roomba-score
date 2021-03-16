package com.freekickr.roombascore.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HighscoresViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HighscoresViewModel::class.java -> HighscoresViewModel() as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}