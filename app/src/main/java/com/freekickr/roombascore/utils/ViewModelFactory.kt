package com.freekickr.roombascore.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.ui.gameover.GameOverViewModel
import com.freekickr.roombascore.ui.gameplay.GameplayViewModel
import com.freekickr.roombascore.ui.gameplayers.PlayersViewModel
import com.freekickr.roombascore.ui.gamesetup.GameSetupViewModel
import com.freekickr.roombascore.ui.highscores.HighscoresViewModel
import com.freekickr.roombascore.ui.mainfragment.MainViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(private val appDatabase: RoombaDatabase): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            MainViewModel::class.java -> MainViewModel() as T
            GameSetupViewModel::class.java -> GameSetupViewModel() as T
            PlayersViewModel::class.java -> PlayersViewModel() as T
            GameplayViewModel::class.java -> GameplayViewModel() as T
            GameOverViewModel::class.java -> GameOverViewModel() as T
            HighscoresViewModel::class.java -> HighscoresViewModel() as T
            else -> throw IllegalArgumentException("exception")
        }
    }

}